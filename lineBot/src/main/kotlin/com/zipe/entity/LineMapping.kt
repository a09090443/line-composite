package com.zipe.entity

import org.springframework.data.domain.Persistable
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass

@Entity
@IdClass(LineMappingId::class)
data class LineMapping(

    @Id
    val channelId: String = "",

    @Id
    var infoId: String = ""

) : Persistable<String> {
    override fun getId(): String = channelId

    override fun isNew(): Boolean = true
}

data class LineMappingId(

    val channelId: String = "",

    var infoId: String = ""

) : Serializable
