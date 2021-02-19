package com.zipe.service.line.impl

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.zipe.entity.LineChannel
import com.zipe.service.line.ILineEventService
import com.zipe.util.log.logger
import org.springframework.stereotype.Service

@Service
class LineMemberLeftEventImpl : ILineEventService {

    override fun process(
        channel: LineChannel,
        client: LineMessagingClient,
        event: Event
    ) {
        logger().info("Someone left")
    }
}
