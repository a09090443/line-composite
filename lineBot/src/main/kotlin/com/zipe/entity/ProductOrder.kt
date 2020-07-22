package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product_order")
data class ProductOrder(

        @Id
        @Column(name = "order_id")
        var orderId: String = UUID.randomUUID().toString(),

        @Column(name = "line_id")
        var lineId: String = "",

        @Column(name = "channel_id")
        var channelId: String = "",

        @Column(name = "product_name")
        var productName: String = "",

        @Column(name = "amount")
        var amount: BigDecimal = BigDecimal.ZERO,

        @Column(name = "quantity")
        var quantity: Long = 0,

        @Column(name = "status")
        var status: String = "",

        @Column(name = "transaction_id")
        var transactionId: Long = 0

) : Serializable, BaseEntity()
