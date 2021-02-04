package com.zipe.service.impl

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel
import com.zipe.service.ILineEventService
import com.zipe.util.log.logger
import org.springframework.stereotype.Service

@Service
class LineMemberLeftEventImpl : ILineEventService {

    override fun process(
        channel: LineChannel,
        client: LineMessagingClient,
        event: Event,
        profile: UserProfileResponse?
    ) {
        logger().info("Someone left")
    }
}
