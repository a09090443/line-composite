package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class OrderProcess(

        @Id
        var id: Long = 0,

        val name: String = "",

        val content: String = "",

        val type: String = "",

        val enabled: String = "",

        val sequence: Long = 0,

        val parentId: Long = 0,

        val channelId: String = ""

) : Serializable, BaseEntity()
