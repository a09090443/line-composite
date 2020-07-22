package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "line_info")
data class LineInfo(

        @Id
        @Column(name = "info_id")
        var infoId: Long? = 0,

        @Column(name = "line_id")
        val lineId: String = "",

        @Column(name = "name")
        val name: String = "",

        @Column(name = "status_message")
        val statusMessage: String? = "",

        @Column(name = "type")
        val type: String = ""

) : Serializable, BaseEntity()
