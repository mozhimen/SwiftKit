package com.mozhimen.basick.utilk.java.util

import java.util.concurrent.TimeUnit


/**
 * @ClassName UtilKTimeUnit
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/11 15:43
 * @Version 1.0
 */
fun Long.second2millis(): Long =
    UtilKTimeUnit.second2millis(this)

fun Long.second2millis2(): Long =
    UtilKTimeUnit.second2millis2(this)

fun Long.minute2millis(): Long =
    UtilKTimeUnit.minute2millis(this)

fun Long.hour2millis(): Long =
    UtilKTimeUnit.hour2millis(this)



object UtilKTimeUnit {
    @JvmStatic
    fun second2millis(second: Long): Long =
        TimeUnit.SECONDS.toMillis(second)

    @JvmStatic
    fun second2millis2(second: Long): Long =
        second * 1000L

    @JvmStatic
    fun minute2millis(second: Long): Long =
        TimeUnit.MINUTES.toMillis(second)

    @JvmStatic
    fun hour2millis(second: Long): Long =
        TimeUnit.HOURS.toMillis(second)
}