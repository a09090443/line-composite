package com.zipe.config.base

import com.zaxxer.hikari.HikariConfig
import com.zipe.model.HibernateProperties
import org.hibernate.cfg.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean

open class BaseDataSourceConfig {

    @Autowired
    lateinit var hibernateProperties: HibernateProperties

    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    @Bean("baseHikariConfig")
    open fun baseHikariConfig(): HikariConfig? {
        return HikariConfig()
    }

    open fun hibernateConfig(): MutableMap<String, String?> {

        val jpaProperties: MutableMap<String, String?> = mutableMapOf()
        jpaProperties[Environment.DIALECT] = hibernateProperties.dialect
        jpaProperties[Environment.FORMAT_SQL] = hibernateProperties.showSql.toString()
        jpaProperties[Environment.CACHE_REGION_FACTORY] = hibernateProperties.factoryClass
        jpaProperties[Environment.USE_SECOND_LEVEL_CACHE] = hibernateProperties.useSecondLevelCache.toString()
        jpaProperties[Environment.USE_QUERY_CACHE] = hibernateProperties.useQueryCache.toString()
        jpaProperties[Environment.USE_MINIMAL_PUTS] = hibernateProperties.useMinimalPuts.toString()
        return jpaProperties
    }
}
