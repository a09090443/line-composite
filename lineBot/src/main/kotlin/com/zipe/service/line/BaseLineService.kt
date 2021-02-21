package com.zipe.service.line

import com.google.common.net.HttpHeaders
import com.google.gson.Gson
import com.zipe.entity.LineChannel
import com.zipe.model.LineProperties
import com.zipe.model.PaymentRequest
import com.zipe.model.PaymentResponse
import com.zipe.repository.ILineStoreRepository
import com.zipe.repository.IOrderProcessRepository
import com.zipe.repository.IProductOrderRepository
import com.zipe.service.IMessageSettingService
import com.zipe.service.line.impl.LINE_REPLAY_MESSAGE_JSON_BLOCK
import com.zipe.util.AUTHORIZATION
import com.zipe.util.CHANNEL_ID
import com.zipe.util.NONCE
import com.zipe.util.crypto.HmacEncryptUtil
import com.zipe.util.http.OkHttpUtil
import com.zipe.util.log.logger
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.MediaType

const val ORDER_PROCESS_CACHE_KEY = "process:%s"

abstract class BaseLineService {
    val logger = logger()

    @Autowired
    protected lateinit var okHttpUtil: OkHttpUtil

    @Autowired
    protected lateinit var messageSettingImpl: IMessageSettingService

    @Autowired
    protected lateinit var lineStoreRepository: ILineStoreRepository

    @Autowired
    protected lateinit var productOrderRepository: IProductOrderRepository

    @Autowired
    protected lateinit var redisTemplate: RedisTemplate<String, String>

    @Autowired
    protected lateinit var orderProcessRepository: IOrderProcessRepository

    @Autowired
    protected lateinit var lineProperties: LineProperties

    /**
     * 回覆 Line 訊息
     */
    protected fun replyFromJson(replyToken: String, json: String, accessToken: String): String {
        val sendContent = String.format(LINE_REPLAY_MESSAGE_JSON_BLOCK, replyToken, json)
        try {
            return sendMessage(lineProperties.replayMessageUrl, sendContent, accessToken)
        } catch (e: Exception) {
            e.message
        }
        return StringUtils.EMPTY
    }

    /**
     * 推送 Line 訊息
     */
    protected fun sendMessage(url: String, content: String, accessToken: String): String {
        val headerMap = mapOf(
            HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE,
            HttpHeaders.AUTHORIZATION to "Bearer $accessToken"
        )
        return okHttpUtil.postSyncJSON(url, content, headerMap)
    }

    protected fun getDataByMessage(message: String, channelId: String): String {
        val messages = messageSettingImpl.findMessages(message, channelId)
        var json = StringUtils.EMPTY
        if (messages.isNotEmpty()) {
            json = messages.random().value
        }

        return json
    }

    protected fun getDataByMessage(message: String): String {
        return this.getDataByMessage(message, StringUtils.EMPTY)
    }

    protected fun paymentProcess(json: String, channel: LineChannel): PaymentResponse {
        val store = lineStoreRepository.findByChannelId(channel.lineStoreId)
        val paymentRequest = PaymentRequest(
            channelSecret = store.channelSecret,
            requestUri = lineProperties.paymentUri,
            requestBody = json,
            sendUrl = lineProperties.paymentRequestUrl,
            channelId = store.channelId
        )
        return encryptPaymentContent(paymentRequest)
    }

    /**
     * 將付款資訊透過 Line Api 驗證是否正確
     */
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
