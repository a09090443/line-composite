package com.zipe.model

import java.math.BigDecimal

data class PaymentProduct(
        var id: String = "",

        var name: String = "",

        var imageUrl: String = "",

        var quantity: Long = 0,

        var price: BigDecimal = BigDecimal.ZERO,

        var originalPrice: BigDecimal = BigDecimal.ZERO
)
