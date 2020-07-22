package com.zipe.repository

import com.zipe.entity.MessageSetting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface IMessageSettingRepository : JpaRepository<MessageSetting, Long> {
    fun findAllByKey(key: String): MessageSetting?
//    @Query("FROM MessageSetting ms ")
//    fun findByMessageDetail(@Param("name") name: String, @Param("type") type: String): MessageSetting?
}
