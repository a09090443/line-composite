package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class LineChannel(
    @Id
    val channelId: String = "",
    val channelSecret: String = "",
    val botId: String = "",
    val name: String = "",
    val description: String = "",
    val email: String = "",
    val userId: String = "",
    val accessToken: String = "",
    val lineStoreId: String = ""
) : Serializable, BaseEntity()
