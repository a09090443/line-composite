package com.zipe.service.impl

import com.linecorp.bot.client.LineMessagingClient
import com.linecorp.bot.model.event.Event
import com.linecorp.bot.model.event.source.GroupSource
import com.linecorp.bot.model.group.GroupSummaryResponse
import com.linecorp.bot.model.profile.UserProfileResponse
import com.zipe.entity.LineChannel
import com.zipe.entity.LineInfo
import com.zipe.entity.LineMapping
import com.zipe.enum.LineType
import com.zipe.repository.ILineInfoRepository
import com.zipe.repository.ILineMappingRepository
import com.zipe.service.ILineEventService
import com.zipe.util.SpringUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
        val source = event.source as GroupSource
        source.groupId.let {
            client.getGroupSummary(it)?.whenComplete { summary: GroupSummaryResponse, _: Throwable? ->
                val serviceProxy = SpringUtil.getBean(this::class.java)
                serviceProxy.saveProcess(channel.channelId, summary)
            }
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun saveProcess(
        channelId: String,
        summary: GroupSummaryResponse
    ) {
        var lineInfo = lineInfoRepository.findByLineIdAndType(summary.groupId, LineType.GROUP.name)
        lineInfo ?: run {
            lineInfo = lineInfoRepository.save(LineInfo(lineId = summary.groupId, name = summary.groupName, type = LineType.GROUP.name))
        }
        lineMappingRepository.save(LineMapping(channelId = channelId, infoId = summary.groupId))
    }
}
