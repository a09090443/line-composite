package com.zipe.model

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "hibernate")
data class HibernateProperties(
    var dialect: String = "",
    var showSql: Boolean = false,
    var formatSql: Boolean = false,
    var factoryClass: String = "",
    var useSecondLevelCache: Boolean = false,
    var useQueryCache: Boolean = false,
    var useMinimalPuts: Boolean = false,
    var hbm2ddlAuto: String = ""
)
