package com.zipe.model.message

import com.zipe.enum.MessageType

/**
 * @author : Gary Tsai
 * @created : @Date 2021/2/17 下午 04:31
 **/
data class ImageMessage(
    var originalContentUrl: String,
    var previewImageUrl: String
) : BasicMessage(MessageType.IMAGE.name.toLowerCase())
