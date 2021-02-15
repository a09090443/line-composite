package com.zipe.service

import com.zipe.entity.MessageSetting
import com.zipe.entity.Messages

interface IMessageSettingService {

    fun findByName(name: String): MessageSetting

    fun findMessagesByMessageKey(key: String): List<Messages>
}
