package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "order_process")
data class OrderProcess(

        @Id
        @Column(name = "process_id")
        var orderId: Long = 0,

        @Column(name = "process_name")
        val processName: String = "",

        @Column(name = "content")
        val content: String = "",

        @Column(name = "type")
        val type: String = "",

        @Column(name = "enabled")
        val enabled: String = "",

        @Column(name = "sequence")
        val sequence: Long = 0,

        @Column(name = "parent_id")
        val parentId: Long = 0,

        @Column(name = "line_id")
        val lineId: Long? = 0

) : Serializable, BaseEntity()
