package com.zipe.service.line.impl

import com.google.gson.Gson
import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.MessageEvent
import com.linecorp.bot.model.event.message.TextMessageContent
import com.zipe.entity.LineChannel
import com.zipe.entity.OrderProcess
import com.zipe.service.line.BaseLineService
import com.zipe.service.line.ILineEventService
import com.zipe.service.line.ORDER_PROCESS_CACHE_KEY
import com.zipe.util.CANCEL
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

@Service
class LineMessageEventServiceImpl : BaseLineService(), ILineEventService {
    override fun process(
        channel: LineChannel,
        client: LineMessagingClient,
        event: Event
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
                    this.replyFromJson(replyToken, json, channel.accessToken)
                } else {
                    val json =
                        redisTemplate.opsForList().index(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId), 0)
                    val process = json.let {
                        Gson().fromJson(it, OrderProcess::class.java)
                    }
                    when (process.type) {
                        // 需確認使用者輸入為數字，否則取消這筆消費
                        "number" -> StringUtils.isNumeric(originalMessageText).takeIf { !it }?.let {
                            orderProcessRepository.findByName(CANCEL).run {

                                replyFromJson(replyToken, this.content, channel.accessToken)
                                throw Exception("Input type error.")
                            }
                        }
                    }
                    // FIXME json 內容如何使用 String.format 來更自由的不限長度取代 %s
                    //{
                    //  "type": "template",
                    //  "altText": "確認付款",
                    //  "template": {
                    //    "type": "confirm",
                    //    "actions": [
                    //      {
                    //        "type": "postback",
                    //        "label": "是",
                    //        "data": "{\"isToPay\":true,\"productId\":\"americano\",\"count\":\"%s\",\"quantityUnit\":\"price\"}"
                    //      },
                    //      {
                    //        "type": "postback",
                    //        "label": "否",
                    //        "data": "{\"isCancel\":true}"
                    //      }
                    //    ],
                    //    "text": "您所輸入的數量為%s杯，總金額為%s元，是否確認購買?"
                    //  }
                    //}
                    this.replyFromJson(
                        event.replyToken, String.format(
                            process.content, originalMessageText, originalMessageText
                        ), channel.accessToken
                    )
                    redisTemplate.opsForList().leftPop(String.format(ORDER_PROCESS_CACHE_KEY, event.source.userId))
                }
            }
        }
    }
}
