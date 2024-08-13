package com.mozhimen.basick.utilk.java.text

import com.mozhimen.basick.utilk.java.util.UtilKLocale
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName UtilKSimpleDateFormatWrapper
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
object UtilKSimpleDateFormatWrapper {
    private val _simpleDateFormats by lazy { ConcurrentHashMap<String, SimpleDateFormat>() }

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun get(pattern: String, locale: Locale): SimpleDateFormat {
        val key = "$pattern$locale"
        if (_simpleDateFormats.containsKey(key))
            return _simpleDateFormats[key]!!
        else {
            val simpleDateFormat = UtilKSimpleDateFormat.get(pattern, locale)
            _simpleDateFormats[key] = simpleDateFormat
            return simpleDateFormat
        }
    }

    @JvmStatic
    fun get(pattern: String): SimpleDateFormat =
        get(pattern, UtilKLocale.get_ofDefault())
}