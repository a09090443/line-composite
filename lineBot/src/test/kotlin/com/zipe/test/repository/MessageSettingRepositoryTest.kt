package com.zipe.test.repository

import com.zipe.entity.LineChannel
import com.zipe.entity.MessageDetail
import com.zipe.entity.MessageSetting
import com.zipe.enum.MessageType
import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.LineChannelJDBC
import com.zipe.repository.IMessageSettingRepository
import com.zipe.test.base.TestBase
import org.junit.Ignore
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.awt.TrayIcon
import java.nio.channels.Channel
import java.util.*

class MessageSettingRepositoryTest : TestBase() {

    @Autowired
    lateinit var messageSettingRepository: IMessageSettingRepository

    @Autowired
    lateinit var lineChannelJDBC: LineChannelJDBC

    @Test
    fun `jdbc test`(){

        val messageSetting = messageSettingRepository.findAllByKey("抽")
        println(messageSetting)
    }

    @Ignore
    @Test
    fun `insert new detail in exist key`() {

//        val detailList = mutableSetOf<MessageDetail>(MessageDetail(detailId = UUID.randomUUID().toString(), value = "https://i.imgur.com/cEEWLrj.jpg", type = "image"),
//                MessageDetail(detailId = UUID.randomUUID().toString(), value = "https://i.imgur.com/vU9X54K.jpg", type = "image"))
//        MessageSetting(key = "抽", messageDetails = detailList)
//        messageSettingRepository.save(MessageSetting(key = "抽", messageDetails = detailList))
    }
}
