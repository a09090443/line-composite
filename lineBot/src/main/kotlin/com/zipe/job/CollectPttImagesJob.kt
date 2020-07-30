package com.zipe.job

import com.zipe.service.ICrawlerService
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired

const val PTT_DOMAIN = "https://www.ptt.cc"
const val PTT_18_ACCESS_URL = "$PTT_DOMAIN/ask/over18"
const val PTT_BOARD_URL = "$PTT_DOMAIN/bbs/%s/index.html"
const val PTT_18_COOKIE_NAME = "over18"
const val USER_AGENT = "Mozilla"

class CollectPttImagesJob : QuartzJobFactory() {
    @Autowired
    lateinit var crawlerService: ICrawlerService

    override fun executeJob(jobExecutionContext: JobExecutionContext?) {
        val jobMap = jobExecutionContext?.jobDetail?.jobDataMap ?: mapOf<String, Any>()
        var board = ""
        var deepLevel = 0
        if (jobMap.isNotEmpty()) {
            board = jobMap["board"].toString()
            deepLevel = jobMap["deepLevel"].toString().toInt()
        }

        var currentPage = getDoc(String.format(PTT_BOARD_URL, board))

        var previous: String

        val pages = mutableListOf<String>().apply {
            for (i in 0..1) {
                previous = currentPage.select("div[class=btn-group btn-group-paging]>a:contains(上頁)").attr("href")
                currentPage = getDoc("$PTT_DOMAIN$previous")
                this.add("$PTT_DOMAIN$previous")
            }
        }

        val links = pages.run {
            val allPages = mutableListOf<String>()
            this.forEach {
                allPages.addAll(
                    getDoc(it).select("div[class=r-ent]")
                        .map { element -> getSubjectpages("[正妹]", 30, element) }.toList()
                )
            }
            allPages
        }.toList()

        val images = mutableListOf<String>()
        links.filter { it.isNotBlank() }.forEach { link ->
            val image = getDoc("$PTT_DOMAIN$link").select("a[rel]").map { it.attr("href") }
                .filter { it.endsWith(".jpg") }
            images.addAll(image)
        }
        println(images)
        crawlerService.saveImageUrlFromPtt("1654386117", "抽", images)
    }
}

private fun getPttAdultCookie(): String {
    val response = Jsoup.connect(PTT_18_ACCESS_URL).data("yes", "yes").method(Connection.Method.POST).execute()
    return response.cookie(PTT_18_COOKIE_NAME)
}

private fun getSubjectpages(keyWord: String, minimalStars: Int, element: Element): String {
    val goodStars = element.select("div[class=nrec]").text()
    val title: String

    if (goodStars.isNotBlank() and !goodStars.startsWith("X")) {
        if (goodStars.toInt() > minimalStars) {
            title = element.select("div[class=title] > a[href]").text()
            if (title.contains(keyWord)) {
                return element.select("div[class=title] > a[href]").attr("href")
            }
        }
    }
    return ""
}

private fun getDoc(url: String) =
    Jsoup.connect(url).userAgent(USER_AGENT).cookie(PTT_18_COOKIE_NAME, getPttAdultCookie()).get()
