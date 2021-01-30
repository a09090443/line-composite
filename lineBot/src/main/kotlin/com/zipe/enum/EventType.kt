package com.zipe.enum

import com.zipe.service.ILineEventService
import com.zipe.service.impl.LineFollowEventServiceImpl
import com.zipe.service.impl.LineJoinEventServiceImpl
import com.zipe.service.impl.LineLeaveEventServiceImpl
import com.zipe.service.impl.LineMessageEventServiceImpl
import com.zipe.service.impl.LinePostBackEventServiceImpl
import com.zipe.service.impl.LineUnfollowEventServiceImpl
import com.zipe.util.SpringUtil

enum class EventType(var clazz: Class<*>) {
    MESSAGEEVENT(LineMessageEventServiceImpl::class.java),
    POSTBACKEVENT(LinePostBackEventServiceImpl::class.java),
    JOINEVENT(LineJoinEventServiceImpl::class.java),
    LEAVEEVENT(LineLeaveEventServiceImpl::class.java),
    FOLLOWEVENT(LineFollowEventServiceImpl::class.java),
    UNFOLLOWEVENT(LineUnfollowEventServiceImpl::class.java);

    companion object{
        fun getService(name: String): ILineEventService {
            val service = enumValueOf<EventType>(name)
            return SpringUtil.getBean(service.clazz) as ILineEventService
        }
    }

}
