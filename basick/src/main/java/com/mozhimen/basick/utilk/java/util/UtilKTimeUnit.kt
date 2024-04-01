package com.mozhimen.basick.utilk.java.util

import java.util.concurrent.TimeUnit


/**
 * @ClassName UtilKTimeUnit
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/11 15:43
 * @Version 1.0
 */
fun Long.longSecond2longMillis(): Long =
    UtilKTimeUnit.longSecond2longMillis(this)

fun Long.longMinute2longMillis(): Long =
    UtilKTimeUnit.longMinute2longMillis(this)

fun Long.longHour2longMillis(): Long =
    UtilKTimeUnit.longHour2longMillis(this)

object UtilKTimeUnit {
    @JvmStatic
    fun longSecond2longMillis(second: Long): Long =
        TimeUnit.SECONDS.toMillis(second)

    @JvmStatic
    fun longMinute2longMillis(second: Long): Long =
        TimeUnit.MINUTES.toMillis(second)

    @JvmStatic
    fun longHour2longMillis(second: Long): Long =
        TimeUnit.HOURS.toMillis(second)
}