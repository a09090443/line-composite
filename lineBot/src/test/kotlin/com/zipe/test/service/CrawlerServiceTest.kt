package com.zipe.test.service

import com.zipe.Application
import com.zipe.model.LineProperties
import com.zipe.model.SystemConfigProperties
import com.zipe.service.ICrawlerService
import com.zipe.test.base.TestBase
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [Application::class])
@ExtendWith(SpringExtension::class)
class CrawlerServiceTest(
    val crawlerServiceImpl: ICrawlerService,
    val lineProperties: LineProperties,
    var systemConfigProperties: SystemConfigProperties
) : TestBase() {
    @Test
    fun test() {
        println(lineProperties.pushMessageUrl)
        println(systemConfigProperties.jobUsingDatabase)
        crawlerServiceImpl.crawlerPttBoard("Beauty", listOf("正妹"), 1)
    }
}
