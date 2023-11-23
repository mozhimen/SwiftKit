package com.mozhimen.basick.utilk.java.util

import android.annotation.SuppressLint
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
     */
    @JvmStatic
    fun getNowDate(): Date =
        Date()

    /**
     * 获取当前时间
     */
    @JvmStatic
    fun getNowStr(formatDate: String = CDateFormat.yyyy_MM_dd_HH_mm_ss, locale: Locale = Locale.CHINA): String =
        date2strDate(getNowDate(), formatDate, locale)

    /**
     * 获取现在日期
     */
    @JvmStatic
    fun getNowLong(): Long =
        date2longDate(getNowDate())

    /**
     * 获取当前小时
     */
    @JvmStatic
    fun getCurrentHourStr(locale: Locale = Locale.CHINA): String =
        date2strDate(getNowDate(), CDateFormat.yyyy_MM_dd_HH, locale)

    /**
     * 获取当前小时
     */
    @JvmStatic
    fun getCurrentHourLong(locale: Locale = Locale.CHINA): Long =
        strDate2longDate(getCurrentHourStr(locale), CDateFormat.yyyy_MM_dd_HH, locale)

    /**
     * 今日
     */
    @JvmStatic
    @JvmOverloads
    fun getTodayStr(locale: Locale = Locale.CHINA, formatDate: String = CDateFormat.yyyy_MM_dd): String =
        date2strDate(getNowDate(), formatDate, locale)

    /**
     * 今日Long
     */
    @JvmStatic
    fun getTodayLong(locale: Locale = Locale.CHINA): Long =
        strDate2longDate(getTodayStr(locale), CDateFormat.yyyy_MM_dd, locale)

    /**
     * 获得Format
     */
    @JvmStatic
    fun getSdf(
        formatDate: String, locale: Locale = Locale.CHINA
    ): SimpleDateFormat =
        SimpleDateFormat(formatDate, locale)

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    fun getSdf1(
        formatDate: String
    ): SimpleDateFormat =
        SimpleDateFormat(formatDate,Locale.getDefault())

    /////////////////////////////////////////////////////////////////////////////////////

    /**
     * date转long
     */
    @JvmStatic
    fun date2longDate(date: Date): Long {
        return date.time
    }

    /**
     * long转date
     */
    @JvmStatic
    fun longDate2date(longDate: Long): Date {
        return Date(longDate)
    }

    /**
     * 日期转字符串
     */
    @JvmStatic
    fun date2strDate(date: Date, formatDate: String, locale: Locale = Locale.CHINA): String {
        return getSdf(formatDate, locale).format(date)
    }

    /**
     * 字符串转日期
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
     */
    @JvmStatic
    fun longDate2strDate(longDate: Long, formatDate: String, locale: Locale = Locale.CHINA): String {
        return getSdf(formatDate, locale).format(longDate)
    }

    /**
     * String转long
     */
    @JvmStatic
    fun strDate2longDate(strDate: String, formatDate: String, locale: Locale = Locale.CHINA): Long {
        return date2longDate(strDate2date(strDate, formatDate, locale))
    }

    /**
     * 时间比较
     * @return Int -1: date2 larger, 1: date1 larger, 0: same
     */
    @JvmStatic
    fun dateCompare(date1: Date, date2: Date): Int =
        date1.compareTo(date2)

    /**
     * 时间比较
     */
    @JvmStatic
    fun dateCompare(date1: String, date2: String, formatDate: String) =
        dateCompare(strDate2date(date1, formatDate), strDate2date(date2, formatDate))
}