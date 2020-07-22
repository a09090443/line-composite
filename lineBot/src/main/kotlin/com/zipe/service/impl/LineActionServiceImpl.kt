package com.zipe.service.impl

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.common.net.HttpHeaders
import com.google.gson.Gson
import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.ReplyMessage
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.PostbackEvent
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.event.source.GroupSource
import com.linecorp.bot.model.message.ImageMessage
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.*
import com.zipe.enum.EventType
import com.zipe.enum.LineType
import com.zipe.enum.MessageType
import com.zipe.enum.OrderStatus
import com.zipe.model.*
import com.zipe.repository.*
import com.zipe.service.ILineActionService
import com.zipe.util.*
import org.apache.commons.codec.digest.HmacAlgorithms
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import java.net.URI
import java.security.MessageDigest
import java.time.Duration
import java.util.*
import java.util.concurrent.ExecutionException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

const val LINE_PUSH_MESSAGE_JSON_BLOCK = """{"to":"%s","messages":[%s]}"""
const val LINE_REPLAY_MESSAGE_JSON_BLOCK = """{"replyToken":"%s","messages":[%s]}"""
const val PUSH_MESSAGE_URL = "https://api.line.me/v2/bot/message/push"
const val REPLY_MESSAGE_URL = "https://api.line.me/v2/bot/message/reply"
const val PAYMENT_REQUEST = "https://sandbox-api-pay.line.me/v3/payments/request"
const val PAYMENT_CONFIRM_URI = "/v3/payments/%s/confirm"
const val PAYMENT_CONFIRM = "https://sandbox-api-pay.line.me/v3/payments/%s/confirm"
const val LINE_PAYMENT_URI = "/v3/payments/request"
const val PAYMENY_CONFIRN_CALLBAK = "https://line.zipe.idv.tw/line/payment/confirm"
const val PAYMENY_CANCEL_CALLBAK = "https://line.zipe.idv.tw/line/payment/cancel"
const val ORDER_PROCESS_CACHE_KEY = "process:%s"
const val LINE_REQUEST_SUCCESS_CODE = "0000"

@Service("lineActionService")
class LineActionServiceImpl : ILineActionService {

    @Autowired
    private lateinit var lineMessagingClient: LineMessagingClient

    @Autowired
    private lateinit var lineInfoRepository: ILineInfoRepository

    @Autowired
    private lateinit var lineChannelRepository: ILineChannelRepository

    @Autowired
    private lateinit var lineStoreRepository: ILineStoreRepository

    @Autowired
    private lateinit var lineMappingRepository: ILineMappingRepository

    @Autowired
    private lateinit var messageSettingRepository: IMessageSettingRepository

    @Autowired
    private lateinit var orderProcessRepository: IOrderProcessRepository

    @Autowired
    private lateinit var productRepository: IProductRepository

    @Autowired
    private lateinit var productOrderRepository: IProductOrderRepository

    @Autowired
    lateinit var okHttpUtil: OkHttpUtil

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    override fun getLineIdByNameExcludeChannelType(name: String) = lineInfoRepository.findLineIdExcludeType(name, LineType.CHANNEL.name)

    override fun getLineIdByUserId(userId: String, type: String) = lineInfoRepository.findByLineIdAndType(userId, type)

    override fun saveLineInfo(lineInfo: LineInfo) = lineInfoRepository.save(lineInfo)

    override fun convertMessageType(json: String, type: String) = MessageType.valueOf(type.toUpperCase()).message(json)

    override fun reply(replyToken: String, messages: List<Message>, notificationDisabled: Boolean) {
        try {
            lineMessagingClient.replyMessage(ReplyMessage(replyToken, messages, notificationDisabled))?.get()
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } catch (e: ExecutionException) {
            throw RuntimeException(e)
        }
    }

    override fun push(to: String, messages: List<Message>, notificationDisabled: Boolean) {
        try {
            val apiResponse = lineMessagingClient.pushMessage(PushMessage(to, messages, notificationDisabled))?.get()
//            log.info("Sent messages: {}", apiResponse)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } catch (e: ExecutionException) {
            throw RuntimeException(e)
        }
    }

    override fun pushFromJson(to: String, json: String, channelName: String, notificationDisabled: Boolean) {
        val channelInfo = lineChannelRepository.findByName(channelName)
        val sendContent = String.format(LINE_PUSH_MESSAGE_JSON_BLOCK, to, json)
        try {
            val response = sendMessage(PUSH_MESSAGE_URL, sendContent, channelInfo.accessToken)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun replyFromJson(replyToken: String, json: String, accessToken: String, notificationDisabled: Boolean) {
        val sendContent = String.format(LINE_REPLAY_MESSAGE_JSON_BLOCK, replyToken, json)
        try {
            val response = sendMessage(REPLY_MESSAGE_URL, sendContent, accessToken)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun paymentProcess(json: String, channel: LineChannel): PaymentResponse {
        val store = lineStoreRepository.findByLineId(channel.lineId)
        val paymentRequest = PaymentRequest(channelSecret = store.channelSecret, requestUri = LINE_PAYMENT_URI, requestBody = json, sendUrl = PAYMENT_REQUEST, channelId = store.channelId)
        return encryptPaymentContent(paymentRequest)
    }

    override fun paymentConfirm(transaction: Long): String {
        val productOrder = productOrderRepository.findByTransactionId(transaction)
        val channel = lineChannelRepository.findByChannelId(productOrder.channelId)
        val store = lineStoreRepository.findByLineId(channel.lineId)

        val conformUri = String.format(PAYMENT_CONFIRM_URI, transaction)
        val requestBody = Gson().toJson(PaymentConfirmRequest(amount = productOrder.amount, currency = "TWD"))

        val paymentRequest = PaymentRequest(channelSecret = store.channelSecret, requestUri = conformUri,
                requestBody = requestBody, sendUrl = String.format(PAYMENT_CONFIRM, transaction), channelId = store.channelId)
        val response = encryptPaymentContent(paymentRequest)
        if (response.returnCode == LINE_REQUEST_SUCCESS_CODE) {
            with(productOrder) {
                this.status = OrderStatus.SUCCESS.name
            }
            productOrderRepository.save(productOrder)
            val success = orderProcessRepository.findByProcessName("pay_success")
            this.pushFromJson(productOrder.lineId, success.content, channel.name, false)
        } else {
            println(response.returnCode)
            return ""
        }
        return channel.botId
    }

    override fun isSignatureValid(channelSecret: String, signature: String, body: ByteArray): Boolean {
        var result = false

        try {
            val sha256HMAC = Mac.getInstance(HmacAlgorithms.HMAC_SHA_256.toString())
            val secretKey = SecretKeySpec(channelSecret.toByteArray(Charsets.UTF_8), HmacAlgorithms.HMAC_SHA_256.toString())
            sha256HMAC.init(secretKey)
            val headerSignature = Base64.getDecoder().decode(signature)
            val bodySignature = sha256HMAC.doFinal(body)
//            logger.info("headerSignature = $headerSignature")
//            logger.info("bodySignature = $bodySignature")
            result = MessageDigest.isEqual(headerSignature, bodySignature)
        } catch (e: Exception) {
//            logger.error(e)
        } finally {
            return result
        }
    }

    override fun eventHandler(event: Event, cahnnelSecret: String) {
        val className = event::class.java.simpleName
        val channel = lineChannelRepository.findByChannelSecret(cahnnelSecret)
        val userId = event.source.userId
        val processCache = redisTemplate.opsForList().range(String.format(ORDER_PROCESS_CACHE_KEY, userId), 0, -1)
                ?: listOf()

        when (EventType.valueOf(className.toUpperCase())) {
            EventType.FOLLOWEVENT -> {
                userId?.let {
                    lineMessagingClient.getProfile(it)?.whenComplete { profile: UserProfileResponse, throwable: Throwable? ->
                        var lineInfo = lineInfoRepository.findByLineIdAndType(userId, LineType.USER.name)

                        if (null == lineInfo) {
                            this.saveLineInfo(LineInfo(lineId = userId, name = profile.displayName,
                                    statusMessage = profile.statusMessage, type = LineType.USER.name))
                            lineInfo = lineInfoRepository.findByLineIdAndType(userId, LineType.USER.name)
                        }

                        lineMappingRepository.save(LineMapping(lineId = channel.lineId, infoId = lineInfo?.infoId ?: 0))
                    }
                }
            }
            EventType.UNFOLLOWEVENT -> {
                val lineInfo = lineInfoRepository.findByLineIdAndType(userId, LineType.USER.name)

                lineMappingRepository.delete(LineMapping(lineId = channel.lineId, infoId = lineInfo?.infoId ?: 0))
            }
            EventType.JOINEVENT -> {
                (event.source as GroupSource).groupId.let {
                    var lineInfo = lineInfoRepository.findByLineIdAndType(it, LineType.GROUP.name)

                    if (null == lineInfo) {
                        this.saveLineInfo(LineInfo(lineId = it, type = LineType.GROUP.name))
                        lineInfo = lineInfoRepository.findByLineIdAndType(it, LineType.GROUP.name)
                    }

                    lineMappingRepository.save(LineMapping(lineId = channel.lineId, infoId = lineInfo?.infoId ?: 0))
                }

            }
            EventType.LEAVEEVENT -> {
                (event.source as GroupSource).groupId.let {
                    val lineInfo = lineInfoRepository.findByLineIdAndType(it, LineType.GROUP.name)
                    lineMappingRepository.delete(LineMapping(lineId = channel.lineId, infoId = lineInfo?.infoId ?: 0))
                }
            }
            EventType.POSTBACKEVENT -> {
                event as PostbackEvent
                val data = event.postbackContent.data
                val processRequest = Gson().fromJson(data, OrderProcessRequest::class.java)

                if (processCache.isNullOrEmpty()) {

                    // 初始訂單流程
                    if (processRequest.isOrderProcess) {
                        val product = productRepository.findByProductIdAndLineId(processRequest.productId, channel.lineId)
                        val firstProcess = startOrderProcess(userId, product.name, channel.lineId)
                        replyFromJson(event.replyToken, firstProcess.content, channel.accessToken, false)
                        redisTemplate.opsForList().leftPop(String.format(ORDER_PROCESS_CACHE_KEY, userId))
                    } else {
                        val json = getDataByMessage(data)
                        this.replyFromJson(event.replyToken, json, channel.accessToken, false)
                        redisTemplate.opsForList().leftPop(String.format(ORDER_PROCESS_CACHE_KEY, userId))
                    }
                } else {
                    // 取消訂單流程
                    if (processRequest.isCancel) {
                        redisTemplate.opsForList()
                                .index(String.format(ORDER_PROCESS_CACHE_KEY, userId), 0)
                                .isNullOrEmpty().takeIf { it }?.let { return }
                        redisTemplate.delete(String.format(ORDER_PROCESS_CACHE_KEY, userId))
                        val cancelOK = orderProcessRepository.findByProcessName(CANCEL_SUCCESS)
                        replyFromJson(event.replyToken, cancelOK.content, channel.accessToken, false)
                        return
                    }

                }

                // 完成訂單流程並產出 line pay
                if (processRequest.isToPay) {

                    val product = productRepository.findByProductIdAndLineId(processRequest.productId, channel.lineId)
                    //Step1 確認輸入值為"數目"或"金額"，如輸入為"金額"數量預設為"1"
                    val paymentProduct = PaymentProduct(id = product.productId, name = product.name,
                            imageUrl = product.image).apply {
                        if (processRequest.quantityUnit == "price") {
                            this.price = processRequest.count
                            this.quantity = 1
                        } else if (processRequest.quantityUnit == "quantity") {
                            this.quantity = processRequest.count.toLong()
                            this.price = product.price.setScale(0)
                        }
                    }

                    //Step2
                    val productPackageForm = ProductPackageForm(name = channel.name, amount = paymentProduct.price.times(paymentProduct.quantity.toBigDecimal()).setScale(0))
                    productPackageForm.products = listOf(paymentProduct)

                    //Step3
                    val order = ProductOrder().apply {
                        this.amount = productPackageForm.amount
                        this.channelId = channel.channelId ?: ""
                        this.lineId = userId
                        this.quantity = paymentProduct.quantity
                        this.status = OrderStatus.PENDING.name
                        this.productName = product.name
                    }

                    val form = CheckoutPaymentRequestForm(amount = productPackageForm.amount, currency = "TWD",
                            orderId = order.orderId, packages = listOf(productPackageForm))

                    val redirectUrls = RedirectUrls(confirmUrl = PAYMENY_CONFIRN_CALLBAK, cancelUrl = PAYMENY_CANCEL_CALLBAK)
                    form.redirectUrls = redirectUrls

                    val paymentResponse = this.paymentProcess(Gson().toJson(form), channel)
                    if (paymentResponse.returnCode == LINE_REQUEST_SUCCESS_CODE) {
                        order.transactionId = paymentResponse.info.transactionId
                        productOrderRepository.save(order)
                        val pay = orderProcessRepository.findByProcessName(PAY)
                        val convert = String.format(pay.content, paymentResponse.info.paymentUrl.web, product.image, product.name)
                        replyFromJson(event.replyToken, convert, channel.accessToken, false)
//                        redisTemplate.delete(String.format(ORDER_PROCESS_CACHE_KEY, userId))
                    } else {
                        println(paymentResponse.returnCode)
                        val error = orderProcessRepository.findByProcessName(ERROR)
                        replyFromJson(event.replyToken, error.content, channel.accessToken, false)
                    }
                    redisTemplate.delete(String.format(ORDER_PROCESS_CACHE_KEY, userId))
                }
            }
            EventType.MESSAGEEVENT -> {
                event as MessageEvent<*>
                when (event.message::class.simpleName) {
                    "TextMessageContent" -> {
                        event as MessageEvent<TextMessageContent>
                        val originalMessageText = event.message.text

                        val replyToken = event.replyToken
                        val result = redisTemplate.opsForList()
                                .range(String.format(ORDER_PROCESS_CACHE_KEY, userId), 0, -1) ?: listOf()

                        if (result.isNullOrEmpty()) {
                            val json = getDataByMessage(originalMessageText)
                            this.replyFromJson(replyToken, json, channel.accessToken, false)
                        } else {
                            val json = redisTemplate.opsForList().index(String.format(ORDER_PROCESS_CACHE_KEY, userId), 0)
                            val process = json.let {
                                Gson().fromJson(it, OrderProcess::class.java)
                            }
                            when (process.type) {
                                "number" -> StringUtils.isNumeric(originalMessageText).takeIf { !it }?.let {
                                    orderProcessRepository.findByProcessName(CANCEL).run {

                                        replyFromJson(replyToken, this.content, channel.accessToken, false)
                                        throw Exception("Input type error.")
                                    }
                                }
                            }
                            this.replyFromJson(event.replyToken, String.format(process.content, originalMessageText,
                                    originalMessageText), channel.accessToken, false)
                            redisTemplate.opsForList().leftPop(String.format(ORDER_PROCESS_CACHE_KEY, userId))
                        }
                    }
                }
            }
        }
    }

    private fun encryptPaymentContent(paymentRequest: PaymentRequest): PaymentResponse {
        val signature = HmacEncryptUtil.encrypt(paymentRequest.channelSecret, paymentRequest.data())
        val headerMap = mapOf(HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE,
                CHANNEL_ID to paymentRequest.channelId, NONCE to paymentRequest.nonce,
                AUTHORIZATION to signature)

        val response = okHttpUtil.postSyncJSON(paymentRequest.sendUrl, paymentRequest.requestBody, headerMap)
        return Gson().fromJson(response, PaymentResponse::class.java)
    }

    private fun startOrderProcess(userId: String, processName: String, lineId: Long): OrderProcess {

        orderProcessRepository.findByProcessNameAndLineIdOrderBySequence(processName, lineId).forEach {
            val json = Gson().toJson(it)
            redisTemplate.opsForList().rightPush(String.format(ORDER_PROCESS_CACHE_KEY, userId), json)
        }
        redisTemplate.opsForList().rightPush(String.format(ORDER_PROCESS_CACHE_KEY, userId), "end_process")
        redisTemplate.expire(String.format(ORDER_PROCESS_CACHE_KEY, userId), Duration.ofMinutes(5))
        val parent = redisTemplate.opsForList().index(String.format(ORDER_PROCESS_CACHE_KEY, userId), 0)
        return Gson().fromJson(parent, OrderProcess::class.java)
    }

    private fun sendMessage(url: String, content: String, accessToken: String) {
        val headerMap = mapOf(HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE, HttpHeaders.AUTHORIZATION to "Bearer $accessToken")
        okHttpUtil.postSyncJSON(url, content, headerMap)
    }

    private fun getDataByMessage(message: String): String {
        val messageSetting = messageSettingRepository.findAllByKey(message) ?: return ""

        val json = messageSetting.messageDetails.random().let {
            when (MessageType.getTypeName(it.type.toUpperCase())) {
                MessageType.IMAGE -> {
                    val imageUrl = URI(it.value)
                    jacksonObjectMapper().writeValueAsString(ImageMessage(imageUrl, imageUrl))
                }
                else -> it.value
            }
        }

        return json
    }
}
