package com.zipe.repository

import com.zipe.entity.MessageSetting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IMessageSettingRepository : JpaRepository<MessageSetting, Long> {

    fun findAllByName(name: String): MessageSetting?

    fun findTopByOrderByNameDesc(): MessageSetting?

}
