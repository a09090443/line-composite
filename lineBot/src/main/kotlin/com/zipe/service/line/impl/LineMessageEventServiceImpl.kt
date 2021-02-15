package com.zipe.service.line.impl

import com.google.gson.Gson
import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.TextMessageContent
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel
import com.zipe.entity.OrderProcess
import com.zipe.service.line.BaseLineService
import com.zipe.service.line.ILineEventService
import com.zipe.util.CANCEL
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

@Service
class LineMessageEventServiceImpl : BaseLineService(), ILineEventService {
    override fun process(
        channel: LineChannel,
        client: LineMessagingClient,
        event: Event,
        profile: UserProfileResponse?
    ) {
        event as MessageEvent<*>
        when (event.message::class.simpleName) {
            "TextMessageContent" -> {
                val textMessage = event.message as TextMessageContent
                val originalMessageText = textMessage.text

                val replyToken = event.replyToken
                val result = redisTemplate.opsForList()
                    .range(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId), 0, -1) ?: listOf()

                if (result.isNullOrEmpty()) {
                    val json = getDataByMessage(originalMessageText)
                    this.replyFromJson(replyToken, json, channel.accessToken, false)
                } else {
                    val json =
                        redisTemplate.opsForList().index(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId), 0)
                    val process = json.let {
                        Gson().fromJson(it, OrderProcess::class.java)
                    }
                    when (process.type) {
                        "number" -> StringUtils.isNumeric(originalMessageText).takeIf { !it }?.let {
                            orderProcessRepository.findByName(CANCEL).run {

                                replyFromJson(replyToken, this.content, channel.accessToken, false)
                                throw Exception("Input type error.")
                            }
                        }
                    }
                    this.replyFromJson(
                        event.replyToken, String.format(
                            process.content, originalMessageText,
                            originalMessageText
                        ), channel.accessToken, false
                    )
                    redisTemplate.opsForList().leftPop(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId))
                }
            }
        }
    }
}
