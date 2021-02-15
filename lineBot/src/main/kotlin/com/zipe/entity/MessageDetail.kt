package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class MessageDetail(

    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    var id: String = "",

    var value: String = "",

    var type: String = "",

    var channelId: String = ""
) : Serializable, BaseEntity() {

}

fun MessageDetail.asObject(value: String, type: String, channelId: String) = MessageDetail().apply {
    this.value = value
    this.type = type
    this.channelId = channelId
}
