package com.zipe.payload

import org.quartz.JobDataMap

data class ScheduleJobDetail(
    val jobName: String = "",
    val group: String = "",
    val description: String = "",
    val classPath: String = "",
    var status: Int = 0,
    val startDate: String = "",
    val endDate: String = "",
    val time: String = "",
    val interval: Int = 0,
    val repeatTimes: Int = 0,
    val timeUnit: Int = 0,
    val jobDataMap: JobDataMap? = JobDataMap(),
    var errorMessage: String = ""
)
