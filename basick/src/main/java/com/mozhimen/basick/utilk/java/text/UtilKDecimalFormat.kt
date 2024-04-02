package com.mozhimen.basick.utilk.java.text

import com.mozhimen.basick.utilk.kotlin.text.addStart_of0
import com.mozhimen.basick.utilk.kotlin.text.replaceDot
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
fun Double.getStrDecimal(bit: Int, locale: Locale? = null): String =
    UtilKDecimalFormat.getStrDecimal(this, bit,locale)

fun Double.getDoubleDecimal(bit: Int,locale: Locale? = null): Double =
    UtilKDecimalFormat.getDoubleDecimal(this, bit,locale)

fun Float.getStrDecimal(bit: Int,locale: Locale? = null): String =
    UtilKDecimalFormat.getStrDecimal(this,bit, locale)

fun Float.getFloatDecimal(bit: Int,locale: Locale? = null): Float =
    UtilKDecimalFormat.getFloatDecimal(this,bit, locale)

//////////////////////////////////////////////////////////////////////

fun Double.getStrDecimal_of1(locale: Locale? = null): String =
    UtilKDecimalFormat.getStrDecimal_of1(this, locale)

fun Double.getDoubleDecimal_of1(locale: Locale? = null): Double =
    UtilKDecimalFormat.getDoubleDecimal_of1(this, locale)

fun Float.getStrDecimal_of1(locale: Locale? = null): String =
    UtilKDecimalFormat.getStrDecimal_of1(this, locale)

fun Float.getFloatDecimal_of1(locale: Locale? = null): Float =
    UtilKDecimalFormat.getFloatDecimal_of1(this, locale)

//////////////////////////////////////////////////////////////////////

fun Double.getStrDecimal_of2(locale: Locale? = null): String =
    UtilKDecimalFormat.getStrDecimal_of2(this, locale)

fun Double.getDoubleDecimal_of2(locale: Locale? = null): Double =
    UtilKDecimalFormat.getDoubleDecimal_of2(this, locale)

fun Float.getStrDecimal_of2(locale: Locale? = null): String =
    UtilKDecimalFormat.getStrDecimal_of2(this, locale)

fun Float.getFloatDecimal_of2(locale: Locale? = null): Float =
    UtilKDecimalFormat.getFloatDecimal_of2(this, locale)

//////////////////////////////////////////////////////////////////////

object UtilKDecimalFormat {
    @JvmStatic
    fun get(pattern: String): DecimalFormat =
        DecimalFormat(pattern/*, DecimalFormatSymbols()*/)

    @JvmStatic
    fun get(pattern: String, locale: Locale?): DecimalFormat =
        if (locale == null)
            get(pattern)
        else
            DecimalFormat(pattern, DecimalFormatSymbols(locale))

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun get_ofBit(bit: Int, locale: Locale?): DecimalFormat {
        var pattern = "#."
        repeat(bit) {
            pattern += "0"
        }
        return get(pattern, locale)
    }

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrDecimal(obj: Any, bit: Int, locale: Locale?): String =
        get_ofBit(bit, locale).format(obj).addStart_of0()

    @JvmStatic
    fun getDoubleDecimal(obj: Any, bit: Int, locale: Locale?): Double =
        get_ofBit(bit, locale).format(obj).addStart_of0().toDouble()

    @JvmStatic
    fun getFloatDecimal(obj: Any, bit: Int, locale: Locale?): Float =
        get_ofBit(bit, locale).format(obj).addStart_of0().toFloat()

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrDecimal_of1(obj: Any, locale: Locale? = null): String =
        getStrDecimal(obj, 1, locale)

    @JvmStatic
    fun getDoubleDecimal_of1(obj: Any, locale: Locale? = null): Double =
        getDoubleDecimal(obj, 1, locale)

    @JvmStatic
    fun getFloatDecimal_of1(obj: Any, locale: Locale? = null): Float =
        getFloatDecimal(obj, 1, locale)

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrDecimal_of2(obj: Any, locale: Locale? = null): String =
        getStrDecimal(obj, 2, locale)

    @JvmStatic
    fun getDoubleDecimal_of2(obj: Any, locale: Locale? = null): Double =
        getDoubleDecimal(obj, 2, locale)

    @JvmStatic
    fun getFloatDecimal_of2(obj: Any, locale: Locale? = null): Float =
        getFloatDecimal(obj, 2, locale)
}