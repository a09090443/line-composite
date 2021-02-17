package com.zipe.model.message

import com.zipe.enum.MessageType

/**
 * @author : Gary Tsai
 * @created : @Date 2021/2/17 下午 04:28
 **/
data class StickerMessage(
    var packageId: String,
    var stickerId: String
) : BasicMessage(MessageType.STICKER.name.toLowerCase())
