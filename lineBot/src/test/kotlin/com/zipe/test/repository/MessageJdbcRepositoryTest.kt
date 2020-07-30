package com.zipe.test.repository

import com.zipe.entity.MessageSetting
import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.LineChannelJDBC
import com.zipe.jdbc.MessageJDBC
import com.zipe.test.base.TestBase
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class MessageJdbcRepositoryTest : TestBase() {
    @Autowired
    lateinit var messageJDBC: MessageJDBC

    @Test
    fun getMessageSettingTest() {
        val resource: ResourceEnum = ResourceEnum.SQL_LINE.getResource("FIND_MESSAGE")
        val test = messageJDBC.queryForBean(resource, MessageSetting::class.java)
        println(test)
    }
}
