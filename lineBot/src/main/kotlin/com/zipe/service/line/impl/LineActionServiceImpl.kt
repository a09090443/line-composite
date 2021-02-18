package com.zipe.service.line.impl

import com.google.gson.Gson
import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.PushMessage
import com.linecorp.bot.model.ReplyMessage
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.message.Message
import com.zipe.entity.LineInfo
import com.zipe.enum.EventType
import com.zipe.enum.LineType
import com.zipe.enum.MessageType
import com.zipe.enum.OrderStatus
import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.LineChannelJDBC
import com.zipe.model.LineUser
import com.zipe.model.PaymentConfirmRequest
import com.zipe.model.PaymentRequest
import com.zipe.repository.ILineChannelRepository
import com.zipe.repository.ILineInfoRepository
import com.zipe.service.line.BaseLineService
import com.zipe.service.line.ILineActionService
import com.zipe.util.log.logger
import org.apache.commons.codec.digest.HmacAlgorithms
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.security.MessageDigest
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
const val PAYMENT_CONFIRM_CALLBACK = "https://line.zipe.idv.tw/line/payment/confirm"
const val PAYMENT_CANCEL_CALLBACK = "https://line.zipe.idv.tw/line/payment/cancel"
const val ORDER_PROCESS_CACHE_KEY = "process:%s"
const val LINE_REQUEST_SUCCESS_CODE = "0000"

@Service
class LineActionServiceImpl : BaseLineService(), ILineActionService {

    @Autowired
    private lateinit var lineMessagingClient: LineMessagingClient

    @Autowired
    private lateinit var lineInfoRepository: ILineInfoRepository

    @Autowired
    private lateinit var lineChannelRepository: ILineChannelRepository

    @Autowired
    private lateinit var lineChannelJDBC: LineChannelJDBC

    override fun getLineIdByNameExcludeChannelType(name: String) =
        lineInfoRepository.findLineIdExcludeType(name, LineType.CHANNEL.name)

    override fun getUserId(channelId: String, users: List<String>, types: List<String>): List<LineUser> {
        val resource: ResourceEnum = ResourceEnum.SQL_LINE.getResource("FIND_LINE_USERS_BY_CHANNEL_ID")
        val argMap = mapOf("channelId" to channelId, "name" to users, "types" to types)
        return lineChannelJDBC.queryForList(resource, null, argMap, LineUser::class.java)
    }

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
            logger().info("Sent messages: {}", apiResponse)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        } catch (e: ExecutionException) {
            throw RuntimeException(e)
        }
    }

    override fun pushFromJson(toUsers: List<String>, json: String, channelId: String, notificationDisabled: Boolean) {
        val channelInfo = lineChannelRepository.findByChannelId(channelId)
        toUsers.forEach { user ->
            try {
                val sendContent = String.format(LINE_PUSH_MESSAGE_JSON_BLOCK, user, json)
                sendMessage(PUSH_MESSAGE_URL, sendContent, channelInfo.accessToken)
            } catch (e: Exception) {
                logger.error("發送訊息失敗，對象:$user, 內容:$json")
                e.message
            }
        }
    }

    override fun paymentConfirm(transaction: Long): String {
        val productOrder = productOrderRepository.findByTransactionId(transaction)
        val channel = lineChannelRepository.findByChannelId(productOrder.channelId)
        val store = lineStoreRepository.findByChannelId(productOrder.channelId)

        val conformUri = String.format(PAYMENT_CONFIRM_URI, transaction)
        val requestBody = Gson().toJson(PaymentConfirmRequest(amount = productOrder.amount, currency = "TWD"))

        val paymentRequest = PaymentRequest(
            channelSecret = store.channelSecret,
            requestUri = conformUri,
            requestBody = requestBody,
            sendUrl = String.format(PAYMENT_CONFIRM, transaction),
            channelId = store.channelId
        )
        val response = encryptPaymentContent(paymentRequest)
        // Line Api 驗證成功則回傳 0000 代碼
        if (response.returnCode == LINE_REQUEST_SUCCESS_CODE) {
            with(productOrder) {
                this.status = OrderStatus.SUCCESS.name
            }
            productOrderRepository.save(productOrder)
            val success = orderProcessRepository.findByName("pay_success")
            this.pushFromJson(listOf(productOrder.lineId), success.content, channel.name, false)
        } else {
            logger.warn("Line Pay 驗證失敗， return code :${response.returnCode}")
            return ""
        }
        return channel.botId
    }

    override fun isSignatureValid(channelSecret: String, signature: String, body: ByteArray): Boolean {
        println(lineProperties)
        return try {
            logger.info("驗證簽名字串...")
            val sha256HMAC = Mac.getInstance(HmacAlgorithms.HMAC_SHA_256.toString())
            val secretKey =
                SecretKeySpec(channelSecret.toByteArray(Charsets.UTF_8), HmacAlgorithms.HMAC_SHA_256.toString())
            sha256HMAC.init(secretKey)
            val headerSignature = Base64.getDecoder().decode(signature)
            val bodySignature = sha256HMAC.doFinal(body)
            logger.info("headerSignature = $headerSignature")
            logger.info("bodySignature = $bodySignature")
            MessageDigest.isEqual(headerSignature, bodySignature)
        } catch (e: Exception) {
            logger.error(e.message)
            false
        }
    }

    override fun eventHandler(event: Event, channelSecret: String) {
        val className = event::class.java.simpleName
        val channel = lineChannelRepository.findByChannelSecret(channelSecret)
        val client = LineMessagingClient.builder(channel.accessToken).build()
        val service = EventType.getService(className.toUpperCase())
        service.process(channel, client, event, null)
    }

}
