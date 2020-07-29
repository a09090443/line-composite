package com.zipe.job

import com.zipe.entity.ScheduleJobLog
import com.zipe.enum.SheduleJobStatusEmun
import com.zipe.repository.IScheduleJobLogRepository
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.quartz.QuartzJobBean
import java.time.LocalDateTime


abstract class QuartzJobFactory : QuartzJobBean() {
    @Autowired
    private lateinit var scheduleJobLogRepository: IScheduleJobLogRepository

    override fun executeInternal(jobExecutionContext: JobExecutionContext) {

        val scheduleJobLog = ScheduleJobLog().apply {
            this.jobName = jobExecutionContext.jobDetail.key.name
            this.jobDescription = jobExecutionContext.jobDetail.description
            this.startTime = LocalDateTime.now()
        }

        try {
            scheduleJobLog.status = SheduleJobStatusEmun.RUN.status
            scheduleJobLogRepository.save(scheduleJobLog)
            executeJob(jobExecutionContext)
            scheduleJobLog.status = SheduleJobStatusEmun.COMPLETE.status
        } catch (e: Exception) {
            scheduleJobLog.message = e.message ?: ""
            scheduleJobLog.status = SheduleJobStatusEmun.ERROR.status
        } finally {
            scheduleJobLog.endTime = LocalDateTime.now()
            scheduleJobLogRepository.saveAndFlush(scheduleJobLog)
        }
    }

    abstract fun executeJob(jobExecutionContext: JobExecutionContext?)
}
