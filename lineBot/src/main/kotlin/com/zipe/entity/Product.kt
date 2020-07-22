package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product")
data class Product(

        @Column(name = "id")
        var id: Long = 0,

        @Column(name = "line_id")
        var lineId: Long = 0,

        @Id
        @Column(name = "product_id")
        val productId: String = "",

        @Column(name = "name")
        val name: String = "",

        @Column(name = "price")
        val price: BigDecimal = BigDecimal.ZERO,

        @Column(name = "quantity")
        val quantity: Long = 0,

        @Column(name = "image")
        val image: String = ""

) : Serializable, BaseEntity()
