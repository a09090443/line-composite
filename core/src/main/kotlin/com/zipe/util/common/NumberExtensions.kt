package com.zipe.util.common

import kotlin.math.abs
import kotlin.math.log10
import kotlin.random.Random

fun Int.random(): Int = Random.nextInt(this)

fun Long.random(): Long = Random.nextLong(this)

val Int.length
    get() =
        when (this) {
            0 -> 1
            else -> log10(abs(toDouble())).toInt() + 1
        }

val Long.length
    get() =
        when (this) {
            0L -> 1
            else -> log10(abs(toDouble())).toInt() + 1
        }

inline infix operator fun Short.times(action: (Short) -> Unit) {
    var i: Short = 0
    while (i < this) action(i++)
}

inline infix operator fun Int.times(action: (Int) -> Unit) {
    var i = 0
    while (i < this) action(i++)
}

inline infix operator fun Long.times(action: (Long) -> Unit) {
    var i = 0L
    while (i < this) action(i++)
}
