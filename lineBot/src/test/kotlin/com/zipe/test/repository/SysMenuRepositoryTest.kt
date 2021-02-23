package com.zipe.test.repository

import com.zipe.repository.ISysMenuRepository
import com.zipe.test.base.TestBase
import org.springframework.beans.factory.annotation.Autowired

class SysMenuRepositoryTest : TestBase() {

    @Autowired
    lateinit var sysMenuRepository: ISysMenuRepository

    @Test
    fun `find all of system menu`() {
        val sysMenus = sysMenuRepository.findByEnabledOrderByParentIdAscSequenceAsc(true)
    }

}
