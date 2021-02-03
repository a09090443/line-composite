package com.zipe.repository

import com.zipe.entity.LineMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ILineMappingRepository : JpaRepository<LineMapping, Long> {

    fun deleteByChannelIdAndInfoId(channelId: String, infoId: String)
}
