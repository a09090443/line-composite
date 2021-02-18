package com.zipe.test.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
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

        val objectMapper = ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE) // Register ParameterNamesModule to read parameter name from lombok generated constructor.
            .registerModule(ParameterNamesModule()) // Register JSR-310(java.time.temporal.*) module and read number as millsec.
            .registerModule(JavaTimeModule())
            .configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
        val s: String = objectMapper.writeValueAsString(message)

        crawlerServiceImpl.crawlerPttBoard("Beauty", listOf("正妹"), 1)
    }
})
