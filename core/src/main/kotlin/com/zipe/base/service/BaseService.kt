package com.zipe.base.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import java.util.*

open class BaseService {

    @Autowired
    private lateinit var messageSource: MessageSource

    protected fun getMessage(str: String): String {
        return messageSource.getMessage(str, null, Locale.TAIWAN)
    }
}
