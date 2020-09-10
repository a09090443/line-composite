package com.zipe.test.repository

import com.google.gson.Gson
import com.zipe.model.OrderProcessRequest
import com.zipe.model.PaymentRequest
import com.zipe.repository.IOrderProcessRepository
import com.zipe.repository.ISysMenuRepository
import com.zipe.test.base.TestBase
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

class SysMenuRepositoryTest : TestBase() {

    @Autowired
    lateinit var sysMenuRepository: ISysMenuRepository

    @Test
    fun `find all of system menu`() {
        val sysMenus = sysMenuRepository.findAll()
        println(sysMenus)
    }

}
