package com.zipe.response

import java.math.BigDecimal

data class PaymentResponse(
        val returnCode: String,
        val returnMessage: String,
        val info: Info
)

data class Info(
        val paymentUrl: PaymentUrl,
        val transactionId: Long,
        val paymentAccessToken: String,
        val authorizationExpireDate: String,
        val regKey: String,
        val payInfo: List<PayInfo>
)

data class PaymentUrl(
        val web: String,
        val app: String
)

data class PayInfo(
        val method: String,
        val amount: BigDecimal,
        val creditCardNickname: String,
        val creditCardBrand: String,
        val maskedCreditCardNumber: String
)
