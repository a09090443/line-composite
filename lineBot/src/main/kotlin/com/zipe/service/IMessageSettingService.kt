package com.zipe.service

import com.zipe.entity.MessageSetting
import com.zipe.entity.Messages

interface IMessageSettingService {

    fun findByName(name: String): MessageSetting

    fun findMessages(name: String, channelId: String): List<Messages>
}
