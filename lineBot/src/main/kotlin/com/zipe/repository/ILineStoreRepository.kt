package com.zipe.repository

import com.zipe.entity.LineStore
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ILineStoreRepository : JpaRepository<LineStore, Long> {

    @Cacheable(cacheNames = ["lineStore"])
    fun findByChannelId(channelId: String): LineStore
}
