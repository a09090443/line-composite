package com.zipe.enum

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.linecorp.bot.model.message.*
import com.linecorp.bot.model.message.template.ButtonsTemplate
import com.linecorp.bot.model.message.template.CarouselTemplate
import com.linecorp.bot.model.message.template.ConfirmTemplate

enum class MessageType {
    TEXT {
        override fun message(json: String): Message {
            return jacksonObjectMapper().readValue(json, TextMessage::class.java)
        }
    },
    IMAGE {
        override fun message(json: String): Message {
            return jacksonObjectMapper().readValue(json, ImageMessage::class.java)
        }
    },
    IMAGEMAP {
        override fun message(json: String): Message {
            return jacksonObjectMapper().readValue(json, ImagemapMessage::class.java)
        }
    },
    LOCATION {
        override fun message(json: String): Message {
            return jacksonObjectMapper().readValue(json, LocationMessage::class.java)
        }
    },
    CAROUSEL {
        override fun message(json: String): Message {
            val carouselTemplate = jacksonObjectMapper().readValue(json, CarouselTemplate::class.java)
            return TemplateMessage("Carousel alt text", carouselTemplate)
        }
    },
    BUTTONS {
        override fun message(json: String): Message {
            val buttomsTemplate = jacksonObjectMapper().readValue(json, ButtonsTemplate::class.java)
            return TemplateMessage("Carousel alt text", buttomsTemplate)
        }
    },
    CONFIRM {
        override fun message(json: String): Message {
            val confirmTemplate = jacksonObjectMapper().readValue(json, ConfirmTemplate::class.java)
            return TemplateMessage("Carousel alt text", confirmTemplate)
        }
    };

    abstract fun message(json: String): Message

    companion object {
        fun getTypeName(value: String): MessageType? = values().find { it.name == value }
    }
}
