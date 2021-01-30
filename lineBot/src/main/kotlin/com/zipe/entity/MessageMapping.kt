package com.zipe.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class MessageMapping(

        @Id
        val messageId: String = "",

        var detailId: String = ""

) : Serializable
