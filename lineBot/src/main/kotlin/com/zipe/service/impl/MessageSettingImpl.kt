package com.zipe.service.impl

import com.zipe.entity.MessageSetting
import com.zipe.entity.Messages
import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.BaseJDBC
import com.zipe.repository.IMessageSettingRepository
import com.zipe.service.IMessageSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Transactional
@Service
class MessageSettingImpl : IMessageSettingService {

    @Autowired
    private lateinit var messageSettingRepository: IMessageSettingRepository

    @Autowired
    private lateinit var messageJDBC: BaseJDBC

    override fun findBykey(key: String): MessageSetting {
        return messageSettingRepository.findAllByKey(key) ?: MessageSetting()
    }

    override fun findMessagesByMessageKey(key: String): List<Messages> {
        val resource: ResourceEnum = ResourceEnum.SQL_LINE.getResource("FIND_MESSAGES")
        val argMap = mapOf("key" to key)
        return messageJDBC.queryForList(resource, null, argMap, Messages::class.java)
    }

    private fun generateUrl(url: String, path: String): URI {
        return ServletUriComponentsBuilder.fromHttpUrl(url)
            .path(path).build()
            .toUri()
    }
}
