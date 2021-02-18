package com.zipe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@PropertySource("classpath:resources.properties")
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
