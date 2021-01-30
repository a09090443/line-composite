package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class LineInfo(

    @Id
    val lineId: String = "",

    val name: String = "",

    val statusMessage: String? = "",

    val type: String = "",

    val status: String = ""

) : Serializable, BaseEntity()
