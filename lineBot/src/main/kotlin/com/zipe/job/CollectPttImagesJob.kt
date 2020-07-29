package com.zipe.job

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.quartz.JobExecutionContext

const val PTT_DOMAIN = "https://www.ptt.cc"
const val PTT_18_ACCESS_URL = "$PTT_DOMAIN/ask/over18"
const val PTT_BOARD_URL = "$PTT_DOMAIN/bbs/%s/index.html"
const val PTT_18_COOKIE_NAME = "over18"
const val USER_AGENT = "Mozilla"

class CollectPttImagesJob : QuartzJobFactory() {
    override fun executeJob(jobExecutionContext: JobExecutionContext?) {
        val cookie: String = getPttAdultCookie()
        val jobMap = jobExecutionContext?.jobDetail?.jobDataMap ?: mapOf<String, Any>()
        var board = ""
        var deepLevel = 0
        if (jobMap.isNotEmpty()) {
            board = jobMap["board"].toString()
            deepLevel = jobMap["deepLevel"] as Int
        }

        var doc = Jsoup.connect(String.format(PTT_BOARD_URL, board))
            .userAgent(USER_AGENT).cookie(PTT_18_COOKIE_NAME, cookie).get()
        var previous = doc.select("div[class=btn-group btn-group-paging]>a:contains(上頁)").attr("href")
        for (i in 0 until deepLevel) {
            doc = Jsoup.connect("$PTT_DOMAIN$previous").userAgent(USER_AGENT).cookie(PTT_18_COOKIE_NAME, cookie).get()
            previous = doc.select("div[class=btn-group btn-group-paging]>a:contains(上頁)").attr("href")
            println(previous)

            val list = doc.select("div[class=r-ent]").forEach {
                val links = it.select("div[class=title]>a[href]")
                for (link in links) {
                    doc = Jsoup.connect("$PTT_DOMAIN${link.attr("href")}").userAgent("Mozilla")
                        .cookie(PTT_18_COOKIE_NAME, cookie).get()
                    println("link : ${link.attr("href")}")
                    val images = doc.select("a[rel]").map { image -> image.attr("href") }.toList()
                    println(images)
//                images.forEach {
//                    val href = it.attr("href")
//                    val text = it.text()
//                    println("$text - $href")
//                }


//                println("image : " + doc.select("img[src$=.jpg]"))
                }
            }
        }
    }
}

private fun getPttAdultCookie(): String {
    val response = Jsoup.connect(PTT_18_ACCESS_URL).data("yes", "yes").method(Connection.Method.POST).execute()
    return response.cookie(PTT_18_COOKIE_NAME)
}
