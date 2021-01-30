package com.zipe.service.impl

import com.google.gson.Gson
import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.PostbackEvent
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel
import com.zipe.entity.OrderProcess
import com.zipe.entity.ProductOrder
import com.zipe.enum.OrderStatus
import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.BaseJDBC
import com.zipe.model.CheckoutPaymentRequestForm
import com.zipe.model.OrderProcessRequest
import com.zipe.model.PaymentProduct
import com.zipe.model.ProductPackageForm
import com.zipe.model.RedirectUrls
import com.zipe.repository.IOrderProcessRepository
import com.zipe.repository.IProductRepository
import com.zipe.service.BaseLineService
import com.zipe.service.ILineEventService
import com.zipe.util.CANCEL_SUCCESS
import com.zipe.util.ERROR
import com.zipe.util.PAY
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class LinePostBackEventServiceImpl : BaseLineService(), ILineEventService {

    @Autowired
    private lateinit var productRepository: IProductRepository

    @Autowired
    private lateinit var orderJDBC: BaseJDBC

    override fun process(
        channel: LineChannel,
        client: LineMessagingClient,
        event: Event,
        profile: UserProfileResponse?
    ) {
        val processCache =
            redisTemplate.opsForList().range(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId), 0, -1) ?: listOf()

        event as PostbackEvent
        val data = event.postbackContent.data
        val processRequest = Gson().fromJson(data, OrderProcessRequest::class.java)

        if (processCache.isNullOrEmpty()) {

            // 初始訂單流程
            if (processRequest.isOrderProcess) {
                val product =
                    productRepository.findByProductIdAndChannelId(processRequest.productId, channel.channelId)
                val firstProcess = startOrderProcess(event.source.userId, product.name, channel.channelId)
                replyFromJson(event.replyToken, firstProcess.content, channel.accessToken, false)
                redisTemplate.opsForList().leftPop(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId))
            } else {
                val json = getDataByMessage(data)
                this.replyFromJson(event.replyToken, json, channel.accessToken, false)
                redisTemplate.opsForList().leftPop(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId))
            }
        } else {
            // 取消訂單流程
            if (processRequest.isCancel) {
                redisTemplate.opsForList()
                    .index(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId), 0)
                    .isNullOrEmpty().takeIf { it }?.let { return }
                redisTemplate.delete(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId))
                val cancelOK = orderProcessRepository.findByName(CANCEL_SUCCESS)
                replyFromJson(event.replyToken, cancelOK.content, channel.accessToken, false)
                return
            }

        }

        // 完成訂單流程並產出 line pay
        if (processRequest.isToPay) {

            val product =
                productRepository.findByProductIdAndChannelId(processRequest.productId, channel.channelId)
            //Step1 確認輸入值為"數目"或"金額"，如輸入為"金額"數量預設為"1"
            val paymentProduct = PaymentProduct(
                id = product.productId, name = product.name,
                imageUrl = product.image
            ).apply {
                if (processRequest.quantityUnit == "price") {
                    this.price = processRequest.count
                    this.quantity = 1
                } else if (processRequest.quantityUnit == "quantity") {
                    this.quantity = processRequest.count.toLong()
                    this.price = product.price.setScale(0)
                }
            }

            //Step2
            val productPackageForm = ProductPackageForm(
                name = channel.name,
                amount = paymentProduct.price.times(paymentProduct.quantity.toBigDecimal()).setScale(0)
            )
            productPackageForm.products = listOf(paymentProduct)

            //Step3
            val order = ProductOrder().apply {
                this.amount = productPackageForm.amount
                this.channelId = channel.channelId
                this.lineId = event.source.userId
                this.quantity = paymentProduct.quantity
                this.status = OrderStatus.PENDING.name
                this.productName = product.name
            }

            val form = CheckoutPaymentRequestForm(
                amount = productPackageForm.amount, currency = "TWD",
                orderId = order.orderId, packages = listOf(productPackageForm)
            )

            val redirectUrls =
                RedirectUrls(confirmUrl = PAYMENY_CONFIRN_CALLBAK, cancelUrl = PAYMENY_CANCEL_CALLBAK)
            form.redirectUrls = redirectUrls

            val paymentResponse = this.paymentProcess(Gson().toJson(form), channel)
            if (paymentResponse.returnCode == LINE_REQUEST_SUCCESS_CODE) {
                order.transactionId = paymentResponse.info.transactionId
                productOrderRepository.save(order)
                val pay = orderProcessRepository.findByName(PAY)
                val convert =
                    String.format(pay.content, paymentResponse.info.paymentUrl.web, product.image, product.name)
                replyFromJson(event.replyToken, convert, channel.accessToken, false)
//                        redisTemplate.delete(String.format(ORDER_PROCESS_CACHE_KEY, userId))
            } else {
                println(paymentResponse.returnCode)
                val error = orderProcessRepository.findByName(ERROR)
                replyFromJson(event.replyToken, error.content, channel.accessToken, false)
            }
            redisTemplate.delete(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId))
        }
    }

    private fun startOrderProcess(userId: String, name: String, channelId: String): OrderProcess {
        val resource: ResourceEnum = ResourceEnum.SQL_LINE.getResource("FIND_ORDER_PROCESS_TREE")
        val argMap = mapOf("name" to name, "channelId" to channelId)
        val orderProcess = orderJDBC.queryForList(resource, null, argMap, OrderProcess::class.java)
        orderProcess.forEach {
            val json = Gson().toJson(it)
            redisTemplate.opsForList().rightPush(String.format(ORDER_PROCESS_CACHE_KEY, userId), json)
        }
        redisTemplate.opsForList().rightPush(String.format(ORDER_PROCESS_CACHE_KEY, userId), "end_process")
        redisTemplate.expire(String.format(ORDER_PROCESS_CACHE_KEY, userId), Duration.ofMinutes(5))
        val parent = redisTemplate.opsForList().index(String.format(ORDER_PROCESS_CACHE_KEY, userId), 0)
        return Gson().fromJson(parent, OrderProcess::class.java)
    }
}
