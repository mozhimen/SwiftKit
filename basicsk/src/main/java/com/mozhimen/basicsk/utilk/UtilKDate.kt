package com.mozhimen.basicsk.utilk

import android.annotation.SuppressLint
import android.util.TimeFormatException
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
     * 获得Format
     * @param formatDate String
     * @param locale Locale?
     * @return SimpleDateFormat
     */
    fun getSdf(
        formatDate: String, locale: Locale? = Locale.CHINA
    ): SimpleDateFormat =
        SimpleDateFormat(formatDate, locale)

    /**
     * 日期转字符串
     * @param date Date
     * @param formatDate String
     * @param locale Locale? 时区
     * @return String
     */
    fun date2String(
        date: Date, formatDate: String, locale: Locale? = Locale.CHINA
    ): String =
        SimpleDateFormat(formatDate, locale).format(date)

    /**
     * 长整型转字符串
     * @param date Long
     * @param formatDate String
     * @param locale Locale?
     * @return String
     */
    fun long2String(
        date: Long, formatDate: String, locale: Locale? = Locale.CHINA
    ): String =
        SimpleDateFormat(formatDate, locale).format(date)

    /**
     * 字符串转日期
     * @param dateStr String
     * @param formatDate String
     * @param locale Locale?
     * @return Date
     */
    fun string2Date(
        dateStr: String, formatDate: String, locale: Locale? = Locale.CHINA
    ): Date {
        return SimpleDateFormat(formatDate, locale).parse(dateStr) ?: kotlin.run {
            throw Exception()
        }
    }

    /**
     * 时间比较
     * @param date1 Date
     * @param date2 Date
     * @return Int -1: date2 larger, 1: date1 larger, 0: same
     */
    fun dateCompare(date1: Date, date2: Date): Int =
        date1.compareTo(date2)

    fun dateCompare(date1: String, date2: String, formatDate: String) =
        dateCompare(string2Date(date1, formatDate), string2Date(date2, formatDate))
}