package com.zipe.service

import com.zipe.entity.MessageSetting
import com.zipe.entity.Messages

interface IMessageSettingService {

    fun findBykey(key: String): MessageSetting

    fun findMessagesByMessageKey(key: String): List<Messages>
}
