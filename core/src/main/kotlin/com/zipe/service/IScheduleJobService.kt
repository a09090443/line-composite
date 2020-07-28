package com.zipe.service

import com.zipe.entity.ScheduleJob
import com.zipe.payload.ScheduleJobDetail


interface IScheduleJobService {
    @Throws(Exception::class)
    fun findAllJobs(): List<ScheduleJob?>?

    @Throws(Exception::class)
    fun findById(id: Int): ScheduleJob?

    @Throws(Exception::class)
    fun findByJobName(jobName: String?): ScheduleJob?

    @Throws(Exception::class)
    fun saveOrUpdate(scheduleJobDetail: ScheduleJobDetail)

    @Throws(Exception::class)
    fun delete(jobName: String)
}
