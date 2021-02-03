package com.zipe.service.impl

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel
import com.zipe.service.ILineEventService
import org.springframework.stereotype.Service

@Service
class LineMemberJoinedEventServiceImpl : ILineEventService {
    override fun process(
        channel: LineChannel,
        client: LineMessagingClient,
        event: Event,
        profile: UserProfileResponse?
    ) {
        TODO("Not yet implemented")
    }
}
