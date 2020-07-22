package com.zipe.repository

import com.zipe.entity.LineStore
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ILineStoreRepository : CrudRepository<LineStore, Long> {

    @Cacheable(cacheNames = ["lineStore"])
    fun findByLineId(lineId: Long): LineStore
}
