package com.zipe.test.repository

import com.zipe.repository.IMessageDetailRepository
import com.zipe.test.base.TestBase
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class MessageDetailRepositoryTest : TestBase() {

    @Autowired
    lateinit var messageDetailRepository: IMessageDetailRepository

    @Test
    fun `find max detail id`() {
        val test = String.format("%09d", 122)
        val test2 = test.trimStart('0')
        println(test)
        val maxDetailId = messageDetailRepository.findTopByOrderByDetailIdDesc()
        println(maxDetailId)
    }
}
