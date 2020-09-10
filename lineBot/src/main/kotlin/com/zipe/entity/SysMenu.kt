package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "sys_menu")
data class SysMenu(

        @Id
        @Column(name = "menu_id")
        var menuId: Long = 0,

        @Column(name = "menu_name")
        val menuName: String = "",

        @Column(name = "path")
        val path: String = "",

        @Column(name = "comment")
        val comment: String? = "",

        @Column(name = "enabled")
        val enabled: Boolean = false,

        @Column(name = "seq")
        val seq: String = "",

        @Column(name = "parent_id")
        val parentId: Long = 0

) : Serializable, BaseEntity()
