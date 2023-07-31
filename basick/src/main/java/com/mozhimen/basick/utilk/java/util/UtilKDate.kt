package com.mozhimen.basick.utilk.java.util

import android.util.Log
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.bases.BaseUtilK
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * @ClassName UtilKDate
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/15 14:08
 * @Version 1.0
 */
fun Date.asDateLong(): Long =
    UtilKDate.date2dateLong(this)

fun Date.asDateStr(formatDate: String, locale: Locale = Locale.CHINA): String =
    UtilKDate.date2dateStr(this, formatDate, locale)

fun Long.asDate(): Date =
    UtilKDate.dateLong2Date(this)

fun Long.asDateStr(formatDate: String, locale: Locale = Locale.CHINA): String =
    UtilKDate.dateLong2dateStr(this, formatDate, locale)

fun String.asDate(formatDate: String, locale: Locale = Locale.CHINA): Date =
    UtilKDate.dateStr2date(this, formatDate, locale)

fun String.asDateLong(formatDate: String, locale: Locale = Locale.CHINA): Long =
    UtilKDate.dateStr2dateLong(this, formatDate, locale)

object UtilKDate : BaseUtilK() {
    /**
     * 获取现在日期
     * @return Date
     */
    @JvmStatic
    fun getNowDate(): Date =
        Date()

    /**
     * 获取当前时间
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun getNowStr(formatDate: String = CDateFormat.yyyyMMddHHmmss, locale: Locale = Locale.CHINA): String =
        date2dateStr(getNowDate(), formatDate, locale)

    /**
     * 获取现在日期
     * @return Long
     */
    @JvmStatic
    fun getNowLong(): Long =
        date2dateLong(getNowDate())

    /**
     * 获取当前小时
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun getCurrentHourStr(locale: Locale = Locale.CHINA): String =
        date2dateStr(getNowDate(), CDateFormat.yyyyMMddHH, locale)

    /**
     * 获取当前小时
     * @return Long
     */
    @JvmStatic
    fun getCurrentHourLong(locale: Locale = Locale.CHINA): Long =
        dateStr2dateLong(getCurrentHourStr(locale), CDateFormat.yyyyMMddHH, locale)

    /**
     * 今日
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    @JvmOverloads
    fun getTodayStr(locale: Locale = Locale.CHINA, formatDate: String = CDateFormat.yyyyMMdd): String =
        date2dateStr(getNowDate(), formatDate, locale)

    /**
     * 今日Long
     * @param locale Locale
     * @return Long
     */
    @JvmStatic
    fun getTodayLong(locale: Locale = Locale.CHINA): Long =
        dateStr2dateLong(getTodayStr(locale), CDateFormat.yyyyMMdd, locale)

    /**
     * 获得Format
     * @param formatDate String
     * @param locale Locale?
     * @return SimpleDateFormat
     */
    @JvmStatic
    fun getSdf(
        formatDate: String, locale: Locale = Locale.CHINA
    ): SimpleDateFormat =
        SimpleDateFormat(formatDate, locale)

    /**
     * date转long
     * @param date Date
     * @return Long
     */
    @JvmStatic
    fun date2dateLong(date: Date): Long {
        return date.time
    }

    /**
     * long转date
     * @param date Long
     * @return Date
     */
    fun dateLong2Date(date: Long): Date {
        return Date(date)
    }

    /**
     * 日期转字符串
     * @param date Date
     * @param formatDate String
     * @param locale Locale? 时区
     * @return String
     */
    @JvmStatic
    fun date2dateStr(
        date: Date, formatDate: String, locale: Locale = Locale.CHINA
    ): String {
        return getSdf(formatDate, locale).format(date)
    }

    /**
     * 字符串转日期
     * @param dateStr String
     * @param formatDate String
     * @param locale Locale?
     * @return Date
     */
    @JvmStatic
    fun dateStr2date(
        dateStr: String, formatDate: String, locale: Locale = Locale.CHINA
    ): Date {
        return getSdf(formatDate, locale).parse(dateStr) ?: kotlin.run {
            Log.e(TAG, "str2Date Exception time format fail!")
            throw Exception("time format fail!")
        }
    }

    /**
     * 长整型转字符串
     * @param dateLong Long
     * @param formatDate String
     * @param locale Locale?
     * @return String
     */
    @JvmStatic
    fun dateLong2dateStr(
        dateLong: Long, formatDate: String, locale: Locale = Locale.CHINA
    ): String {
        return getSdf(formatDate, locale).format(dateLong)
    }

    /**
     * String转long
     * @param dateStr String
     * @param formatDate String
     * @param locale Locale
     * @return Long
     */
    @JvmStatic
    fun dateStr2dateLong(
        dateStr: String, formatDate: String, locale: Locale = Locale.CHINA
    ): Long {
        return date2dateLong(dateStr2date(dateStr, formatDate, locale))
    }

    /**
     * 时间比较
     * @param date1 Date
     * @param date2 Date
     * @return Int -1: date2 larger, 1: date1 larger, 0: same
     */
    @JvmStatic
    fun dateCompare(date1: Date, date2: Date): Int =
        date1.compareTo(date2)

    /**
     * 时间比较
     * @param date1 String
     * @param date2 String
     * @param formatDate String
     * @return Int
     */
    @JvmStatic
    fun dateCompare(date1: String, date2: String, formatDate: String) =
        dateCompare(dateStr2date(date1, formatDate), dateStr2date(date2, formatDate))
}