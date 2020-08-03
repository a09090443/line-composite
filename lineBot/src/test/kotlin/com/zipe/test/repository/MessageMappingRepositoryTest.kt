package com.zipe.test.repository

import com.zipe.entity.MessageMapping
import com.zipe.repository.IMessageDetailRepository
import com.zipe.repository.IMessageMappingRepository
import com.zipe.test.base.TestBase
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class MessageMappingRepositoryTest : TestBase() {

    @Autowired
    lateinit var messageMappingRepository: IMessageMappingRepository

    @Test
    fun `save new mapping`() {

        for(i in 0..5){
            messageMappingRepository.save(MessageMapping(messageId = "00002", detailId = String.format("%06d", i)))
        }
    }
}
