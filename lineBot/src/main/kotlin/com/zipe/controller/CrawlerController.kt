package com.zipe.controller

import CrawlerRequest
import com.zipe.service.ICrawlerService
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

const val PTT_DOMAIN = "https://www.ptt.cc"
const val PTT_ADULT_URL = "$PTT_DOMAIN/ask/over18"
const val PTT_ADULT_COOKIE_KEY = "over18"

@RestController
@RequestMapping(value = ["/crawler"])
class CrawlerController {

    @Autowired
    lateinit var crawlerService: ICrawlerService

    @PostMapping("/addImages")
    fun addImages(@RequestBody crawlerRequest: CrawlerRequest) {
        var doc = Jsoup.connect("$PTT_DOMAIN/bbs/Beauty/index.html")
                .userAgent("Mozilla").cookie(PTT_ADULT_COOKIE_KEY, pttCookie()).get()
        var previous = doc.select("div[class=btn-group btn-group-paging]>a:contains(上頁)").attr("href")
        var images = listOf<String>()
        for (i in 0 until 5) {
            doc = Jsoup.connect("$PTT_DOMAIN$previous").userAgent("Mozilla").cookie("over18", pttCookie()).get()
            previous = doc.select("div[class=btn-group btn-group-paging]>a:contains(上頁)").attr("href")
            println(previous)

            doc.select("div[class=r-ent]").forEach {
                val links = it.select("div[class=title]>a[href]")
                for (link in links) {
                    doc = Jsoup.connect("$PTT_DOMAIN${link.attr("href")}").userAgent("Mozilla").cookie("over18", pttCookie()).get()
                    println("link : ${link.attr("href")}")
                    images = doc.select("a[rel]").map { url -> url.attr("href") }.toList()
                    crawlerService.saveImageUrlFromPtt(crawlerRequest.channelId, crawlerRequest.key, images)
                }
            }
        }
    }

    private fun pttCookie(): String {
        val response = Jsoup.connect(PTT_ADULT_URL).data("yes", "yes").method(Connection.Method.POST).execute()
        return response.cookie(PTT_ADULT_COOKIE_KEY)
    }
}
