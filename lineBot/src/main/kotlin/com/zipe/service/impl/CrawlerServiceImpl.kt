package com.zipe.service.impl

import com.zipe.entity.MessageDetail
import com.zipe.entity.MessageMapping
import com.zipe.entity.MessageSetting
import com.zipe.entity.asObject
import com.zipe.repository.IMessageDetailRepository
import com.zipe.repository.IMessageMappingRepository
import com.zipe.repository.IMessageSettingRepository
import com.zipe.service.ICrawlerService
import org.apache.commons.lang.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service("crawlerService")
class CrawlerServiceImpl : ICrawlerService {

    @Autowired
    lateinit var messageSettingRepository: IMessageSettingRepository

    @Autowired
    lateinit var messageDetailRepository: IMessageDetailRepository

    @Autowired
    lateinit var messageMappingRepository: IMessageMappingRepository

    @Throws(Exception::class)
    override fun saveImageUrlFromPtt(channelId: String, key: String, images: List<String>) {

        val messageSetting = messageSettingRepository.findAllByKey(key) ?: saveMessageSetting(key)
        messageMappingRepository.deleteMessageMappingByMessageId(messageSetting.messageId)

        messageMappingRepository.findDetailIdsByMessageId(messageSetting.messageId)
            ?.map { messageDetailRepository.deleteById(it) }

        var messageDetail: MessageDetail
        images.forEach {
            messageDetail = MessageDetail().asObject(it, "image", channelId)
            messageDetailRepository.save(messageDetail)
            try {
                messageMappingRepository.save(
                    MessageMapping(
                        messageId = messageSetting.messageId,
                        detailId = String.format("%05d", messageDetail.channelId)
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    private fun saveMessageSetting(key: String): MessageSetting {
        val messageSetting = getNewMessageId().let {
            MessageSetting().asObject(String.format("%5d", it), key, StringUtils.EMPTY)
        }
        messageSettingRepository.save(messageSetting)
        return messageSetting
    }

    private fun getNewMessageId() =
        messageSettingRepository.findTopByOrderByMessageIdDesc()?.messageId?.trimStart('0')?.toLong()?.plus(1) ?: 1
}
