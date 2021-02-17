package com.zipe.test.service

import com.zipe.Application
import com.zipe.service.ICrawlerService
import io.kotest.core.spec.style.FunSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(classes = [Application::class])
@ExtendWith(SpringExtension::class)
class CrawlerServiceTest(val crawlerServiceImpl: ICrawlerService) : FunSpec({

    test("crawler ptt Beauty board")
    {
        crawlerServiceImpl.crawlerPttBeautyBoard("Beauty", listOf("正妹"), 1)
    }
})
