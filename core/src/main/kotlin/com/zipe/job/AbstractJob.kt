package com.zipe.job

import com.zipe.enum.ScheduleEmun
import com.zipe.enum.SheduleJobStatusEmun
import com.zipe.payload.ScheduleJobDetail
import org.apache.commons.lang.StringUtils
import org.quartz.*
import org.springframework.beans.factory.annotation.Autowired
import java.text.ParseException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

abstract class AbstractJob {

    @Autowired
    private lateinit var scheduler: Scheduler

    protected var result: ScheduleJobDetail? = null

    @Throws(ClassNotFoundException::class, ParseException::class, SchedulerException::class)
    fun executeJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        val dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val startTime = LocalDateTime.parse(
            "${scheduleJobDetail.startDate} ${scheduleJobDetail.time}",
            dtf
        )
        val endTime = LocalDateTime.parse(
            "${scheduleJobDetail.endDate} ${scheduleJobDetail.time}",
            dtf
        )
        try {
            val jobDetail = buildJobDetail(scheduleJobDetail)
            val trigger = buildJobTrigger(jobDetail, startTime, endTime, scheduleJobDetail)
            scheduler.scheduleJob(jobDetail, trigger)
        } catch (e: SchedulerException) {
            throw e
        }
        return scheduleJobDetail
    }

    @Throws(SchedulerException::class, ParseException::class, ClassNotFoundException::class)
    fun deleteJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        return scheduleJobStatusProcess(scheduleJobDetail, SheduleJobStatusEmun.DELETE)
    }

    @Throws(SchedulerException::class, ParseException::class, ClassNotFoundException::class)
    fun suspendJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        return scheduleJobStatusProcess(scheduleJobDetail, SheduleJobStatusEmun.SUSPEND)
    }

    @Throws(SchedulerException::class, ParseException::class, ClassNotFoundException::class)
    fun resumeJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        return scheduleJobStatusProcess(scheduleJobDetail, SheduleJobStatusEmun.RESUME)
    }

    @Throws(SchedulerException::class, ParseException::class, ClassNotFoundException::class)
    fun createJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        return scheduleJobStatusProcess(scheduleJobDetail, SheduleJobStatusEmun.CREATE)
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(ClassNotFoundException::class)
    fun buildJobDetail(scheduleJobDetail: ScheduleJobDetail): JobDetail {
        val clazz: Class<out Job> = Class.forName(scheduleJobDetail.classPath) as Class<out Job>

        return JobBuilder.newJob(clazz)
            .withIdentity(scheduleJobDetail.jobName, scheduleJobDetail.group)
            .withDescription(scheduleJobDetail.description)
            .usingJobData(scheduleJobDetail.jobDataMap)
            .storeDurably()
            .build()
    }

    private fun buildJobTrigger(
        jobDetail: JobDetail,
        startTime: LocalDateTime?,
        endTime: LocalDateTime?,
        scheduleJobDetail: ScheduleJobDetail
    ): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity(jobDetail.key.name, scheduleJobDetail.jobName)
            .withDescription(scheduleJobDetail.description)
            .startAt(Date.from(startTime?.atZone(ZoneId.systemDefault())?.toInstant()))
            .endAt(Date.from(endTime?.atZone(ZoneId.systemDefault())?.toInstant()))
            .withSchedule(
                ScheduleEmun.getTimeUnit(scheduleJobDetail.timeUnit)
                    ?.setCycle(scheduleJobDetail.interval, scheduleJobDetail.repeatTimes)
            )
            .build()
    }

    @Throws(SchedulerException::class, ParseException::class, ClassNotFoundException::class)
    private fun scheduleJobStatusProcess(
        scheduleJobDetail: ScheduleJobDetail,
        sheduleJobStatusEmun: SheduleJobStatusEmun
    ): ScheduleJobDetail {
        val jobKey = JobKey.jobKey(scheduleJobDetail.jobName, scheduleJobDetail.group)
        try {
            if (!scheduler.checkExists(jobKey) && sheduleJobStatusEmun.desc != SheduleJobStatusEmun.CREATE.desc) {
                throw SchedulerConfigException("The job is not exist.")
            }
        } catch (e: SchedulerException) {
            scheduleJobDetail.errorMessage = e.message ?: StringUtils.EMPTY
        }
        try {
            when (sheduleJobStatusEmun) {
                SheduleJobStatusEmun.CREATE -> {
                    scheduler.deleteJob(jobKey)
                    executeJobProcess(scheduleJobDetail)
                }
                SheduleJobStatusEmun.SUSPEND -> scheduler.pauseJob(jobKey)
                SheduleJobStatusEmun.RESUME -> scheduler.resumeJob(jobKey)
                SheduleJobStatusEmun.DELETE -> scheduler.deleteJob(jobKey)
            }
        } catch (e: SchedulerException) {
            e.printStackTrace()
            scheduleJobDetail.errorMessage = "Schedule job : ${scheduleJobDetail.jobName} error."
        }
        return scheduleJobDetail
    }
}
