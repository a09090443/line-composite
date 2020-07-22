package com.zipe.test.repository

import com.google.gson.Gson
import com.zipe.model.OrderProcessRequest
import com.zipe.model.PaymentRequest
import com.zipe.repository.IOrderProcessRepository
import com.zipe.test.base.TestBase
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class ProductOrderProcessRepositoryTest : TestBase() {

    @Autowired
    lateinit var orderProcessRepository: IOrderProcessRepository

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @Test
    fun `find order process tree view`() {
        val testJson = """
                  {
                    "type": "postback",
                    "label": "捐款",
                    "data": "{\"isPaymentProcess\":true,\"productName\":\"donate\"}"
                  }
        """.trimIndent()

        val payment = Gson().fromJson(testJson, OrderProcessRequest::class.java)
        println(payment)
//        val orderProcesses = orderProcessRepository.findByProcessIdAndLineIdOrderBySequence(1, 1).forEach {
//            val json = Gson().toJson(it)
//            redisTemplate.opsForList().rightPush("process:zipe", json)
//        }
//
//        val test = redisTemplate.opsForList().index("process:zipe", 0)
//        val ttt = redisTemplate.opsForList().leftPop("process:zipe")
//        println(ttt)
    }

}
