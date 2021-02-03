package com.zipe.aspect

import com.zipe.config.DynamicDataSourceContextHolder
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Order(-1)
@Component
class DynamicDataSourceAspect {
    @Pointcut("execution(* com.zipe.service..*.*(..))")
    private fun aspect() {
    }

    @Before("aspect()")
    fun before(point: JoinPoint) {
        val datasourceName = "primaryDataSource"
        DynamicDataSourceContextHolder.setDataSourceName(datasourceName)
    }

    @After("aspect()")
    fun after(point: JoinPoint){
        DynamicDataSourceContextHolder.clearDataSourceName()
    }
}
