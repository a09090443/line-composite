package com.zipe.model

import java.io.Serializable

data class OrderProcess(

        var id: Long = 0,

        var name: String = "",

        var content: String = "",

        var type: String = "",

        var enabled: String = "",

        var sequence: Long = 0,

        var parentId: Long = 0,

        var channelId: String = ""

) : Serializable
