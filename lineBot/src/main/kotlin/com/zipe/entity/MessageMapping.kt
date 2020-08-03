package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "message_mapping")
data class MessageMapping(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: Long = 0,

        @Column(name = "message_id")
        val messageId: String = "",

        @Column(name = "detail_id")
        var detailId: String = ""

) : Serializable, BaseEntity()
