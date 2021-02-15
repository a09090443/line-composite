package com.zipe.service.line.impl

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.source.GroupSource
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel
import com.zipe.enum.LineType
import com.zipe.repository.ILineInfoRepository
import com.zipe.repository.ILineMappingRepository
import com.zipe.service.line.ILineEventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LineLeaveEventServiceImpl : ILineEventService {

    @Autowired
    private lateinit var lineInfoRepository: ILineInfoRepository

    @Autowired
    private lateinit var lineMappingRepository: ILineMappingRepository

    @Transactional(rollbackFor = [Exception::class])
    override fun process(
        channel: LineChannel,
        client: LineMessagingClient,
        event: Event,
        profile: UserProfileResponse?
    ) {
        (event.source as GroupSource).groupId.let {
            lineInfoRepository.findByLineIdAndType(it, LineType.GROUP.name)?.let { lineInfo ->
                try {
                    lineMappingRepository.deleteByChannelIdAndInfoId(channel.channelId, lineInfo.lineId)
                } catch (e: Exception) {
                    throw Exception("Could not delete channelId:$channel.channelId, infoId:$it.lineId in LineMapping table.")
                }
            }
        }
    }
}
