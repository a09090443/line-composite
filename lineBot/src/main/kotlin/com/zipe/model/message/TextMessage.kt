package com.zipe.model.message

import com.zipe.enum.MessageType

/**
 * @author : Gary Tsai
 * @created : @Date 2021/2/17 下午 03:44
 **/
data class TextMessage(
    var text: String = "",
    var emojis: EmojiMain
) : BasicMessage(MessageType.TEXT.name.toLowerCase())
