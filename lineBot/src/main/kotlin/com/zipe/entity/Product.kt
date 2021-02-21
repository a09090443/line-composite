package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import org.hibernate.annotations.GenericGenerator
import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Product(

    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    var id: String = "",

    var channelId: String = "",

    @Id
    val productId: String = "",

    val name: String = "",

    val price: BigDecimal = BigDecimal.ZERO,

    val quantity: Long = 0,

    val image: String = ""

) : Serializable, BaseEntity()
