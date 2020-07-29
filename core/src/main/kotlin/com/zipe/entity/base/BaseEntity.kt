package com.zipe.entity.base

import java.time.LocalDateTime
import javax.persistence.Column

open class BaseEntity {

    @Column(name = "update_time", nullable = true)
    val updateTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "update_user", nullable = true)
    val updateBy: String = ""

    @Column(name = "create_time", nullable = true)
    val createTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "create_user", nullable = true)
    val createBy: String = ""
}
