package com.zipe.util

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext


open class SpringContextUtil {
    // Spring应用上下文环境
    private var applicationContext: ApplicationContext? = null

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     */
    @Throws(BeansException::class)
    fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    open fun getApplicationContext(): ApplicationContext? {
        return applicationContext
    }

    /**
     * 根据beanId返回Spring中的实例
     * @Date 2019-08-07 17:36
     * @param
     * @return
     */
    @Throws(BeansException::class)
    open fun getBean(beanId: String): Any? {
        return applicationContext?.getBean(beanId)
    }

}
