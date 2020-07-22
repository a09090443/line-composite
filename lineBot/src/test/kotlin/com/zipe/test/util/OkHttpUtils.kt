package com.zipe.test.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.linecorp.bot.model.action.*
import com.linecorp.bot.model.message.ImageMessage
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.model.message.template.ButtonsTemplate
import com.linecorp.bot.model.message.template.CarouselColumn
import com.linecorp.bot.model.message.template.CarouselTemplate
import com.zipe.enum.MessageType
import com.zipe.util.OkHttpUtil
import okhttp3.*
import org.apache.commons.lang3.StringUtils
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.IOException
import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


fun main() {
    val sss = MessageType.getTypeName("IMAGE")
    println(sss)
    val str = jacksonObjectMapper().writeValueAsString(ImageMessage(URI("https://yukiblogtw.achangpro.com/img/a22978399d27_11F4E/DSCF2839.jpg"), URI("https://yukiblogtw.achangpro.com/img/a22978399d27_11F4E/DSCF2839.jpg")))
    println(str)
    val headerMap = mapOf<String, String>("Content-Type" to "application/json", "Authorization" to "Bearer D7YFymbHjichf2vTlzSCoTdcJtQjA/BeqmaphMKr+1VJs0y6OEDl3BILKtegGjlwWIuy8Xe9OonY6eq3+j+XlB7Pum5VMVqXpEsyz9W2JEpNvGKERTkXkZgoSYF5YmWbNaBytbFHJtdTswbYGrk6VQdB04t89/1O/w1cDnyilFU=")

    val actions = listOf(PostbackAction("www", "{\"isToPay\":true,\"productId\":\"fafawfagva\",\"count\":\"%s\",\"quantityUnit\":\"price\"}", "測試1", "測試1"),
            PostbackAction("www", "{\"isToPay\":true,\"productId\":\"fafawfagva\",\"count\":\"%s\",\"quantityUnit\":\"price\"}", "測試2", "測試2"),
            PostbackAction("www", "{\"isToPay\":true,\"productId\":\"fafawfagva\",\"count\":\"%s\",\"quantityUnit\":\"price\"}", "測試3", "測試3"))

    val test = jacksonObjectMapper().writeValueAsString(ButtonsTemplate(URI("http://www.google.com"), "test", "tset", actions))
    println(test)
//    okHttp.postSyncJSON("https://api.line.me/v2/bot/message/push", body, headerMap)
//    OkHttpUtil().postAsyncJSON("https://api.line.me/v2/bot/message/push", body, headerMap, Callback())

}

private fun testUr(): URI {
    return ServletUriComponentsBuilder.fromHttpUrl("https://pic.52112.com/180324/180324_90")
            .scheme("https")
            .path("/XXe6cXhwQA_small.jpg").build()
            .toUri()
}


