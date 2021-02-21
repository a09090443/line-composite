package com.zipe.service.line

import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.message.Message
import com.zipe.entity.LineInfo
import com.zipe.model.LineUser

interface ILineActionService {

    fun getLineIdByNameExcludeChannelType(name: String): String

    /**
     * 取得頻道所對應的 Line 使用者
     */
    fun getUserId(channelId: String, users: List<String>, types: List<String>): List<LineUser>

    fun getLineIdByUserId(userId: String, type: String): LineInfo?

    fun saveLineInfo(lineInfo: LineInfo): LineInfo

    /**
     * 根據使用者所輸入之關鍵字，回應相關訊息
     */
    fun reply(replyToken: String, messages: List<Message>, notificationDisabled: Boolean)

    /**
     * 根據輸入之關鍵字，主動傳送給指定使用者
     */
    fun push(to: String, messages: List<Message>, notificationDisabled: Boolean)

    /**
     * 頻道主動推送訊息至使用者或群組
     */
    fun pushFromJson(toUsers: List<String>, json: String, channelId: String)

    /**
     * Line pay 驗證流程
     */
    fun paymentConfirm(transactionId: Long): String

    /**
     * 驗證從 Line 所傳送之 Channel secret 是否正確
     */
    fun isSignatureValid(channelSecret: String, signature: String, body: ByteArray): Boolean

    /**
     * 處理從 Line 伺服器回傳之不同事件之物件
     */
    fun eventHandler(event: Event, channelSecret: String)

}
