package com.mozhimen.basick.utilk.java.text

import android.content.Context
import com.mozhimen.basick.elemk.android.view.cons.CMotionEvent
import com.mozhimen.basick.lintk.annors.AFormatStyle
import com.mozhimen.basick.utilk.android.text.UtilKDateFormat
import com.mozhimen.basick.utilk.java.util.UtilKDate
import java.time.format.FormatStyle
import java.util.Date
import java.util.Locale

/**
 * @ClassName UtilKSimpleDateFormatFormat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
fun Long.longDate2strDate(strFormatDate: String): String =
    UtilKSimpleDateFormatFormat.longDate2strDate(this, strFormatDate)

fun Long.longDate2strDate(strFormatDate: String, locale: Locale): String =
    UtilKSimpleDateFormatFormat.longDate2strDate(this, strFormatDate, locale)

fun Long.longDate2date(): Date =
    UtilKSimpleDateFormatFormat.longDate2date(this)

///////////////////////////////////////////////////////////////////////////

fun Date.date2strDate(strFormatDate: String): String =
    UtilKSimpleDateFormatFormat.date2strDate(this, strFormatDate)

fun Date.date2strDate(strFormatDate: String, locale: Locale): String =
    UtilKSimpleDateFormatFormat.date2strDate(this, strFormatDate, locale)

fun Date.date2strDate(context: Context, @AFormatStyle style: Int): String =
    UtilKSimpleDateFormatFormat.date2strDate(this, context, style)

fun Date.date2strDate(locale: Locale, skeleton: String): String =
    UtilKSimpleDateFormatFormat.date2strDate(this, locale, skeleton)

fun Date.date2longDate(): Long =
    UtilKSimpleDateFormatFormat.date2longDate(this)

///////////////////////////////////////////////////////////////////////////

fun String.strDate2date(strFormatDate: String): Date? =
    UtilKSimpleDateFormatFormat.strDate2date(this, strFormatDate)

fun String.strDate2date(strFormatDate: String, locale: Locale): Date? =
    UtilKSimpleDateFormatFormat.strDate2date(this, strFormatDate, locale)

fun String.strDate2longDate(strFormatDate: String): Long? =
    UtilKSimpleDateFormatFormat.strDate2longDate(this, strFormatDate)

fun String.strDate2longDate(strFormatDate: String, locale: Locale): Long? =
    UtilKSimpleDateFormatFormat.strDate2longDate(this, strFormatDate, locale)

///////////////////////////////////////////////////////////////////////////

object UtilKSimpleDateFormatFormat {
    //长整型转字符串
    @JvmStatic
    fun longDate2strDate(longDate: Long, strFormatDate: String): String =
        UtilKSimpleDateFormat.format(UtilKSimpleDateFormatWrapper.get(strFormatDate), longDate)

    @JvmStatic
    fun longDate2strDate(longDate: Long, strFormatDate: String, locale: Locale): String =
        UtilKSimpleDateFormat.format(UtilKSimpleDateFormatWrapper.get(strFormatDate, locale), longDate)

    //long转date
    @JvmStatic
    fun longDate2date(longDate: Long): Date =
        UtilKDate.get(longDate)//Date(longDate)

    ///////////////////////////////////////////////////////////////////////////

    //日期转字符串
    @JvmStatic
    fun date2strDate(date: Date, strFormatDate: String): String =
        UtilKSimpleDateFormat.format(UtilKSimpleDateFormatWrapper.get(strFormatDate), date)

    @JvmStatic
    fun date2strDate(date: Date, strFormatDate: String, locale: Locale): String =
        UtilKSimpleDateFormat.format(UtilKSimpleDateFormatWrapper.get(strFormatDate, locale), date)

    @JvmStatic
    fun date2strDate(date: Date, context: Context, @AFormatStyle style: Int): String =
        date2strDate(date, UtilKSimpleDateFormat.toLocalizedPattern(UtilKSimpleDateFormatWrapper.get(context, style)))

    @JvmStatic
    fun date2strDate(date: Date, locale: Locale, skeleton: String): String {
        val newSkeleton = UtilKDateFormat.getBestDateTimePattern(locale, skeleton)
        return UtilKSimpleDateFormat.format(UtilKSimpleDateFormatWrapper.get(newSkeleton, locale), date)
    }

    //date转long
    @JvmStatic
    fun date2longDate(date: Date): Long =
        UtilKDate.getTime(date)

    ///////////////////////////////////////////////////////////////////////////

    //字符串转日期
    @JvmStatic
    fun strDate2date(strDate: String, strFormatDate: String): Date? =
        UtilKSimpleDateFormat.parse(UtilKSimpleDateFormatWrapper.get(strFormatDate), strDate)

    @JvmStatic
    fun strDate2date(strDate: String, strFormatDate: String, locale: Locale): Date? =
        UtilKSimpleDateFormat.parse(UtilKSimpleDateFormatWrapper.get(strFormatDate, locale), strDate)

    //String转long
    @JvmStatic
    fun strDate2longDate(strDate: String, strFormatDate: String): Long? =
        strDate2date(strDate, strFormatDate)?.let { date2longDate(it) }

    @JvmStatic
    fun strDate2longDate(strDate: String, strFormatDate: String, locale: Locale): Long? =
        strDate2date(strDate, strFormatDate, locale)?.let { date2longDate(it) }
}