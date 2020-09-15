package com.zipe.test.service

import com.zipe.service.ISysMenuService
import com.zipe.test.base.TestBase
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

class SysMenuServiceTest : TestBase() {

    @Autowired
    lateinit var sysMenuService: ISysMenuService

    @Test
    fun `test get system menu tree`() {
        sysMenuService.getSysMenuTree()
    }
}
