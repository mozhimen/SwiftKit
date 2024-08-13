package com.mozhimen.basick.utilk.java.text

import com.mozhimen.basick.utilk.java.util.UtilKLocale
import java.text.DateFormat
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName UtilKDateFormatWrapper
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
object UtilKDateFormatWrapper {
    private val _dateFormats by lazy { ConcurrentHashMap<String, DateFormat>() }

    ///////////////////////////////////////////////////////////////////

    @JvmStatic
    fun get(): DateFormat =
        get(DateFormat.DEFAULT)

    @JvmStatic
    fun get(style: Int): DateFormat =
        get(style, UtilKLocale.get_ofDefault())

    @JvmStatic
    fun get(style: Int, locale: Locale): DateFormat {
        val key = "$style$locale"
        if (_dateFormats.containsKey(key))
            return _dateFormats[key]!!
        else {
            val dateFormat = UtilKDateFormat.get_ofDate(style, locale)
            _dateFormats[key] = dateFormat
            return dateFormat
        }
    }
}