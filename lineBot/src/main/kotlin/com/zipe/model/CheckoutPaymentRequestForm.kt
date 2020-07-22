package com.zipe.model

import java.math.BigDecimal

data class CheckoutPaymentRequestForm(
        var amount: BigDecimal,
        var currency: String,
        var orderId: String,
        var packages: List<ProductPackageForm> = listOf(),
        var redirectUrls: RedirectUrls? = null
)
