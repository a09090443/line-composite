package com.zipe.model.message

import com.zipe.enum.MessageType

/**
 * @author : Gary Tsai
 * @created : @Date 2021/2/17 下午 04:47
 **/
data class ImageMapMessage(
    var baseUrl: String,
    var altText: String,
    var baseSize: BaseSize,
    var video: Video,
    var actions: ActionMain
) : BasicMessage(MessageType.IMAGEMAP.name.toLowerCase())

data class BaseSize(
    var width: Int = 0,
    var height: Int = 0
)

data class Video(
    var originalContentUrl: String,
    var previewImageUrl: String,
    var area: Area,
    var externalLink: ExternalLink
)

data class Area(
    var x: Int = 0,
    var y: Int = 0,
    var width: Int = 0,
    var height: Int = 0
)

data class ExternalLink(
    var linkUri: String,
    var label: String
)

data class ActionMain(
    var details: List<ActionDetail> = listOf()
)

data class ActionDetail(
    var type: String,
    var label: String,
    var linkUri: String,
    var text: String,
    var area: Area,
    var data: String
)

