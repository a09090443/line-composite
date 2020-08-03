package com.zipe.repository

import com.zipe.entity.MessageMapping
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface IMessageMappingRepository : CrudRepository<MessageMapping, Long> {

    @Query("SELECT mm.detailId FROM MessageMapping mm WHERE mm.messageId = :messageId")
    fun findDetailIdsByMessageId(@Param("messageId") messageId: String): List<String>?

    fun deleteMessageMappingByMessageId(messageId: String)

}
