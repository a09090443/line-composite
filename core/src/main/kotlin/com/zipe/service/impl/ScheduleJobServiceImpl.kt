package com.zipe.service.impl

import com.zipe.entity.ScheduleJob
import com.zipe.payload.ScheduleJobDetail
import com.zipe.repository.IScheduleJobRepository
import com.zipe.service.IScheduleJobService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


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
        if (scheduleJobRepository != null) {
            return scheduleJobRepository.findById(id)
        }
        return ScheduleJob()
    }

    @Throws(Exception::class)
    override fun findByJobName(jobName: String?): ScheduleJob? {
        return scheduleJobRepository.findByJobName(jobName)
        return ScheduleJob()
    }

    override fun saveOrUpdate(scheduleJobDetail: ScheduleJobDetail?) {

        val scheduleJob = ScheduleJob().apply {
            this.jobClass = scheduleJobDetail!!.classPath
            this.jobName = scheduleJobDetail.jobName
            this.jobGroup = scheduleJobDetail.group
            this.jobDescription = scheduleJobDetail.description
            this.startTime = "${scheduleJobDetail.startDate}  ${scheduleJobDetail.time}"
            this.endTime = "${scheduleJobDetail.endDate} ${scheduleJobDetail.time}"
            this.status = scheduleJobDetail.status
            this.executeTimes = scheduleJobDetail.repeatTimes
            this.timeUnit = scheduleJobDetail.timeUnit
            this.repeatInterval = scheduleJobDetail.interval
        }

        scheduleJobRepository.save(scheduleJob)
    }

    @Throws(Exception::class)
    override fun delete(scheduleJobEntity: ScheduleJob?) {
        if (scheduleJobRepository != null) {
//            scheduleJobEntityRepository.delete(scheduleJobEntity)
        }
    }
}
