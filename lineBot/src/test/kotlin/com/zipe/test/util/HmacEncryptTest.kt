package com.zipe.test.util

import com.google.common.net.HttpHeaders
import com.zipe.test.base.TestBase
import com.zipe.util.AUTHORIZATION
import com.zipe.util.CHANNEL_ID
import com.zipe.util.NONCE
import com.zipe.util.crypto.HmacEncryptUtil
import com.zipe.util.http.OkHttpUtil
import okhttp3.Response
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class HmacEncryptTest(val okHttpUtil: OkHttpUtil) : TestBase() {

    @Test
    fun `test to send order to line pay api`() {
        val json = """
{"amount":3,"currency":"TWD","orderId":"374342cf-48af-47d5-99b4-0e18d5421df3","packages":[{"id":"c8ee1fb3-0027-40fd-91cc-1f6e15699154","name":"Kotlin咖啡廳","amount":3,"userFee":0,"products":[{"id":"americano","name":"美式咖啡","imageUrl":"","quantity":1,"price":3,"originalPrice":0}]}],"redirectUrls":{"confirmUrl":"https://8614d5373d99.ngrok.io/line/payment/confirm","cancelUrl":"https://8614d5373d99.ngrok.io/line/payment/cancel"}}""".trimIndent()
        val channelSecret = ""
        val requestUri = "/v3/payments/request"
        val nonce = UUID.randomUUID().toString()
        println("nonce : $nonce")
        val signature: String = HmacEncryptUtil.encrypt(channelSecret, channelSecret + requestUri + json + nonce)
        println("signature : $signature")
        val headerMap = mapOf("Content-Type" to "application/json",
            "X-LINE-ChannelId" to "1654394737",
            "X-LINE-Authorization-Nonce" to nonce,
            "X-LINE-Authorization" to signature)
//        val result = okHttpUtil.postSyncJSON("https://sandbox-api-pay.line.me/v3/payments/request", json, headerMap)
//        println(result)
//        okHttpUtil.initGetRequest("https://sandbox-api-pay.line.me/v3/payments/request")
//        val test = okHttpUtil.getSync("https://sandbox-api-pay.line.me/v3/payments/request")

    }

    @Test
    fun `test to send message to line message api`() {

        val headerMap = mapOf("Content-Type" to "application/json", "Authorization" to "Bearer D7YFymbHjichf2vTlzSCoTdcJtQjA/BeqmaphMKr+1VJs0y6OEDl3BILKtegGjlwWIuy8Xe9OonY6eq3+j+XlB7Pum5VMVqXpEsyz9W2JEpNvGKERTkXkZgoSYF5YmWbNaBytbFHJtdTswbYGrk6VQdB04t89/1O/w1cDnyilFU=")
        val body = """
        {
            "to":"U21d2289790fe0d2e5d01e20a314a8caa",
            "messages":[{
          "type": "template",
          "altText": "this is a carousel template",
          "template": {
            "type": "carousel",
            "actions": [],
            "columns": [
              {
                "thumbnailImageUrl": "https://yukiblogtw.achangpro.com/img/a22978399d27_11F4E/DSCF2839.jpg",
                "title": "杯子蛋糕",
                "text": "跟碗公一樣大的蛋糕",
                "actions": [
                  {
                    "type": "uri",
                    "label": "100000元",
                    "uri": "https://www.google.com"
                  }
                ]
              },
              {
                "thumbnailImageUrl": "https://cc.tvbs.com.tw/img/upload/2020/05/13/20200513213230-d92ab512.jpg",
                "title": "超完美網咖泡麵",
                "text": "只能在網咖才能買到的美食",
                "actions": [
                  {
                    "type": "uri",
                    "label": "10000000元",
                    "uri": "https://www.google.com"
                  }
                ]
              },
              {
                "thumbnailImageUrl": "https://yukiblogtw.achangpro.com/img/a22978399d27_11F4E/DSCF2839.jpg",
                "title": "肥宅快樂水",
                "text": "肥宅聖品之一",
                "actions": [
                  {
                    "type": "uri",
                    "label": "100000000元",
                    "uri": "https://www.google.com"
                  }
                ]
              },
              {
                "thumbnailImageUrl": "https://yukiblogtw.achangpro.com/img/a22978399d27_11F4E/DSCF2839.jpg",
                "title": "炭火神豬",
                "text": "此乃天上神豬，吃完可回春",
                "actions": [
                  {
                    "type": "uri",
                    "label": "10000000000元",
                    "uri": "http://www.google.com"
                  }
                ]
              }
            ]
          }
        }]
        }
    """.trimIndent()
        okHttpUtil.postAsyncJSON("https://api.line.me/v2/bot/message/push", body, headerMap, Callback())

//        okHttpUtil.postSyncJSON("https://api.line.me/v2/bot/message/push", body, headerMap)
    }
}

class Callback : OkHttpUtil.IOkHttpCallback {
    override fun onResponse(response: Response) {
        println("SUCCESS")
        println("SUCCESS")
        println("SUCCESS")
        println("SUCCESS")
        println("SUCCESS")
        println("SUCCESS")
    }

    override fun onFailure(e: IOException) {
        println("FAILURE")
        println("FAILURE")
        println("FAILURE")
        println("FAILURE")
        println("FAILURE")
    }

}
