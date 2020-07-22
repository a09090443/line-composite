package com.zipe.config

import com.zipe.model.CacheProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.support.NoOpCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableConfigurationProperties(CacheProperties::class)
@EnableCaching
class RedisConfig : CachingConfigurerSupport() {
    @Autowired
    lateinit var cacheProperties: CacheProperties

    @Bean
    fun redisCacheConfiguration(): RedisCacheConfiguration? {
        var cacheConfiguration =
            RedisCacheConfiguration.defaultCacheConfig()
        //Expire time
        cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofMinutes(1))
        //Fix cache error while hot deploy
        val redisSerializer =
            JdkSerializationRedisSerializer(javaClass.classLoader)
        val serializationPair: SerializationPair<*> = SerializationPair.fromSerializer(redisSerializer)
        cacheConfiguration = cacheConfiguration.serializeValuesWith(serializationPair)
        return cacheConfiguration
    }

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, String> {

        return RedisTemplate<String, String>().apply {
            this.setConnectionFactory(factory)
            this.keySerializer = StringRedisSerializer();
            this.valueSerializer = StringRedisSerializer();
            this.hashKeySerializer = StringRedisSerializer();
            this.setHashValueSerializer(StringRedisSerializer());
        }
    }

    @Bean
    fun cacheManager(factory: RedisConnectionFactory): CacheManager {

        return if (cacheProperties.enable) {
            //Fix cache error while hot deploy
            val redisSerializer = JdkSerializationRedisSerializer(javaClass.classLoader)

            val serializationPair: SerializationPair<*> = SerializationPair.fromSerializer(redisSerializer)

            val defaultCacheConfig =
                RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(serializationPair).entryTtl(
                    Duration.ofHours(1)
                )


            val initialCacheConfiguration = cacheProperties.key.map { (k, v) ->
                k to RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.parse(v))
            }.toMap()

            RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .cacheDefaults(defaultCacheConfig).withInitialCacheConfigurations(initialCacheConfiguration).build()
        } else {
            NoOpCacheManager()
        }

    }

}
