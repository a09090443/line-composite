package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class MessageSetting(

    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    var id: String = "",

    var name: String = "",

    var comment: String = ""
) : Serializable, BaseEntity()

fun MessageSetting.asObject(name: String, comment: String) = MessageSetting().apply {
    this.name = name
    this.comment = comment
}
