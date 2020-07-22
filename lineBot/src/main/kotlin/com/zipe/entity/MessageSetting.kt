package com.zipe.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.linecorp.bot.model.message.Message
import com.zipe.entity.base.BaseEntity
import com.zipe.enum.MessageType
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "message_setting")
class MessageSetting : Serializable, BaseEntity() {

    @Column(name = "id")
    var id: Long = 0

    @Id
    @Column(name = "message_id")
    var messageId: String = ""

    @Column(name = "key")
    var key: String = ""

    @Column(name = "comment", nullable = true)
    val comment: String = ""

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
            name = "message_mapping",
            joinColumns = [JoinColumn(name = "message_id")],
            inverseJoinColumns = [JoinColumn(name = "detail_id")]
    )
    var messageDetails: Set<MessageDetail> = setOf()

    fun MessageSetting.convertMessageType(json: String): List<Message> {
        return this.messageDetails.map { MessageType.valueOf(it.type.toUpperCase()).message(json.trimIndent()) }.toList()
    }
}
