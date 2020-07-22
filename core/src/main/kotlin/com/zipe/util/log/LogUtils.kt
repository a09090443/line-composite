package com.zipe.util.log

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Logger {
    if (T::class.isCompanion) {
        return LoggerFactory.getLogger(T::class.java.enclosingClass)
    }

    return LoggerFactory.getLogger(T::class.java)
}

/**
 * It is used for top level functions
 */
fun logger(): Logger {
    return LoggerFactory.getLogger(object {}.javaClass.enclosingClass)
}
