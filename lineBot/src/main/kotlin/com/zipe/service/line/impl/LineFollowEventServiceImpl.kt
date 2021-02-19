package com.zipe.service.line.impl

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel
import com.zipe.entity.LineInfo
import com.zipe.entity.LineMapping
import com.zipe.enum.LineType
import com.zipe.repository.ILineInfoRepository
import com.zipe.repository.ILineMappingRepository
import com.zipe.service.line.ILineEventService
import com.zipe.util.SpringUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LineFollowEventServiceImpl : ILineEventService {

    @Autowired
    private lateinit var lineInfoRepository: ILineInfoRepository

    @Autowired
    private lateinit var lineMappingRepository: ILineMappingRepository

    override fun process(
        channel: LineChannel,
        client: LineMessagingClient,
        event: Event
    ) {
        event.source.userId?.let {
            client.getProfile(it)?.whenComplete { profile: UserProfileResponse, _: Throwable? ->
                val serviceProxy = SpringUtil.getBean(this::class.java)
                serviceProxy.saveProcess(channel.channelId, profile)
            }
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun saveProcess(
        channelId: String,
        profile: UserProfileResponse
    ) {
        var lineInfo = lineInfoRepository.findByLineIdAndType(profile.userId, LineType.USER.name)
        lineInfo ?: run {
            lineInfo = lineInfoRepository.save(LineInfo(lineId = profile.userId, type = LineType.USER.name))
        }
        lineMappingRepository.save(LineMapping(channelId = channelId, infoId = profile.userId))
    }
}
