package com.zipe.test.service

import com.zipe.service.ICrawlerService
import com.zipe.test.base.TestBase

class CrawlerServiceTest(
    val crawlerServiceImpl: ICrawlerService
) : TestBase() {
    @Test
    fun test() {
        crawlerServiceImpl.crawlerPttBoard("Beauty", listOf("正妹"), 1)
    }
}
