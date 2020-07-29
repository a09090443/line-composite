package com.zipe.controller

import com.zipe.enum.SheduleJobStatusEmun
import com.zipe.job.AbstractJob
import com.zipe.payload.ScheduleJobDetail
import com.zipe.service.IScheduleJobService
import com.zipe.util.log.logger
import org.quartz.SchedulerException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.text.ParseException

@RestController
@RequestMapping("/job")
class ScheduleJobController : AbstractJob() {

    val logger = logger()

    @Autowired
    private lateinit var scheduleJobService: IScheduleJobService

    @PostMapping("/register")
    @Throws(Exception::class)
    fun register(@RequestBody scheduleJobDetail: ScheduleJobDetail): ResponseEntity<ScheduleJobDetail> {
        try {
            saveOrUpdateScheduleJobStatus(scheduleJobDetail, SheduleJobStatusEmun.START.status)
            result = createJobProcess(scheduleJobDetail)
            return run {
                logger.error(result!!.errorMessage)
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(result)
            }
        } catch (ex: SchedulerException) {
            logger.error("Error scheduling message", ex)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result)
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return ResponseEntity.ok(result!!)
    }

    @DeleteMapping("/delete")
    @Throws(Exception::class)
    fun delete(@RequestBody scheduleJobDetail: ScheduleJobDetail): ResponseEntity<ScheduleJobDetail> {
        try {
            scheduleJobService.delete(scheduleJobDetail.jobName)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result)
        }
        result = deleteJobProcess(scheduleJobDetail)
        return run {
            logger.error(result!!.errorMessage)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result)
        }
    }

    @PostMapping("/stop")
    @Throws(Exception::class)
    fun stop(@RequestBody scheduleJobDetail: ScheduleJobDetail): ResponseEntity<ScheduleJobDetail> {
        try {
            saveOrUpdateScheduleJobStatus(scheduleJobDetail, SheduleJobStatusEmun.SUSPEND.status)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result)
        }
        result = suspendJobProcess(scheduleJobDetail)
        return run {
            logger.error(result!!.errorMessage)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result)
        }
    }

    @PostMapping("/start")
    @Throws(Exception::class)
    fun start(@RequestBody scheduleJobDetail: ScheduleJobDetail): ResponseEntity<ScheduleJobDetail> {
        try {
            saveOrUpdateScheduleJobStatus(scheduleJobDetail, SheduleJobStatusEmun.START.status)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result)
        }
        result = resumeJobProcess(scheduleJobDetail)
        return run {
            logger.error(result!!.errorMessage)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(result)
        }
    }

    @Throws(Exception::class)
    private fun saveOrUpdateScheduleJobStatus(scheduleJobDetail: ScheduleJobDetail, status: Int): ScheduleJobDetail {
        with(scheduleJobDetail) {
            this.status = status
        }
        try {
            scheduleJobService.findByJobName(scheduleJobDetail.jobName) ?: scheduleJobService.saveOrUpdate(scheduleJobDetail)
        } catch (e: Exception) {
            logger.error(e.message)
            throw e
        }
        return scheduleJobDetail
    }
}
