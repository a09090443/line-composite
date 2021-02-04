package com.zipe.service

import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.message.Message
import com.zipe.entity.LineInfo
import com.zipe.model.LineUser

interface ILineActionService {

    fun getLineIdByNameExcludeChannelType(name: String): String

    fun getUserId(channelId: String, users: List<String>, types: List<String>): List<LineUser>

    fun getLineIdByUserId(userId: String, type: String): LineInfo?

    fun saveLineInfo(lineInfo: LineInfo): LineInfo

    fun convertMessageType(json: String, type: String): Message

    /**
     * 根據使用者所輸入之關鍵字，回應相關訊息
     */
    fun reply(replyToken: String, messages: List<Message>, notificationDisabled: Boolean)

    fun push(to: String, messages: List<Message>, notificationDisabled: Boolean)

    /**
     * 頻道主動推送訊息至使用者或群組
     */
    fun pushFromJson(toUsers: List<String>, json: String, channelId: String, notificationDisabled: Boolean)

//    fun replyFromJson(replyToken: String, json: String, accessToken: String, notificationDisabled: Boolean)

//    fun paymentProcess(json: String, channel: LineChannel): PaymentResponse

    fun paymentConfirm(transaction: Long): String

    /**
     * 驗證從 Line 所傳送之 Channel secret 是否正確
     */
    fun isSignatureValid(channelSecret: String, signature: String, body: ByteArray): Boolean

    /**
     * 處理從 Line 伺服器回傳之不同型態之物件
     */
    fun eventHandler(event: Event, channelSecret: String)

}
