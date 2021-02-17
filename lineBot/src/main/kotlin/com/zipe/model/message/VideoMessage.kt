package com.zipe.model.message

import com.zipe.enum.MessageType

/**
 * @author : Gary Tsai
 * @created : @Date 2021/2/17 下午 04:33
 **/
data class VideoMessage(
    var originalContentUrl: String,
    var previewImageUrl: String,
    var trackingId: String
) : BasicMessage(MessageType.VIDEO.name.toLowerCase())
