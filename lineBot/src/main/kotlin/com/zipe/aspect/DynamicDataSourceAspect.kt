package com.zipe.aspect

import com.zipe.config.DynamicDataSourceContextHolder
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
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

    @Around("aspect()")
    @Throws(Throwable::class)
    fun around(point: ProceedingJoinPoint): Any {
        val datasourceName = "primaryDataSource"
        DynamicDataSourceContextHolder.setDataSourceName(datasourceName)
        try {
            return point.proceed()
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceName()
        }
    }
}
