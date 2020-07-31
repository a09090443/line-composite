package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable

data class MessageSettingTest(

    var id: Long = 0,

    var messageId: String = "",

    var key: String = "",

    val comment: String = "",

    var messageDetails: Set<MessageDetail> = setOf()

) : Serializable, BaseEntity() {
}
