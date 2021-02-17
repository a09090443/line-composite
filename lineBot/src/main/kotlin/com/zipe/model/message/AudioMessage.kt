package com.zipe.model.message

import com.zipe.enum.MessageType

/**
 * @author : Gary Tsai
 * @created : @Date 2021/2/17 下午 04:37
 **/
data class AudioMessage(
    var originalContentUrl: String,
    var duration: Long = 0
) : BasicMessage(MessageType.AUDIO.name.toLowerCase())
