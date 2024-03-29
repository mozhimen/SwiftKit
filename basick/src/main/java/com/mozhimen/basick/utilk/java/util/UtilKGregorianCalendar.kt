package com.mozhimen.basick.utilk.java.util

import java.util.Calendar
import java.util.GregorianCalendar

/**
 * @ClassName UtilKGregorianCalendar
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/29
 * @Version 1.0
 */
object UtilKGregorianCalendar {
    @JvmStatic
    fun get(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int, second: Int): GregorianCalendar =
        GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second)

    @JvmStatic
    fun get_ofNextHour(calendar: Calendar): GregorianCalendar =
        get(calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DATE], calendar[Calendar.HOUR_OF_DAY] + 1, 0, 0)

}