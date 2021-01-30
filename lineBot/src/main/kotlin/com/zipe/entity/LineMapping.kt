package com.zipe.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class LineMapping(

    @Id
    val channelId: String = "",

    var infoId: String = ""

) : Serializable
