package com.zipe.test.repository

import com.zipe.entity.Messages
import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.MessageJDBC
import com.zipe.test.base.TestBase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class MessageJdbcRepositoryTest : TestBase() {
    @Autowired
    lateinit var messageJDBC: MessageJDBC

    @Test
    fun getMessageSettingTest() {
        val resource: ResourceEnum = ResourceEnum.SQL_LINE.getResource("FIND_MESSAGE")
        val argMap = mapOf("key" to "抽")
        val test = messageJDBC.queryForList(resource, Messages::class.java)
        println(test)
    }
}
