package com.mozhimen.basick.utilk.java.text

import com.mozhimen.basick.utilk.java.util.UtilKLocale
import java.text.DecimalFormat
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName UtilKDecimalFormatWrapper
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
object UtilKDecimalFormatWrapper {
    private val _decimalFormats by lazy { ConcurrentHashMap<String, DecimalFormat>() }

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun get(pattern: String): DecimalFormat =
        get(pattern, UtilKLocale.get_ofDefault())

    @JvmStatic
    fun get(pattern: String, locale: Locale): DecimalFormat {
        val key = "$pattern$locale"
        if (_decimalFormats.containsKey(key)) {
            return _decimalFormats[key]!!
        } else {
            val decimalFormat = UtilKDecimalFormat.get(pattern, locale)
            _decimalFormats[key] = decimalFormat
            return decimalFormat
        }
    }

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun get_ofBit(bit: Int): DecimalFormat {
        var pattern = "#."
        repeat(bit) {
            pattern += "0"
        }
        return get(pattern)
    }

    @JvmStatic
    fun get_ofBit(bit: Int, locale: Locale): DecimalFormat {
        var pattern = "#."
        repeat(bit) {
            pattern += "0"
        }
        return get(pattern, locale)
    }
}