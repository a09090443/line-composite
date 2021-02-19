package com.zipe.service.line

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel

interface ILineEventService {
    fun process(channel: LineChannel, client: LineMessagingClient, event: Event)
}
