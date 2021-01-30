package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class LineStore(

        @Id
        val channelId: String = "",

        val channelSecret: String = "",

        val name: String = "",

        val description: String = "",

        val email: String = ""

) : Serializable, BaseEntity()
