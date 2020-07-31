package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "message_detail")
class MessageDetail : Serializable, BaseEntity() {

    @Column(name = "id")
    var id: Long = 0

    @Id
    @Column(name = "detail_id")
    var detailId: String = ""

    @Column(name = "value")
    var value: String = ""

    @Column(name = "type")
    var type: String = ""

    @Column(name = "channel_id")
    var channelId: String = ""

}

fun MessageDetail.asObject(detailId: String, value: String, type: String, channelId: String) = MessageDetail().apply {
    this.detailId = detailId
    this.value = value
    this.type = type
    this.channelId = channelId
}
