package com.zipe.line.handler

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.*
import com.linecorp.bot.model.event.message.LocationMessageContent
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.event.source.GroupSource
import com.linecorp.bot.model.profile.UserProfileResponse
import com.linecorp.bot.spring.boot.LineBotProperties
import com.linecorp.bot.spring.boot.annotation.EventMapping
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler
import com.zipe.entity.LineInfo
import com.zipe.enum.LineType
import com.zipe.service.ILineActionService
import com.zipe.service.IMessageSettingService
import org.springframework.beans.factory.annotation.Autowired


@LineMessageHandler
class MessageHandler {
    @Autowired
    private lateinit var lineMessagingClient: LineMessagingClient

    @Autowired
    private lateinit var messageService: IMessageSettingService

    @Autowired
    lateinit var lineActionService: ILineActionService

    @Autowired
    lateinit var lineBotProperties: LineBotProperties

    @EventMapping
    fun handleTextMessageEvent(event: MessageEvent<TextMessageContent>) {

        val className = event.source::class.simpleName

        println("reply token : ${event.replyToken}")
        println("message token : ${event.message::class.simpleName}")
        if (className?.contains("GROUP", true) == true) {
            println((event.source as GroupSource).groupId)
            println((event.source as GroupSource).senderId)
        }
        println("接收到的ID TYPE : $className")
        val originalMessageText = event.message.text

        val message = messageService.findBykey(originalMessageText)
        if (message.messageDetails.isEmpty()) {
            return
        } else {
            var json = message.messageDetails.first().value
//            if (originalMessageText == "捐款" || originalMessageText == "老司機") {
//                val paymentUrl = lineActionService.paymentProcess()
//                json = String.format(message.messageDetail.first().value, paymentUrl)
//            }
            lineActionService.replyFromJson(event.replyToken, json, event.source.userId, false)

        }
    }

    @EventMapping
    fun handleLocationMessageEvent(event: MessageEvent<LocationMessageContent>) {
        println("locationEvent: ${event.message.latitude}")
    }

    @EventMapping
    fun handleDefaultMessageEvent(event: Event) {
        //加入聊天室, 離開聊天室, 還有一些有的沒的事件
        println("event: $event.message::class.simpleName")

    }

    @EventMapping
    fun handleFollowEvent(event: FollowEvent) {
        event.source.userId?.let {
            lineMessagingClient.getProfile(it)?.whenComplete { profile: UserProfileResponse, throwable: Throwable? ->
                lineActionService.saveLineInfo(LineInfo(lineId = event.source.userId, name = profile.displayName,
                        statusMessage = profile.statusMessage, type = LineType.USER.name))
            }
        }
    }

    @EventMapping
    fun handlePostbackEvent(event: PostbackEvent) {

//        if (event.postbackContent.data == "捐款-Donate") {
//            println("PostbackEvent")
//
//            lineActionService.paymentProcess()
//        }
    }

    @EventMapping
    fun handleJoinEvent(event: JoinEvent) {
        println("JoinEvent")

        println(event.source.userId)
        println(event.source.senderId)
    }

    private fun getUserInfo(event: Event) {
        event.source.userId?.let {
            lineMessagingClient.getProfile(it)?.whenComplete { profile: UserProfileResponse, throwable: Throwable? ->
                println(profile.toString())
//                if (lineInfoRepository.findByUserId(it).isEmpty()) {
//                    lineInfoRepository.save(UserInfo(userId = it, name = profile.displayName, statusMessage = profile.statusMessage))
//                }
            }
        }
    }

}
