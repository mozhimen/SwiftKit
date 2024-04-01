package com.mozhimen.basick.utilk.java.util

import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.java.util.cons.CDateFormat
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.text.UtilKSimpleDateFormat
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
    UtilKDateWrapper.date2longDate(this)

fun Date.date2strDate(strFormatDate: String, locale: Locale = Locale.CHINA): String =
    UtilKDateWrapper.date2strDate(this, strFormatDate, locale)

fun Long.longDate2date(): Date =
    UtilKDateWrapper.longDate2date(this)

fun Long.longDate2strDate(strFormatDate: String, locale: Locale = Locale.CHINA): String =
    UtilKDateWrapper.longDate2strDate(this, strFormatDate, locale)

fun String.strDate2date(strFormatDate: String, locale: Locale = Locale.CHINA): Date? =
    UtilKDateWrapper.strDate2date(this, strFormatDate, locale)

fun String.strDate2longDate(strFormatDate: String, locale: Locale = Locale.CHINA): Long? =
    UtilKDateWrapper.strDate2longDate(this, strFormatDate, locale)

///////////////////////////////////////////////////////////////////////////

object UtilKDateWrapper : BaseUtilK() {
    //获取现在日期
    @JvmStatic
    fun getNowDate(): Date =
        UtilKDate.get()

    //获取当前时间
    @JvmStatic
    fun getNowStr(strFormatDate: String = CDateFormat.yyyy_MM_dd_HH_mm_ss, locale: Locale = Locale.CHINA): String =
        date2strDate(getNowDate(), strFormatDate, locale)

    //获取现在日期
    @JvmStatic
    fun getNowLong(): Long =
        date2longDate(getNowDate())

    /////////////////////////////////////////////////////////////////////////////////////

    //获取当前小时
    @JvmStatic
    fun getCurrentHourStr(locale: Locale = Locale.CHINA): String =
        date2strDate(getNowDate(), CDateFormat.yyyy_MM_dd_HH, locale)

    //获取当前小时2位
    @JvmStatic
    fun getCurrentHourStr_ofHH(): String =
        getNowStr(CDateFormat.HH)

    @JvmStatic
    fun getCurrentHourStr_ofHHmm(): String =
        getNowStr(CDateFormat.HH_mm)

    //获取当前小时
    @JvmStatic
    fun getCurrentHourLong(locale: Locale = Locale.CHINA): Long? =
        strDate2longDate(getCurrentHourStr(locale), CDateFormat.yyyy_MM_dd_HH, locale)

    //今日
    @JvmStatic
    @JvmOverloads
    fun getTodayStr(locale: Locale = Locale.CHINA, strFormatDate: String = CDateFormat.yyyy_MM_dd): String =
        date2strDate(getNowDate(), strFormatDate, locale)

    //今日Long
    @JvmStatic
    fun getTodayLong(locale: Locale = Locale.CHINA): Long? =
        strDate2longDate(getTodayStr(locale), CDateFormat.yyyy_MM_dd, locale)

    /////////////////////////////////////////////////////////////////////////////////////

    //date转long
    @JvmStatic
    fun date2longDate(date: Date): Long =
        UtilKDate.getTime(date)

    //日期转字符串
    @JvmStatic
    fun date2strDate(date: Date, strFormatDate: String, locale: Locale = Locale.CHINA): String =
        UtilKSimpleDateFormat.get(strFormatDate, locale).format(date)

    //long转date
    @JvmStatic
    fun longDate2date(longDate: Long): Date =
        UtilKDate.get(longDate)//Date(longDate)

    //长整型转字符串
    @JvmStatic
    fun longDate2strDate(longDate: Long, strFormatDate: String, locale: Locale = Locale.CHINA): String =
        UtilKSimpleDateFormat.get(strFormatDate, locale).format(longDate)

    //字符串转日期
    @JvmStatic
    fun strDate2date(strDate: String, strFormatDate: String, locale: Locale = Locale.CHINA): Date? =
        UtilKSimpleDateFormat.get(strFormatDate, locale).parse(strDate)

    //String转long
    @JvmStatic
    fun strDate2longDate(strDate: String, strFormatDate: String, locale: Locale = Locale.CHINA): Long? =
        strDate2date(strDate, strFormatDate, locale)?.let { date2longDate(it) }
}