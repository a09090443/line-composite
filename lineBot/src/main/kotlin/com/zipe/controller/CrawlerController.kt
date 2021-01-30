package com.zipe.controller

import CrawlerRequest
import com.zipe.service.ICrawlerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

const val PTT_DOMAIN = "https://www.ptt.cc"
const val PTT_18_ACCESS_URL = "$PTT_DOMAIN/ask/over18"
const val PTT_BOARD_URL = "$PTT_DOMAIN/bbs/%s/index.html"
const val PTT_18_COOKIE_NAME = "over18"
const val USER_AGENT = "Mozilla"

@RestController
@RequestMapping(value = ["/crawler"])
class CrawlerController {

    @Autowired
    lateinit var crawlerService: ICrawlerService

    @PostMapping("/addImages")
    fun addImages(@RequestBody crawlerRequest: CrawlerRequest) {

    }

}
