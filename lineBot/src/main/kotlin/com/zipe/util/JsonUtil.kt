package com.zipe.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.linecorp.bot.model.objectmapper.ModelObjectMapper

object JsonUtil {
    fun lineJsonMapper(): ObjectMapper = ModelObjectMapper.createNewObjectMapper()
}
