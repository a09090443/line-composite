package com.zipe.service.impl

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.source.GroupSource
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel
import com.zipe.entity.LineInfo
import com.zipe.entity.LineMapping
import com.zipe.enum.LineType
import com.zipe.repository.ILineInfoRepository
import com.zipe.repository.ILineMappingRepository
import com.zipe.service.ILineEventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LineJoinEventServiceImpl : ILineEventService {
    @Autowired
    private lateinit var lineInfoRepository: ILineInfoRepository

    @Autowired
    private lateinit var lineMappingRepository: ILineMappingRepository

    override fun process(
        channel: LineChannel,
        client: LineMessagingClient,
        event: Event,
        profile: UserProfileResponse?
    ) {
        (event.source as GroupSource).groupId.let {
            var lineInfo = lineInfoRepository.findByLineIdAndType(it, LineType.GROUP.name)

            if (null == lineInfo) {
                lineInfoRepository.save(LineInfo(lineId = it, type = LineType.GROUP.name))
                lineInfo = lineInfoRepository.findByLineIdAndType(it, LineType.GROUP.name)
            }

            lineMappingRepository.save(LineMapping(channelId = channel.channelId, infoId = lineInfo?.lineId ?: ""))
        }
    }
}
