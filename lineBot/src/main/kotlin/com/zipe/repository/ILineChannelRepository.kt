package com.zipe.repository

import com.zipe.entity.LineChannel
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ILineChannelRepository : JpaRepository<LineChannel, Long> {

    @Cacheable(cacheNames = ["channel"])
    fun findByChannelSecret(channelSecret: String): LineChannel

    @Cacheable(cacheNames = ["channel"])
    fun findByName(name: String): LineChannel

    @Cacheable(cacheNames = ["channel"])
    fun findByChannelId(channelId: String): LineChannel

}
