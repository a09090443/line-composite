package com.zipe.test.util

import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

fun main() {
//    val sss = MessageType.getTypeName("IMAGE")
//    println(sss)
//    val str = jacksonObjectMapper().writeValueAsString(ImageMessage(URI("https://yukiblogtw.achangpro.com/img/a22978399d27_11F4E/DSCF2839.jpg"), URI("https://yukiblogtw.achangpro.com/img/a22978399d27_11F4E/DSCF2839.jpg")))
//    println(str)
//    val headerMap = mapOf<String, String>("Content-Type" to "application/json", "Authorization" to "Bearer D7YFymbHjichf2vTlzSCoTdcJtQjA/BeqmaphMKr+1VJs0y6OEDl3BILKtegGjlwWIuy8Xe9OonY6eq3+j+XlB7Pum5VMVqXpEsyz9W2JEpNvGKERTkXkZgoSYF5YmWbNaBytbFHJtdTswbYGrk6VQdB04t89/1O/w1cDnyilFU=")
//
//    val actions = listOf(PostbackAction("www", "{\"isToPay\":true,\"productId\":\"fafawfagva\",\"count\":\"%s\",\"quantityUnit\":\"price\"}", "測試1", "測試1"),
//            PostbackAction("www", "{\"isToPay\":true,\"productId\":\"fafawfagva\",\"count\":\"%s\",\"quantityUnit\":\"price\"}", "測試2", "測試2"),
//            PostbackAction("www", "{\"isToPay\":true,\"productId\":\"fafawfagva\",\"count\":\"%s\",\"quantityUnit\":\"price\"}", "測試3", "測試3"))
//
//    val test = jacksonObjectMapper().writeValueAsString(ButtonsTemplate(URI("http://www.google.com"), "test", "tset", actions))
//    println(test)
//    okHttp.postSyncJSON("https://api.line.me/v2/bot/message/push", body, headerMap)
//    OkHttpUtil().postAsyncJSON("https://api.line.me/v2/bot/message/push", body, headerMap, Callback())

    val s = 5
    val str = """{
      "type": "template",
      "altText": "確認付款",
      "template": {
        "type": "confirm",
        "actions": [
          {
            "type": "postback",
            "label": "是",
            "data": "{\"isToPay\":true,\"productId\":\"americano\",\"count\":\"${s}\",\"quantityUnit\":\"price\"}"
          },
          {
            "type": "postback",
            "label": "否",
            "data": "{\"isCancel\":true}"
          }
        ],
        "text": "您所輸入的數量為${s}杯，總金額為${s * 20}元，是否確認購買?"
      }
    }"""

    println(str)
}

private fun testUr(): URI {
    return ServletUriComponentsBuilder.fromHttpUrl("https://pic.52112.com/180324/180324_90")
        .scheme("https")
        .path("/XXe6cXhwQA_small.jpg").build()
        .toUri()
}


