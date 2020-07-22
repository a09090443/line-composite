package com.zipe.test.util

import com.zipe.test.base.TestBase
import org.junit.Ignore
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class RedisTest : TestBase() {
    @Autowired
    lateinit var redisTemplate: RedisTemplate<String, String>

    @Test
    fun `redis insert test`() {

//        val properties = mapOf<String, Any>("zipe" to "superman", "捐款" to "10")
//
//        redisTemplate.opsForHash<String, Any>().putAll("aaa:test", properties)
//
//        val result = redisTemplate.opsForHash<String, Any>().entries("aaa:test")
//        println(result)
        val testList = listOf<String>("test1", "test2")
        redisTemplate.opsForList().rightPushAll("process:zipe", testList)

        val result = redisTemplate.opsForList().index("process:zipe", 0)
        println(result.isNullOrEmpty())
    }

//    @Test
    fun `redis remove test`() {
        redisTemplate.delete("aaa:test")
    }
}
