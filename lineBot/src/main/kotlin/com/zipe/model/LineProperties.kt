package com.zipe.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author : Gary Tsai
 * @created : @Date 2021/2/18 下午 01:48
 **/
@Component
@ConfigurationProperties(prefix = "line")
data class LineProperties(
    var pushMessageUrl: String = "",
    var replayMessageUrl: String = "",
    var paymentRequestUrl: String = "",
    var paymentConfirmUri: String = "",
    var paymentConfirmUrl: String = "",
    var paymentUri: String = ""
)
