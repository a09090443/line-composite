package com.zipe.model

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application.cache")
data class CacheProperties(
    var enable: Boolean = true,
    var key: Map<String, String> = mapOf()
)
