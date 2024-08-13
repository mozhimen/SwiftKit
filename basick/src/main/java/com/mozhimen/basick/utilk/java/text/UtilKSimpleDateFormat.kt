package com.mozhimen.basick.utilk.java.text

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * @ClassName UtilKSimpleDateFormat
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/29
 * @Version 1.0
 */
@SuppressLint("SimpleDateFormat")
object UtilKSimpleDateFormat {
    @JvmStatic
    fun get(pattern: String): SimpleDateFormat =
        SimpleDateFormat(pattern)

    @JvmStatic
    fun get(pattern: String, locale: Locale): SimpleDateFormat =
        SimpleDateFormat(pattern, locale)

    @JvmStatic
    fun get(pattern: String, decimalFormatSymbols: DateFormatSymbols): SimpleDateFormat =
        SimpleDateFormat(pattern, decimalFormatSymbols)

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun format(simpleDateFormat: SimpleDateFormat, date: Date): String =
        simpleDateFormat.format(date)

    @JvmStatic
    fun format(simpleDateFormat: SimpleDateFormat, obj: Any): String =
        simpleDateFormat.format(obj)

    @JvmStatic
    fun parse(simpleDateFormat: SimpleDateFormat, source: String): Date? =
        simpleDateFormat.parse(source)
}