package com.zipe.service.impl

import com.zipe.entity.MessageSetting
import com.zipe.repository.IMessageSettingRepository
import com.zipe.service.IMessageSettingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Transactional
@Service("messageService")
class MessageSettingImpl : IMessageSettingService {

    @Autowired
    private lateinit var messageSettingRepository: IMessageSettingRepository

    override fun findBykey(key: String): MessageSetting {
        return messageSettingRepository.findAllByKey(key) ?: MessageSetting()
    }

    private fun generateUrl(url: String, path: String): URI {
        return ServletUriComponentsBuilder.fromHttpUrl(url)
                .path(path).build()
                .toUri()
    }
}
