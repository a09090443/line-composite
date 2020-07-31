package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "message_mapping")
data class MessageMapping(

        @Id
        @Column(name = "message_id")
        val messageId: String = "",

        @Column(name = "detail_id")
        var detailId: String = ""

) : Serializable, BaseEntity()
