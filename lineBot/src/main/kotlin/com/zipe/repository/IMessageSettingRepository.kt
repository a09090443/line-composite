package com.zipe.repository

import com.zipe.entity.MessageSetting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IMessageSettingRepository : JpaRepository<MessageSetting, Long> {

    fun findAllByKey(key: String): MessageSetting?

    fun findTopByOrderByMessageIdDesc(): MessageSetting?

}
