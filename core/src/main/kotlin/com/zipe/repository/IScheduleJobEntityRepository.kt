package com.zipe.repository

import com.zipe.model.ScheduleJob
import org.springframework.data.jpa.repository.JpaRepository


interface IScheduleJobEntityRepository : JpaRepository<ScheduleJob?, Long?> {
    fun findById(id: Int): ScheduleJob?
    fun findByJobName(jobName: String?): ScheduleJob?
}
