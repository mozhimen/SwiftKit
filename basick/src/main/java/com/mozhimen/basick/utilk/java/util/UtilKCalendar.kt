package com.mozhimen.basick.utilk.java.util

import java.util.Calendar
import java.util.GregorianCalendar

/**
 * @ClassName UtilKCalendar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:03
 * @Version 1.0
 */
object UtilKCalendar {
    /**
     * 获取下小时结束还剩余多少秒
     * @return
     */
    @JvmStatic
    fun getRemainTimeForNextHour(): Long {
        //获取今天当前时间
        val calendar = Calendar.getInstance()
        //获取明天凌晨0点的日期
        val nextHourCalendar: Calendar = GregorianCalendar(
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DATE],
                calendar[Calendar.HOUR_OF_DAY] + 1, 0, 0
        )
        //返回 明天凌晨0点 和 今天当前时间 的差值（秒数）
        return (nextHourCalendar.timeInMillis - calendar.timeInMillis) / 1000L
    }
}