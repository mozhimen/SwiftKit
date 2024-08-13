package com.mozhimen.basick.utilk.java.text

import java.util.Date
import java.util.Locale

/**
 * @ClassName UtilKDateFormatFormat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
fun Date.date2strDate(): String =
    UtilKDateFormatFormat.date2strDate(this)

fun Date.date2strDate(style: Int): String =
    UtilKDateFormatFormat.date2strDate(this, style)

fun Date.date2strDate(style: Int, locale: Locale): String =
    UtilKDateFormatFormat.date2strDate(this, style, locale)

////////////////////////////////////////////////////////////////

object UtilKDateFormatFormat {

    @JvmStatic
    fun date2strDate(date: Date): String =
        UtilKDateFormat.format(UtilKDateFormatWrapper.get(), date)

    @JvmStatic
    fun date2strDate(date: Date, style: Int): String =
        UtilKDateFormat.format(UtilKDateFormatWrapper.get(style), date)

    @JvmStatic
    fun date2strDate(date: Date, style: Int, locale: Locale): String =
        UtilKDateFormat.format(UtilKDateFormatWrapper.get(style, locale), date)
}