package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "message_detail")
data class MessageDetail(

    @Column(name = "id")
    var id: Long = 0,

    @Id
    @Column(name = "detail_id")
    var detailId: String = "",

    @Column(name = "value")
    val value: String = "",

    @Column(name = "type")
    val type: String = "",

    @Column(name = "channel_id")
    val channelId: String = ""

) : Serializable, BaseEntity()
