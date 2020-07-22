package com.zipe.util

import com.linecorp.bot.model.objectmapper.ModelObjectMapper

object JsonUtil {
    fun lineJsonMapper() = ModelObjectMapper.createNewObjectMapper()
}
