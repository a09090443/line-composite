package com.zipe.model

import java.math.BigDecimal
import java.util.*

data class ProductPackageForm(

        var id: String = UUID.randomUUID().toString(),

        var name: String = "",

        var amount: BigDecimal = BigDecimal.ZERO,

        var userFee: BigDecimal = BigDecimal.ZERO,

        var products: List<PaymentProduct> = listOf()

)
