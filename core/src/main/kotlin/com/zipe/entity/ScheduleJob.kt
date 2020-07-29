package com.zipe.entity

import com.zipe.entity.base.BaseEntity
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "schedule_job")
data class ScheduleJob(

    @Id
    @Column(name = "id")
    val id: Int = 0,

    @Column(name = "job_name", unique = true)
    var jobName: String = "",

    @Column(name = "job_group")
    var jobGroup: String = "",

    @Column(name = "job_description")
    var jobDescription: String = "",

    @Column(name = "job_class")
    var jobClass: String = "",

    @Column(name = "status")
    var status: Int = 0,

    @Column(name = "time_unit")
    var timeUnit: Int = 0,

    @Column(name = "repeat_interval")
    var repeatInterval: Int = 0,

    @Column(name = "execute_times")
    var executeTimes: Int = 0,

    @Column(name = "start_time")
    var startTime: LocalDateTime? = null,

    @Column(name = "end_time")
    var endTime: LocalDateTime? = null

) : Serializable, BaseEntity()
