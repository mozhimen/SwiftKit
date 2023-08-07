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
fun Date.date2longDate(): Long =
    UtilKDate.date2longDate(this)

fun Long.longDate2date(): Date =
    UtilKDate.longDate2date(this)

fun Date.date2strDate(formatDate: String, locale: Locale = Locale.CHINA): String =
    UtilKDate.date2strDate(this, formatDate, locale)

fun Long.longDate2strDate(formatDate: String, locale: Locale = Locale.CHINA): String =
    UtilKDate.longDate2strDate(this, formatDate, locale)

fun String.strDate2date(formatDate: String, locale: Locale = Locale.CHINA): Date =
    UtilKDate.strDate2date(this, formatDate, locale)

fun String.strDate2longDate(formatDate: String, locale: Locale = Locale.CHINA): Long =
    UtilKDate.strDate2longDate(this, formatDate, locale)

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
        date2strDate(getNowDate(), formatDate, locale)

    /**
     * 获取现在日期
     * @return Long
     */
    @JvmStatic
    fun getNowLong(): Long =
        date2longDate(getNowDate())

    /**
     * 获取当前小时
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    fun getCurrentHourStr(locale: Locale = Locale.CHINA): String =
        date2strDate(getNowDate(), CDateFormat.yyyyMMddHH, locale)

    /**
     * 获取当前小时
     * @return Long
     */
    @JvmStatic
    fun getCurrentHourLong(locale: Locale = Locale.CHINA): Long =
        strDate2longDate(getCurrentHourStr(locale), CDateFormat.yyyyMMddHH, locale)

    /**
     * 今日
     * @param locale Locale
     * @return String
     */
    @JvmStatic
    @JvmOverloads
    fun getTodayStr(locale: Locale = Locale.CHINA, formatDate: String = CDateFormat.yyyyMMdd): String =
        date2strDate(getNowDate(), formatDate, locale)

    /**
     * 今日Long
     * @param locale Locale
     * @return Long
     */
    @JvmStatic
    fun getTodayLong(locale: Locale = Locale.CHINA): Long =
        strDate2longDate(getTodayStr(locale), CDateFormat.yyyyMMdd, locale)

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

    /////////////////////////////////////////////////////////////////////////////////////

    /**
     * date转long
     * @param date Date
     * @return Long
     */
    @JvmStatic
    fun date2longDate(date: Date): Long {
        return date.time
    }

    /**
     * long转date
     * @param longDate Long
     * @return Date
     */
    fun longDate2date(longDate: Long): Date {
        return Date(longDate)
    }

    /**
     * 日期转字符串
     * @param date Date
     * @param formatDate String
     * @param locale Locale? 时区
     * @return String
     */
    @JvmStatic
    fun date2strDate(date: Date, formatDate: String, locale: Locale = Locale.CHINA): String {
        return getSdf(formatDate, locale).format(date)
    }

    /**
     * 字符串转日期
     * @param strDate String
     * @param formatDate String
     * @param locale Locale?
     * @return Date
     */
    @JvmStatic
    fun strDate2date(strDate: String, formatDate: String, locale: Locale = Locale.CHINA): Date {
        return getSdf(formatDate, locale).parse(strDate) ?: kotlin.run {
            Log.e(TAG, "str2Date Exception time format fail!")
            throw Exception("time format fail!")
        }
    }

    /**
     * 长整型转字符串
     * @param longDate Long
     * @param formatDate String
     * @param locale Locale?
     * @return String
     */
    @JvmStatic
    fun longDate2strDate(longDate: Long, formatDate: String, locale: Locale = Locale.CHINA): String {
        return getSdf(formatDate, locale).format(longDate)
    }

    /**
     * String转long
     * @param strDate String
     * @param formatDate String
     * @param locale Locale
     * @return Long
     */
    @JvmStatic
    fun strDate2longDate(strDate: String, formatDate: String, locale: Locale = Locale.CHINA): Long {
        return date2longDate(strDate2date(strDate, formatDate, locale))
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
        dateCompare(strDate2date(date1, formatDate), strDate2date(date2, formatDate))
}