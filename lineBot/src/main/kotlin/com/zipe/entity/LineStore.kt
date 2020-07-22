package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "line_store")
data class LineStore(

        @Id
        @Column(name = "line_id")
        var lineId: Long = 0,

        @Column(name = "channel_id")
        val channelId: String = "",

        @Column(name = "channel_secret")
        val channelSecret: String = "",

        @Column(name = "name")
        val name: String = "",

        @Column(name = "description")
        val description: String = "",

        @Column(name = "email")
        val email: String = ""

) : Serializable, BaseEntity()
