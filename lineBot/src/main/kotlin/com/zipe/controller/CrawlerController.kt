package com.zipe.controller

import com.zipe.request.CrawlerRequest
import com.zipe.service.ICrawlerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/crawler"])
class CrawlerController {

    @Autowired
    lateinit var crawlerService: ICrawlerService

    @PostMapping("/addImages")
    fun addImages(@RequestBody crawlerRequest: CrawlerRequest) {

    }

}
