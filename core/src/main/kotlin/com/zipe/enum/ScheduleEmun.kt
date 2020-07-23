package com.zipe.enum

import org.quartz.*

enum class ScheduleEmun(var timeUnit: Int) {
    NOW(0) {
        override fun setCycle(interval: Int, repeatCount: Int): SimpleScheduleBuilder {
            return SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow()
        }
    },
    SECOND(1) {
        override fun setCycle(interval: Int, repeatCount: Int): SimpleScheduleBuilder {
            return SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).withRepeatCount(repeatCount)
        }
    },
    MINUTE(2) {
        override fun setCycle(interval: Int, repeatCount: Int): SimpleScheduleBuilder {
            return SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(interval).withRepeatCount(repeatCount)
        }
    },
    HOUR(3) {
        override fun setCycle(interval: Int, repeatCount: Int): SimpleScheduleBuilder {
            return SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(interval).withRepeatCount(repeatCount)
        }
    },
    DAY(4) {
        override fun setCycle(interval: Int, repeatCount: Int): DailyTimeIntervalScheduleBuilder {
            return DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .withInterval(interval, DateBuilder.IntervalUnit.DAY).withRepeatCount(repeatCount)
        }
    },
    WEEK(5) {
        override fun setCycle(interval: Int, repeatCount: Int): CalendarIntervalScheduleBuilder {
            return CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInWeeks(interval)
        }
    },
    MONTH(6) {
        override fun setCycle(interval: Int, repeatCount: Int): CalendarIntervalScheduleBuilder {
            return CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInMonths(interval)
        }
    },
    YEAR(7) {
        override fun setCycle(interval: Int, repeatCount: Int): CalendarIntervalScheduleBuilder {
            return CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInYears(interval)
        }
    };

    abstract fun setCycle(interval: Int, repeatCount: Int): ScheduleBuilder<*>?

    companion object {
        fun getTimeUnit(timeUnit: Int): ScheduleEmun? {
            for (scheduleEmun in values()) {
                if (scheduleEmun.timeUnit == timeUnit) {
                    return scheduleEmun
                }
            }
            return null
        }
    }

}
