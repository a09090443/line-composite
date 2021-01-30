package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class MessageDetail : Serializable, BaseEntity() {

    @Id
    var id: String = ""

    var value: String = ""

    var type: String = ""

    var channelId: String = ""
}

fun MessageDetail.asObject(value: String, type: String, channelId: String) = MessageDetail().apply {
    this.value = value
    this.type = type
    this.channelId = channelId
}
