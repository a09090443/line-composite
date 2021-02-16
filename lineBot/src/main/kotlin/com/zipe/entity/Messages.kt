package com.zipe.entity

import java.io.Serializable

data class Messages(

    var name: String = "",

    var value: String = "",

    var type: String = "",

    val comment: String = "",

    var messageDetails: Set<MessageDetail> = setOf()

) : Serializable
