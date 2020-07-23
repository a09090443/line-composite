package com.zipe.service.impl

import com.zipe.model.ScheduleJob
import com.zipe.payload.ScheduleJobDetail
import com.zipe.repository.IScheduleJobEntityRepository
import com.zipe.service.IScheduleJobService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Transactional
@Service("scheduleJobService")
class ScheduleJobServiceImpl : IScheduleJobService {
    @Autowired
    private val scheduleJobEntityRepository: IScheduleJobEntityRepository? = null

    @Throws(Exception::class)
    override fun findAllJobs(): List<ScheduleJob?>? {
        if (scheduleJobEntityRepository != null) {
            return scheduleJobEntityRepository.findAll()
        }
        return listOf()
    }

    @Throws(Exception::class)
    override fun findById(id: Int): ScheduleJob? {
        if (scheduleJobEntityRepository != null) {
            return scheduleJobEntityRepository.findById(id)
        }
        return ScheduleJob()
    }

    @Throws(Exception::class)
    override fun findByJobName(jobName: String?): ScheduleJob? {
        if (scheduleJobEntityRepository != null) {
            return scheduleJobEntityRepository.findByJobName(jobName)
        }
        return ScheduleJob()
    }

    override fun saveOrUpdate(scheduleJobDetail: ScheduleJobDetail?) {
        val scheduleJob = ScheduleJob()
        scheduleJob.jobClass = scheduleJobDetail!!.classPath
        scheduleJob.jobName = scheduleJobDetail.jobName
        scheduleJob.jobGroup = scheduleJobDetail.group
        scheduleJob.jobDescription = scheduleJobDetail.description
        scheduleJob.startTime = scheduleJobDetail.startDate + " " + scheduleJobDetail.time
        scheduleJob.endTime = scheduleJobDetail.endDate + " " + scheduleJobDetail.time
        scheduleJob.status = scheduleJobDetail.status
        scheduleJob.executeTimes = scheduleJobDetail.repeatTimes
        scheduleJob.timeUnit = scheduleJobDetail.timeUnit
        scheduleJob.repeatInterval = scheduleJobDetail.interval
        if (scheduleJobEntityRepository != null) {
            scheduleJobEntityRepository.save(scheduleJob)
        }
    }

    @Throws(Exception::class)
    override fun delete(scheduleJobEntity: ScheduleJob?) {
        if (scheduleJobEntityRepository != null) {
//            scheduleJobEntityRepository.delete(scheduleJobEntity)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(ScheduleJobServiceImpl::class.java)
    }
}
