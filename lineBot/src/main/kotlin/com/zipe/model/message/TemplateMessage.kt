package com.zipe.model.message

import com.zipe.enum.MessageType

/**
 * @author : Gary Tsai
 * @created : @Date 2021/2/17 下午 05:11
 **/
data class TemplateMessage(
    var altText: String,
    var template: Template
) : BasicMessage(MessageType.TEMPLATE.name.toLowerCase())

data class Template(
    var type: String,
    var thumbnailImageUrl: String,
    var imageAspectRatio: String,
    var imageSize: String,
    var imageBackgroundColor: String,
    var title: String,
    var text: String,
    var defaultAction: DefaultAction,
    var actions: ActionMain
)

data class DefaultAction(
    var type: String,
    var label: String,
    var uri: String
)
