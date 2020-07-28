package com.zipe.repository

import com.zipe.entity.ScheduleJob
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IScheduleJobRepository : JpaRepository<ScheduleJob, Long> {

    fun findById(id: Int): ScheduleJob?

    fun findByJobName(jobName: String?): ScheduleJob?

    fun deleteByJobName(jobName: String)

}
