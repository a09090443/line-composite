package com.zipe.aspect


import com.zipe.annotation.DBRouting
import com.zipe.config.DynamicDataSourceContextHolder.clearDataSourceName
import com.zipe.config.DynamicDataSourceContextHolder.setDataSourceName
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
@Aspect
@Order(-1)
class DBRoutingAspect {

    @Pointcut("@annotation(com.zipe.annotation.DBRouting)")
    fun pointCut() {
    }

    @Around("pointCut()")
    fun before(point: ProceedingJoinPoint): Any {
        val method = getMethod(point)
        val dbRouting = method.getAnnotation(DBRouting::class.java)
        val datasourceName = dbRouting.datasourceName
        setDataSourceName(datasourceName)

        try {
            return point.proceed()
        } finally {
            clearDataSourceName()
        }
    }

    private fun getMethod(joinPoint: JoinPoint): Method {
        val signature = joinPoint.signature
        val methodSignature = signature as MethodSignature
        return methodSignature.method
    }
}
