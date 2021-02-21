package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
data class SysMenu(

    @Id
    var menuId: Long = 0,

    val menuName: String = "",

    val path: String = "",

    val comment: String? = "",

    val enabled: Boolean = false,

    val sequence: String = "",

    val parentId: Long = 0

) : Serializable, BaseEntity()
