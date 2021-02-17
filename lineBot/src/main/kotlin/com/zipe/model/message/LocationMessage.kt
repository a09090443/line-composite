package com.zipe.model.message

import com.zipe.enum.MessageType

/**
 * @author : Gary Tsai
 * @created : @Date 2021/2/17 下午 04:39
 **/
data class LocationMessage(
    var title: String,
    var address: String,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
) : BasicMessage(MessageType.LOCATION.name.toLowerCase())
