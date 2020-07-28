package com.zipe.service.impl

import com.zipe.entity.ScheduleJob
import com.zipe.payload.ScheduleJobDetail
import com.zipe.repository.IScheduleJobRepository
import com.zipe.service.IScheduleJobService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Transactional
@Service("scheduleJobService")
class ScheduleJobServiceImpl : IScheduleJobService {

    @Autowired
    private lateinit var scheduleJobRepository: IScheduleJobRepository

    @Throws(Exception::class)
    override fun findAllJobs(): List<ScheduleJob?>? {
        return scheduleJobRepository.findAll()
    }

    @Throws(Exception::class)
    override fun findById(id: Int): ScheduleJob? {
        return scheduleJobRepository.findById(id)
    }

    @Throws(Exception::class)
    override fun findByJobName(jobName: String?): ScheduleJob? {
        return scheduleJobRepository.findByJobName(jobName)
    }

    override fun saveOrUpdate(scheduleJobDetail: ScheduleJobDetail) {

        val scheduleJob = ScheduleJob().apply {
            this.jobClass = scheduleJobDetail.classPath
            this.jobName = scheduleJobDetail.jobName
            this.jobGroup = scheduleJobDetail.group
            this.jobDescription = scheduleJobDetail.description

            this.startTime = LocalDateTime.parse(
                "${scheduleJobDetail.startDate} ${scheduleJobDetail.time}",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            )
            this.endTime = LocalDateTime.parse(
                "${scheduleJobDetail.endDate} ${scheduleJobDetail.time}",
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            )

            this.status = scheduleJobDetail.status
            this.executeTimes = scheduleJobDetail.repeatTimes
            this.timeUnit = scheduleJobDetail.timeUnit
            this.repeatInterval = scheduleJobDetail.interval
        }

        scheduleJobRepository.save(scheduleJob)
    }

    @Throws(Exception::class)
    override fun delete(jobName: String) {
        scheduleJobRepository.deleteByJobName(jobName)
    }
}
