package com.zipe.test.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.linecorp.bot.model.message.ImageMessage
import com.zipe.Application
import com.zipe.service.ICrawlerService
import io.kotest.core.spec.style.FunSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.net.URI

@SpringBootTest(classes = [Application::class])
@ExtendWith(SpringExtension::class)
class CrawlerServiceTest(val crawlerServiceImpl: ICrawlerService) : FunSpec({

    test("crawler ptt Beauty board")
    {
        val message = ImageMessage(
            URI.create("https://example.com/"),
            URI.create("https://example.com/preview")
        )
        val gson: Gson = GsonBuilder().setPrettyPrinting().create()
        gson.toJson(message)
        crawlerServiceImpl.crawlerPttBoard("Beauty", listOf("正妹"), 1)
    }
})
