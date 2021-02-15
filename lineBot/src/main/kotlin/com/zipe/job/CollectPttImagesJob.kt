package com.zipe.job

import com.zipe.service.ICrawlerService
import com.zipe.util.common.DOT
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired

const val PTT_DOMAIN = "https://www.ptt.cc"
const val PTT_18_ACCESS_URL = "$PTT_DOMAIN/ask/over18"
const val PTT_BOARD_URL = "$PTT_DOMAIN/bbs/%s/index.html"
const val PTT_18_COOKIE_NAME = "over18"
const val USER_AGENT = "Mozilla"
const val IMAGE_JSON = """{
  "type": "image",
  "originalContentUrl": "%s",
  "previewImageUrl" : "%s"
}"""

class CollectPttImagesJob : QuartzJobFactory() {
    @Autowired
    lateinit var crawlerService: ICrawlerService

    override fun executeJob(jobExecutionContext: JobExecutionContext?) {
        val jobMap = jobExecutionContext?.jobDetail?.jobDataMap ?: mapOf<String, Any>()
        var board = ""
        var keyWords = ""
        var deepLevel = 1
        if (jobMap.isNotEmpty()) {
            board = jobMap["board"].toString()
            keyWords = jobMap["keyWords"].toString()
            deepLevel = jobMap["deepLevel"].toString().toInt()
        }
        crawlerService.crawlerPttBeautyBoard(board, keyWords.split(DOT).toList(), deepLevel)
    }
}
