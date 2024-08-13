package com.mozhimen.basick.utilk.java.text

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * @ClassName UtilKDecimalFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/29 14:40
 * @Version 1.0
 */
object UtilKDecimalFormat {
    @JvmStatic
    fun get(pattern: String): DecimalFormat =
        DecimalFormat(pattern/*, DecimalFormatSymbols()*/)

    @JvmStatic
    fun get(pattern: String, locale: Locale): DecimalFormat =
        DecimalFormat(pattern, DecimalFormatSymbols(locale))

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun get_ofBit(bit: Int, locale: Locale?): DecimalFormat {
        var pattern = "#."
        repeat(bit) {
            pattern += "0"
        }
        return locale?.let { get(pattern, it) } ?: get(pattern)
    }
}