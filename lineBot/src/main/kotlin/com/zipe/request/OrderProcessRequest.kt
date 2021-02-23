package com.zipe.request

import java.io.Serializable
import java.math.BigDecimal

data class OrderProcessRequest(

        val isOrderProcess: Boolean = false,

        val productId: String = "",

        val processId: Long = 0,

        val isCancel: Boolean = false,

        val isToPay: Boolean = false,

        val quantityUnit: String = "",

        val count: BigDecimal = BigDecimal.ZERO
) : Serializable
