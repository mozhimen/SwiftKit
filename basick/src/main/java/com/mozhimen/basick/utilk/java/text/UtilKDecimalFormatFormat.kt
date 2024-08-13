package com.mozhimen.basick.utilk.java.text

import com.mozhimen.basick.utilk.kotlin.text.addStart_of0_ofDot
import java.util.Locale

/**
 * @ClassName UtilKDecimalFormatFormat
 * @Description TODO
 * @Author mozhimen
 * @Date 2024/8/13
 * @Version 1.0
 */
fun Double.getStrDecimal(bit: Int): String =
    UtilKDecimalFormatFormat.getStrDecimal(this, bit)

fun Double.getStrDecimal(bit: Int, locale: Locale): String =
    UtilKDecimalFormatFormat.getStrDecimal(this, bit, locale)

fun Double.getDoubleDecimal(bit: Int): Double =
    UtilKDecimalFormatFormat.getDoubleDecimal(this, bit)

fun Double.getDoubleDecimal(bit: Int, locale: Locale): Double =
    UtilKDecimalFormatFormat.getDoubleDecimal(this, bit, locale)

fun Float.getStrDecimal(bit: Int): String =
    UtilKDecimalFormatFormat.getStrDecimal(this, bit)

fun Float.getFloatDecimal(bit: Int, locale: Locale): Float =
    UtilKDecimalFormatFormat.getFloatDecimal(this, bit, locale)

//////////////////////////////////////////////////////////////////////

fun Double.getStrDecimal_of1(): String =
    UtilKDecimalFormatFormat.getStrDecimal_of1(this)

fun Double.getStrDecimal_of1(locale: Locale): String =
    UtilKDecimalFormatFormat.getStrDecimal_of1(this, locale)

fun Double.getDoubleDecimal_of1(): Double =
    UtilKDecimalFormatFormat.getDoubleDecimal_of1(this)

fun Double.getDoubleDecimal_of1(locale: Locale): Double =
    UtilKDecimalFormatFormat.getDoubleDecimal_of1(this, locale)

fun Float.getStrDecimal_of1(): String =
    UtilKDecimalFormatFormat.getStrDecimal_of1(this)

fun Float.getStrDecimal_of1(locale: Locale): String =
    UtilKDecimalFormatFormat.getStrDecimal_of1(this, locale)

fun Float.getFloatDecimal_of1(): Float =
    UtilKDecimalFormatFormat.getFloatDecimal_of1(this)

fun Float.getFloatDecimal_of1(locale: Locale): Float =
    UtilKDecimalFormatFormat.getFloatDecimal_of1(this, locale)

//////////////////////////////////////////////////////////////////////

fun Double.getStrDecimal_of2(): String =
    UtilKDecimalFormatFormat.getStrDecimal_of2(this)

fun Double.getStrDecimal_of2(locale: Locale): String =
    UtilKDecimalFormatFormat.getStrDecimal_of2(this, locale)

fun Double.getDoubleDecimal_of2(): Double =
    UtilKDecimalFormatFormat.getDoubleDecimal_of2(this)

fun Double.getDoubleDecimal_of2(locale: Locale): Double =
    UtilKDecimalFormatFormat.getDoubleDecimal_of2(this, locale)

fun Float.getStrDecimal_of2(): String =
    UtilKDecimalFormatFormat.getStrDecimal_of2(this)

fun Float.getStrDecimal_of2(locale: Locale): String =
    UtilKDecimalFormatFormat.getStrDecimal_of2(this, locale)

fun Float.getFloatDecimal_of2(): Float =
    UtilKDecimalFormatFormat.getFloatDecimal_of2(this)

fun Float.getFloatDecimal_of2(locale: Locale): Float =
    UtilKDecimalFormatFormat.getFloatDecimal_of2(this, locale)

//////////////////////////////////////////////////////////////////////

object UtilKDecimalFormatFormat {

    @JvmStatic
    fun getStrDecimal(obj: Any, bit: Int): String =
        UtilKDecimalFormatWrapper.get_ofBit(bit).format(obj).addStart_of0_ofDot()

    @JvmStatic
    fun getDoubleDecimal(obj: Any, bit: Int): Double =
        UtilKDecimalFormatWrapper.get_ofBit(bit).format(obj).addStart_of0_ofDot().toDouble()

    @JvmStatic
    fun getFloatDecimal(obj: Any, bit: Int): Float =
        UtilKDecimalFormatWrapper.get_ofBit(bit).format(obj).addStart_of0_ofDot().toFloat()

    @JvmStatic
    fun getStrDecimal(obj: Any, bit: Int, locale: Locale): String =
        UtilKDecimalFormatWrapper.get_ofBit(bit, locale).format(obj).addStart_of0_ofDot()

    @JvmStatic
    fun getDoubleDecimal(obj: Any, bit: Int, locale: Locale): Double =
        UtilKDecimalFormatWrapper.get_ofBit(bit, locale).format(obj).addStart_of0_ofDot().toDouble()

    @JvmStatic
    fun getFloatDecimal(obj: Any, bit: Int, locale: Locale): Float =
        UtilKDecimalFormatWrapper.get_ofBit(bit, locale).format(obj).addStart_of0_ofDot().toFloat()

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrDecimal_of1(obj: Any): String =
        getStrDecimal(obj, 1)

    @JvmStatic
    fun getDoubleDecimal_of1(obj: Any): Double =
        getDoubleDecimal(obj, 1)

    @JvmStatic
    fun getFloatDecimal_of1(obj: Any): Float =
        getFloatDecimal(obj, 1)

    @JvmStatic
    fun getStrDecimal_of1(obj: Any, locale: Locale): String =
        getStrDecimal(obj, 1, locale)

    @JvmStatic
    fun getDoubleDecimal_of1(obj: Any, locale: Locale): Double =
        getDoubleDecimal(obj, 1, locale)

    @JvmStatic
    fun getFloatDecimal_of1(obj: Any, locale: Locale): Float =
        getFloatDecimal(obj, 1, locale)

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrDecimal_of2(obj: Any): String =
        getStrDecimal(obj, 2)

    @JvmStatic
    fun getDoubleDecimal_of2(obj: Any): Double =
        getDoubleDecimal(obj, 2)

    @JvmStatic
    fun getFloatDecimal_of2(obj: Any): Float =
        getFloatDecimal(obj, 2)

    @JvmStatic
    fun getStrDecimal_of2(obj: Any, locale: Locale): String =
        getStrDecimal(obj, 2, locale)

    @JvmStatic
    fun getDoubleDecimal_of2(obj: Any, locale: Locale): Double =
        getDoubleDecimal(obj, 2, locale)

    @JvmStatic
    fun getFloatDecimal_of2(obj: Any, locale: Locale): Float =
        getFloatDecimal(obj, 2, locale)
}