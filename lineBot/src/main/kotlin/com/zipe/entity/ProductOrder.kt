package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import com.zipe.util.common.DASH
import org.apache.commons.lang.StringUtils
import java.io.Serializable
import java.math.BigDecimal
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ProductOrder(

    @Id
    var id: String = UUID.randomUUID().toString().replace(DASH, StringUtils.EMPTY),

    var lineId: String = "",

    var channelId: String = "",

    var productName: String = "",

    var amount: BigDecimal = BigDecimal.ZERO,

    var quantity: Long = 0,

    var status: String = "",

    var transactionId: Long = 0

) : Serializable, BaseEntity()
