package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "line_channel")
class LineChannel : Serializable, BaseEntity() {

    @Id
    @Column(name = "line_id")
    var lineId: Long = 0

    @Column(name = "channel_id")
    var channelId: String = ""

    @Column(name = "channel_secret")
    var channelSecret: String = ""

    @Column(name = "bot_id")
    var botId: String = ""

    @Column(name = "name")
    var name: String = ""

    @Column(name = "description")
    var description: String = ""

    @Column(name = "email")
    var email: String = ""

    @Column(name = "user_id")
    var userId: String = ""

    @Column(name = "access_token")
    var accessToken: String = ""

}
