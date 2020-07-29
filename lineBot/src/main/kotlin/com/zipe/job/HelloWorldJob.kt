package com.zipe.job

import org.quartz.JobExecutionContext

class HelloWorldJob : QuartzJobFactory() {
    override fun executeJob(jobExecutionContext: JobExecutionContext?) {
        println("HelloWorld job is running!!")
    }
}
