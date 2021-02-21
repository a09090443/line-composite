package com.zipe.service.impl

import com.zipe.entity.MessageSetting
import com.zipe.entity.Messages
import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.BaseJDBC
import com.zipe.jdbc.criteria.Conditions
import com.zipe.repository.IMessageSettingRepository
import com.zipe.service.IMessageSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Service
class MessageSettingImpl : IMessageSettingService {

    @Autowired
    private lateinit var messageSettingRepository: IMessageSettingRepository

    @Autowired
    private lateinit var messageJDBC: BaseJDBC

    override fun findByName(name: String): MessageSetting {
        return messageSettingRepository.findAllByName(name) ?: MessageSetting()
    }

    override fun findMessages(name: String, channelId: String): List<Messages> {
        val conditions = Conditions()
        if(channelId.isNotBlank()){
            conditions.and().equal("md.channelId", channelId)
        }
        val resource: ResourceEnum = ResourceEnum.SQL_LINE.getResource("FIND_MESSAGES")
        val argMap = mapOf("name" to name)
        return messageJDBC.queryForList(resource, conditions, argMap, Messages::class.java)
    }

    private fun generateUrl(url: String, path: String): URI {
        return ServletUriComponentsBuilder.fromHttpUrl(url)
            .path(path).build()
            .toUri()
    }
}
