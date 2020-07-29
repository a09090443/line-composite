package com.zipe.repository

import com.zipe.entity.ScheduleJobLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IScheduleJobLogRepository : JpaRepository<ScheduleJobLog, Long> {

}
