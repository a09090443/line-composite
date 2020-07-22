package com.zipe.service.impl

import com.zipe.entity.MessageSetting
import com.zipe.repository.IMessageSettingRepository
import com.zipe.service.ICrawlerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service("crawlerService")
class CrawlerServiceImpl : ICrawlerService {

    @Autowired
    private lateinit var messageSettingRepository: IMessageSettingRepository

    override fun saveImageUrlFromPtt(channelId: String, key: String, images: List<String>) {

        images.filter { it.endsWith(".jpg") }.forEach {

        }
    }

}
