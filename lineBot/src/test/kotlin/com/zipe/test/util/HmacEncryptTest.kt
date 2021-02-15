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

class HmacEncryptTest : TestBase() {

    @Autowired
    lateinit var okHttpUtil: OkHttpUtil

    @Test
    fun `test to send order to line pay api`() {
        val test = LocalDateTime.parse(
            "2020-09-17 22:43:00",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.TAIWAN)
        )
        println(test)
//        val form = CheckoutPaymentRequestForm(amount = BigDecimal("100"), currency = "TWD", orderId = UUID.randomUUID().toString())
//
//        val productPackageForm = ProductPackageForm(id = "44433234dsa", name = "shop_name", amount = BigDecimal("100"))
//
//        val productForm = PaymentProduct(id = "product_id", name = "product_name", quantity = 10, price = BigDecimal("10"))
//
//        productPackageForm.products = listOf(productForm)
//        form.packages = listOf(productPackageForm)
//        val redirectUrls = RedirectUrls(confirmUrl = "http://localhost/confirm", cancelUrl = "http://localhost/cencel")
//        form.redirectUrls = redirectUrls
        val json = """
{"amount":1936,"currency":"TWD","orderId":"374342cf-48af-47d5-99b4-0e18d5421df3","packages":[{"id":"76515b4f-4b23-40b1-98c8-3789c6c698c9","name":"健人工程師","amount":1936,"userFee":0,"products":[{"id":"fafawfagva","name":"捐款","imageUrl":"https://www.markgaler.com/wp-content/uploads/2017/06/Donate.jpg","quantity":1,"price":44,"originalPrice":0}]}],"redirectUrls":{"confirmUrl":"https://11dc488b8469.ngrok.io/line/payment/confirm","cancelUrl":"https://11dc488b8469.ngrok.io/line/payment/cancel"}}        """.trimIndent()
        val ChannelSecret = "3dc8c4282cf38fb050110542f973d5a7"
        val requestUri = "/v3/payments/request"
        val nonce = UUID.randomUUID().toString()
        println("nonce : $nonce")
//        println(json)
        val signature: String = HmacEncryptUtil.encrypt(ChannelSecret, ChannelSecret + requestUri + json + nonce)
        println(signature)
        val headerMap = mapOf(HttpHeaders.CONTENT_TYPE to MediaType.APPLICATION_JSON_VALUE, CHANNEL_ID to "1654394737",
                NONCE to nonce, AUTHORIZATION to signature)
        val result = okHttpUtil.postSyncJSON("https://sandbox-api-pay.line.me/v3/payments/request", json, headerMap)
        println(result)
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
