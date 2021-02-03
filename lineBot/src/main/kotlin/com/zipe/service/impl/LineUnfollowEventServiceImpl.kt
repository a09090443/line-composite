package com.zipe.service.impl

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel
import com.zipe.enum.LineType
import com.zipe.repository.ILineInfoRepository
import com.zipe.repository.ILineMappingRepository
import com.zipe.service.ILineEventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LineUnfollowEventServiceImpl : ILineEventService {

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
        lineInfoRepository.findByLineIdAndType(event.source.userId, LineType.USER.name).let {
            try {
                lineMappingRepository.deleteByChannelIdAndInfoId(channel.channelId, it!!.lineId)
            } catch (e: Exception) {
                throw Exception("Could not delete channelId:$channel.channelId, infoId:$it.lineId in LineMapping table.")
            }
        }
    }
}
