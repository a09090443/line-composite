package com.zipe.enum

import com.zipe.service.line.ILineEventService
import com.zipe.service.line.impl.LineFollowEventServiceImpl
import com.zipe.service.line.impl.LineJoinEventServiceImpl
import com.zipe.service.line.impl.LineLeaveEventServiceImpl
import com.zipe.service.line.impl.LineMemberJoinedEventServiceImpl
import com.zipe.service.line.impl.LineMemberLeftEventImpl
import com.zipe.service.line.impl.LineMessageEventServiceImpl
import com.zipe.service.line.impl.LinePostBackEventServiceImpl
import com.zipe.service.line.impl.LineUnfollowEventServiceImpl
import com.zipe.util.SpringUtil

enum class EventType(var clazz: Class<*>) {
    MESSAGEEVENT(LineMessageEventServiceImpl::class.java),
    POSTBACKEVENT(LinePostBackEventServiceImpl::class.java),
    JOINEVENT(LineJoinEventServiceImpl::class.java),
    LEAVEEVENT(LineLeaveEventServiceImpl::class.java),
    FOLLOWEVENT(LineFollowEventServiceImpl::class.java),
    UNFOLLOWEVENT(LineUnfollowEventServiceImpl::class.java),
    MEMBERJOINEDEVENT(LineMemberJoinedEventServiceImpl::class.java),
    MEMBERLEFTEVENT(LineMemberLeftEventImpl::class.java);

    companion object{
        fun getService(name: String): ILineEventService {
            val service = enumValueOf<EventType>(name)
            return SpringUtil.getBean(service.clazz) as ILineEventService
        }
    }

}
