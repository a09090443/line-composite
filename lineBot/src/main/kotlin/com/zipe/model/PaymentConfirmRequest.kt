package com.zipe.model

import java.math.BigDecimal

data class PaymentConfirmRequest(
        val amount: BigDecimal = BigDecimal.ZERO,
        val currency: String = ""
)
