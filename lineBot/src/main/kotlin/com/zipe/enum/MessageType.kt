package com.zipe.enum

import com.linecorp.bot.model.message.AudioMessage
import com.linecorp.bot.model.message.ImageMessage
import com.linecorp.bot.model.message.ImagemapMessage
import com.linecorp.bot.model.message.LocationMessage
import com.linecorp.bot.model.message.Message
import com.linecorp.bot.model.message.StickerMessage
import com.linecorp.bot.model.message.TemplateMessage
import com.linecorp.bot.model.message.TextMessage
import com.linecorp.bot.model.message.VideoMessage
import com.linecorp.bot.model.message.template.ButtonsTemplate
import com.linecorp.bot.model.message.template.CarouselTemplate
import com.linecorp.bot.model.message.template.ConfirmTemplate
import com.zipe.util.JsonUtil

enum class MessageType {
    TEXT {
        override fun message(json: String): Message {
            return JsonUtil.lineJsonMapper().readValue(json, TextMessage::class.java)
        }
    },
    IMAGE {
        override fun message(json: String): Message {
            return JsonUtil.lineJsonMapper().readValue(json, ImageMessage::class.java)
        }
    },
    IMAGEMAP {
        override fun message(json: String): Message {
            return JsonUtil.lineJsonMapper().readValue(json, ImagemapMessage::class.java)
        }
    },
    LOCATION {
        override fun message(json: String): Message {
            return JsonUtil.lineJsonMapper().readValue(json, LocationMessage::class.java)
        }
    },
    CAROUSEL {
        override fun message(json: String): Message {
            val carouselTemplate = JsonUtil.lineJsonMapper().readValue(json, CarouselTemplate::class.java)
            return TemplateMessage("Carousel alt text", carouselTemplate)
        }
    },
    BUTTONS {
        override fun message(json: String): Message {
            val buttonsTemplate = JsonUtil.lineJsonMapper().readValue(json, ButtonsTemplate::class.java)
            return TemplateMessage("Carousel alt text", buttonsTemplate)
        }
    },
    CONFIRM {
        override fun message(json: String): Message {
            val confirmTemplate = JsonUtil.lineJsonMapper().readValue(json, ConfirmTemplate::class.java)
            return TemplateMessage("Carousel alt text", confirmTemplate)
        }
    },
    STICKER {
        override fun message(json: String): Message {
            return JsonUtil.lineJsonMapper().readValue(json, StickerMessage::class.java)
        }
    },
    VIDEO {
        override fun message(json: String): Message {
            return JsonUtil.lineJsonMapper().readValue(json, VideoMessage::class.java)
        }
    },
    AUDIO {
        override fun message(json: String): Message {
            return JsonUtil.lineJsonMapper().readValue(json, AudioMessage::class.java)
        }
    },
    TEMPLATE {
        override fun message(json: String): Message {
            return JsonUtil.lineJsonMapper().readValue(json, TemplateMessage::class.java)
        }
    };

    abstract fun message(json: String): Message

    companion object {
        fun getTypeName(value: String): MessageType? = values().find { it.name == value }
    }
}
