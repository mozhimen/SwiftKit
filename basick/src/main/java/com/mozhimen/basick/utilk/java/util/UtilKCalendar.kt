package com.mozhimen.basick.utilk.java.util

import java.util.Calendar

/**
 * @ClassName UtilKCalendar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/2 15:03
 * @Version 1.0
 */
object UtilKCalendar {
    @JvmStatic
    fun get(): Calendar =
        Calendar.getInstance()

    //获取下小时结束还剩余多少秒
    @JvmStatic
    fun getRemainTime_ofNextHour(): Long {
        val calendar = get()//获取今天当前时间//获取明天凌晨0点的日期
        val calendarNextHour: Calendar = UtilKGregorianCalendar.get_ofNextHour(calendar)
        return (calendarNextHour.timeInMillis - calendar.timeInMillis) / 1000L//返回 明天凌晨0点 和 今天当前时间 的差值（秒数）
    }
}