package com.mozhimen.basick.utilk.java.text

import java.text.DateFormat
import java.util.Date
import java.util.Locale

/**
 * @ClassName UtilKDateFormat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
object UtilKDateFormat {

    @JvmStatic
    fun get_ofDate(style: Int, locale: Locale): DateFormat =
        getDateInstance(style, locale)

    @JvmStatic
    fun get_ofDate(style: Int): DateFormat =
        getDateInstance(style)

    @JvmStatic
    fun get_ofDate(): DateFormat =
        getDateInstance()

    ///////////////////////////////////////////////////////////////

    @JvmStatic
    fun getDateInstance(): DateFormat =
        DateFormat.getDateInstance()

    @JvmStatic
    fun getDateInstance(style: Int): DateFormat =
        DateFormat.getDateInstance(style)

    @JvmStatic
    fun getDateInstance(style: Int, locale: Locale): DateFormat =
        DateFormat.getDateInstance(style, locale)

    ///////////////////////////////////////////////////////////////

    @JvmStatic
    fun format(dateFormat: DateFormat, date: Date): String =
        dateFormat.format(date)
}