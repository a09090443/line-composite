package com.zipe.job

import com.zipe.enum.ScheduleEmun
import com.zipe.enum.SheduleJobStatusEmun
import com.zipe.payload.ScheduleJobDetail
import jdk.nashorn.internal.runtime.ECMAException
import org.apache.commons.lang.StringUtils
import org.quartz.*
import org.springframework.beans.factory.annotation.Autowired
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


abstract class AbstractJob {
    @Autowired
    private val scheduler: Scheduler? = null

    protected var result: ScheduleJobDetail? = null

    @Throws(ClassNotFoundException::class, ParseException::class, SchedulerException::class)
    fun executeJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val startTime =
            sdf.parse("${scheduleJobDetail.startDate} ${scheduleJobDetail.time}")
        val endTime =
            sdf.parse("${scheduleJobDetail.endDate} ${scheduleJobDetail.time}")
        try {
            val jobDetail = buildJobDetail(scheduleJobDetail)
            val trigger = buildJobTrigger(jobDetail, startTime, endTime, scheduleJobDetail)
            scheduler!!.scheduleJob(jobDetail, trigger)
        } catch (e: SchedulerException) {
            throw e
        }
        return scheduleJobDetail
    }

    @Throws(SchedulerException::class, ParseException::class, ClassNotFoundException::class)
    fun deleteJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        var scheduleJobDetail: ScheduleJobDetail = scheduleJobDetail
        scheduleJobDetail = scheduleJobStatusProcess(scheduleJobDetail, SheduleJobStatusEmun.DELETE)
        return scheduleJobDetail
    }

    @Throws(SchedulerException::class, ParseException::class, ClassNotFoundException::class)
    fun suspendJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        var scheduleJobDetail: ScheduleJobDetail = scheduleJobDetail
        scheduleJobDetail = scheduleJobStatusProcess(scheduleJobDetail, SheduleJobStatusEmun.SUSPEND)
        return scheduleJobDetail
    }

    @Throws(SchedulerException::class, ParseException::class, ClassNotFoundException::class)
    fun resumeJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        var scheduleJobDetail: ScheduleJobDetail = scheduleJobDetail
        scheduleJobDetail = scheduleJobStatusProcess(scheduleJobDetail, SheduleJobStatusEmun.RESUME)
        return scheduleJobDetail
    }

    @Throws(SchedulerException::class, ParseException::class, ClassNotFoundException::class)
    fun createJobProcess(scheduleJobDetail: ScheduleJobDetail): ScheduleJobDetail {
        var scheduleJobDetail: ScheduleJobDetail = scheduleJobDetail
        scheduleJobDetail = scheduleJobStatusProcess(scheduleJobDetail, SheduleJobStatusEmun.CREATE)
        return scheduleJobDetail
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(ClassNotFoundException::class)
    fun buildJobDetail(scheduleJobDetail: ScheduleJobDetail): JobDetail {
        val clazz: Class<out Job> = Class.forName(scheduleJobDetail.classPath) as Class<out Job>
//        if (null == scheduleJobDetail.jobDataMap) {
//            scheduleJobDetail.setJobDataMap(JobDataMap())
//        }
        return JobBuilder.newJob(clazz)
            .withIdentity(scheduleJobDetail.jobName, scheduleJobDetail.group)
            .withDescription(scheduleJobDetail.description)
            .usingJobData(scheduleJobDetail.jobDataMap)
            .storeDurably()
            .build()
    }

    fun buildJobTrigger(
        jobDetail: JobDetail,
        startTime: Date?,
        endTime: Date?,
        scheduleJobDetail: ScheduleJobDetail
    ): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(jobDetail)
            .withIdentity(jobDetail.key.name, scheduleJobDetail.jobName)
            .withDescription(scheduleJobDetail.description)
            .startAt(startTime)
            .endAt(endTime)
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
            if (!scheduler!!.checkExists(jobKey) && !sheduleJobStatusEmun.desc
                    .equals(SheduleJobStatusEmun.CREATE.desc)
            ) {
                throw SchedulerConfigException("The job is not exist.")
            }
        } catch (e: SchedulerException) {
            scheduleJobDetail.errorMessage = e.message ?: StringUtils.EMPTY
        }
        try {
            when (sheduleJobStatusEmun) {
                ECMAException.CREATE -> {
                    scheduler!!.deleteJob(jobKey)
                    executeJobProcess(scheduleJobDetail)
                }
                SheduleJobStatusEmun.SUSPEND -> scheduler!!.pauseJob(jobKey)
                SheduleJobStatusEmun.RESUME -> scheduler!!.resumeJob(jobKey)
                SheduleJobStatusEmun.DELETE -> scheduler!!.deleteJob(jobKey)
            }
        } catch (e: SchedulerException) {
            e.printStackTrace()
            scheduleJobDetail.errorMessage = "Schedule job : ${scheduleJobDetail.jobName} error."
        }
        return scheduleJobDetail
    }
}
