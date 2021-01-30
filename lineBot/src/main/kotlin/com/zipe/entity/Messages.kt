package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable

data class Messages(

    var key: String = "",

    var value: String = "",

    var type: String = "",

    val comment: String = "",

    var messageDetails: Set<MessageDetail> = setOf()

) : Serializable, BaseEntity() {
}
