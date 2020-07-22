package com.zipe.model

import java.util.*

data class PaymentRequest(
        val nonce: String = UUID.randomUUID().toString(),
        val channelSecret: String,
        val requestUri: String,
        val requestBody: String,
        val sendUrl: String,
        val channelId: String
) {
    fun data() = this.channelSecret + this.requestUri + this.requestBody + this.nonce
}
