package com.zipe.service

import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.message.Message
import com.zipe.entity.LineChannel
import com.zipe.entity.LineInfo
import com.zipe.model.PaymentResponse

interface ILineActionService {

    fun getLineIdByNameExcludeChannelType(name: String): String

    fun getLineIdByUserId(userId: String, type: String): LineInfo?

    fun saveLineInfo(lineInfo: LineInfo): LineInfo

    fun convertMessageType(json: String, type: String): Message

    fun reply(replyToken: String, messages: List<Message>, notificationDisabled: Boolean)

    fun push(to: String, messages: List<Message>, notificationDisabled: Boolean)

    fun pushFromJson(to: String, json: String, channelName: String, notificationDisabled: Boolean)

//    fun replyFromJson(replyToken: String, json: String, accessToken: String, notificationDisabled: Boolean)

//    fun paymentProcess(json: String, channel: LineChannel): PaymentResponse

    fun paymentConfirm(transaction: Long): String

    /**
     * 驗證從 Line 所傳送之 Channel secret 是否正確
     */
    fun isSignatureValid(channelSecret: String, signature: String, body: ByteArray): Boolean

    fun eventHandler(event: Event, channelSecret: String)

}
