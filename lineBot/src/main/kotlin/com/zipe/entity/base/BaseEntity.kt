package com.zipe.entity.base

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.persistence.Column

open class BaseEntity {

    @Column(name = "update_time", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    val updateTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "update_user", nullable = true, length = 256)
    val updateBy: String = ""

    @Column(name = "create_time", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    val createTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "create_user", nullable = true, length = 256)
    val createBy: String = ""
}
