package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class MessageSetting : Serializable, BaseEntity() {

    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    var id: String = ""

    var messageId: String = ""

    var key: String = ""

    var comment: String = ""

}

fun MessageSetting.asObject(messageId: String, key: String, comment: String) = MessageSetting().apply {
    this.messageId = messageId
    this.key = key
    this.comment = comment
}
