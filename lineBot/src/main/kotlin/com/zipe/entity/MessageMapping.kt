package com.zipe.entity

import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(MessageMappingId::class)
data class MessageMapping(

        @Id
        val messageId: String = "",

        @Id
        val detailId: String = ""

) : Persistable<String> {
        override fun getId(): String = messageId

        override fun isNew(): Boolean = true
}

data class MessageMappingId(

        val messageId: String = "",

        val detailId: String = ""
) : Serializable
