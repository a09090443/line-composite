package com.zipe.controller

import CrawlerRequest
import com.zipe.job.PTT_18_ACCESS_URL
import com.zipe.job.PTT_18_COOKIE_NAME
import com.zipe.job.PTT_BOARD_URL
import com.zipe.job.USER_AGENT
import com.zipe.service.ICrawlerService
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

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
