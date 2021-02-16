package com.zipe.job

import com.zipe.service.ICrawlerService
import com.zipe.util.common.COMMA
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CollectPttImagesJob : QuartzJobFactory() {

    @Autowired
    lateinit var crawlerServiceImpl: ICrawlerService

    override fun executeJob(jobExecutionContext: JobExecutionContext) {
        val jobMap = jobExecutionContext.jobDetail?.jobDataMap ?: mapOf<String, Any>()
        var board = ""
        var keyWords = ""
        var deepLevel = 1
        if (jobMap.isNotEmpty()) {
            board = jobMap["board"].toString()
            keyWords = jobMap["keyWords"].toString()
            deepLevel = jobMap["deepLevel"].toString().toInt()
        }
        crawlerServiceImpl.crawlerPttBeautyBoard(board, keyWords.split(COMMA).toList(), deepLevel)
    }
}
