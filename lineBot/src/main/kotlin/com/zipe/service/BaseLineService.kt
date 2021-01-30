package com.zipe.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.google.common.net.HttpHeaders
import com.google.gson.Gson
import com.linecorp.bot.model.message.ImageMessage
import com.zipe.entity.LineChannel
import com.zipe.enum.MessageType
import com.zipe.model.PaymentRequest
import com.zipe.model.PaymentResponse
import com.zipe.repository.ILineStoreRepository
import com.zipe.repository.IOrderProcessRepository
import com.zipe.repository.IProductOrderRepository
import com.zipe.service.impl.LINE_PAYMENT_URI
import com.zipe.service.impl.LINE_REPLAY_MESSAGE_JSON_BLOCK
import com.zipe.service.impl.MessageSettingImpl
import com.zipe.service.impl.PAYMENT_REQUEST
import com.zipe.service.impl.REPLY_MESSAGE_URL
import com.zipe.util.AUTHORIZATION
import com.zipe.util.CHANNEL_ID
import com.zipe.util.NONCE
import com.zipe.util.crypto.HmacEncryptUtil
import com.zipe.util.http.OkHttpUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.MediaType
import java.net.URI

abstract class BaseLineService {

    @Autowired
    protected lateinit var okHttpUtil: OkHttpUtil

    @Autowired
    protected lateinit var messageSettingImpl: MessageSettingImpl

    @Autowired
    protected lateinit var lineStoreRepository: ILineStoreRepository

    @Autowired
    protected lateinit var productOrderRepository: IProductOrderRepository

    @Autowired
    protected lateinit var redisTemplate: RedisTemplate<String, String>

    @Autowired
    protected lateinit var orderProcessRepository: IOrderProcessRepository

    protected fun replyFromJson(replyToken: String, json: String, accessToken: String, notificationDisabled: Boolean) {
        val sendContent = String.format(LINE_REPLAY_MESSAGE_JSON_BLOCK, replyToken, json)
        try {
            val response = sendMessage(REPLY_MESSAGE_URL, sendContent, accessToken)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun sendMessage(url: String, content: String, accessToken: String): String {
        val headerMap = mapOf(
            HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE,
            HttpHeaders.AUTHORIZATION to "Bearer $accessToken"
        )
        return okHttpUtil.postSyncJSON(url, content, headerMap)
    }

    protected fun getDataByMessage(message: String): String {
        val messages = messageSettingImpl.findMessagesByMessageKey(message);
        val json = messages.random().let {
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

    protected fun paymentProcess(json: String, channel: LineChannel): PaymentResponse {
        val store = lineStoreRepository.findByChannelId(channel.channelId)
        val paymentRequest = PaymentRequest(
            channelSecret = store.channelSecret,
            requestUri = LINE_PAYMENT_URI,
            requestBody = json,
            sendUrl = PAYMENT_REQUEST,
            channelId = store.channelId
        )
        return encryptPaymentContent(paymentRequest)
    }

    protected fun encryptPaymentContent(paymentRequest: PaymentRequest): PaymentResponse {
        val signature = HmacEncryptUtil.encrypt(paymentRequest.channelSecret, paymentRequest.data())
        val headerMap = mapOf(
            HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE,
            CHANNEL_ID to paymentRequest.channelId, NONCE to paymentRequest.nonce,
            AUTHORIZATION to signature
        )

        val response = okHttpUtil.postSyncJSON(paymentRequest.sendUrl, paymentRequest.requestBody, headerMap)
        return Gson().fromJson(response, PaymentResponse::class.java)
    }
}
