package com.zipe.service

import com.zipe.entity.MessageSetting

interface IMessageSettingService {

    fun findBykey(key: String): MessageSetting

}
