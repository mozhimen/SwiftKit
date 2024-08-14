package com.mozhimen.basick.utilk.android.text

import android.content.Context
import android.text.format.DateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * @ClassName UtilKDateFormat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/14
 * @Version 1.0
 */
object UtilKDateFormat {
    @JvmStatic
    fun getDateFormat(context: Context): java.text.DateFormat =
        DateFormat.getDateFormat(context)

    @JvmStatic
    fun getLongDateFormat(context: Context): java.text.DateFormat =
        DateFormat.getLongDateFormat(context)

    @JvmStatic
    fun getMediumDateFormat(context: Context): java.text.DateFormat =
        DateFormat.getMediumDateFormat(context)

    @JvmStatic
    fun getTimeFormat(context: Context): java.text.DateFormat =
        DateFormat.getTimeFormat(context)

    @JvmStatic
    fun getBestDateTimePattern(locale: Locale, skeleton: String): String =
        DateFormat.getBestDateTimePattern(locale, skeleton)

    ///////////////////////////////////////////////////////////////

    @JvmStatic
    fun format(strFormatDate: String, date: Date): CharSequence =
        DateFormat.format(strFormatDate, date)

    @JvmStatic
    fun format(strFormatDate: String, calendar: Calendar): CharSequence =
        DateFormat.format(strFormatDate, calendar)

    @JvmStatic
    fun format(strFormatDate: String, longDate: Long): CharSequence =
        DateFormat.format(strFormatDate, longDate)
}