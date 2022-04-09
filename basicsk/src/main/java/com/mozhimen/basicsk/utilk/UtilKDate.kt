package com.mozhimen.basicsk.utilk

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @ClassName UtilKDate
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 14:08
 * @Version 1.0
 */
object UtilKDate {
    const val FORMAT_yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss"

    const val FORMAT_yyyyMMdd = "yyyy-MM-dd"

    const val FORMAT_HHmmss = "HH:mm:ss"

    const val FORMAT_HHmm = "HH:mm"

    const val FORMAT_yyyy = "yyyy"

    const val FORMAT_MM = "MM"

    const val FORMAT_dd = "dd"

    const val FORMAT_HH = "HH"

    const val FORMAT_mm = "mm"

    const val FORMAT_ss = "ss"

    /**
     * 日期转字符串
     */
    fun date2String(date: Date, formatDate: String, locale: Locale? = Locale.CHINA): String {
        val format: DateFormat = SimpleDateFormat(formatDate, locale)
        return format.format(date)
    }

    /**
     * 字符串转日期
     */
    fun string2Date(
        dateStr: String,
        formatDate: String,
        locale: Locale? = Locale.CHINA
    ): Date {
        val format: DateFormat = SimpleDateFormat(formatDate, locale)
        return format.parse(dateStr)!!
    }

    /**
     * 时间格式转换
     */
    fun convertDateFormatOfString(
        dateStr: String,
        oldFormatDate: String,
        newFormatDate: String, locale: Locale? = Locale.CHINA
    ): String {
        val format: DateFormat = SimpleDateFormat(oldFormatDate, locale)
        val format1: DateFormat = SimpleDateFormat(newFormatDate, locale)
        val oldDate = format.parse(dateStr)!!
        return format1.format(oldDate)
    }

    /**
     * 获得分裂的时间
     */
    fun splitHHmmss(dateStr: String, formatDate: String): String {
        val timeArray = dateStr.split(":").toTypedArray()
        require(timeArray.size == 3) {
            "time format is wrong"
        }
        return when (formatDate) {
            FORMAT_HH -> timeArray[0]
            FORMAT_mm -> timeArray[1]
            else -> timeArray[2]
        }
    }

    /**
     * 时间比较, -1: date2 larger, 1: date1 larger, 0: same
     */
    fun dateCompare(date1: Date, date2: Date): Int {
        return date1.compareTo(date2)
    }

    fun dateCompare(date1: String, date2: String, formatDate: String): Int {
        val date1: Date = string2Date(date1, formatDate)
        val date2: Date = string2Date(date2, formatDate)
        return dateCompare(date1, date2)
    }
}