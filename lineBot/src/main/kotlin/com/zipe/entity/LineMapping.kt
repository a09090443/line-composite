package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "line_mapping")
data class LineMapping(

        @Id
        @Column(name = "line_id")
        val lineId: Long = 0,

        @Column(name = "info_id")
        var infoId: Long = 0

) : Serializable, BaseEntity()
