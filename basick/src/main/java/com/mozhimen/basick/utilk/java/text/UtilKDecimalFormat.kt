package com.mozhimen.basick.utilk.java.text

import com.mozhimen.basick.utilk.kotlin.text.replaceDot
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexLineBreak
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @ClassName UtilKDecimalFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/29 14:40
 * @Version 1.0
 */
fun Double.getStrDecimal(bit: Int): String =
    UtilKDecimalFormat.getStrDecimal(this, bit)

fun Double.getStrDecimal(bit: Int, roundingMode: RoundingMode): String =
    UtilKDecimalFormat.getStrDecimal(this, bit, roundingMode)

fun Float.getStrDecimal(bit: Int): String =
    UtilKDecimalFormat.getStrDecimal(this, bit)

fun Float.getStrDecimal(bit: Int, roundingMode: RoundingMode): String =
    UtilKDecimalFormat.getStrDecimal(this, bit, roundingMode)

//////////////////////////////////////////////////////////////////////

fun Float.getFloatDecimal(bit: Int): Float =
    UtilKDecimalFormat.getFloatDecimal(this, bit)

fun Double.getDoubleDecimal(bit: Int): Double =
    UtilKDecimalFormat.getDoubleDecimal(this, bit)

//////////////////////////////////////////////////////////////////////

fun Double.getStrDecimalOf1(): String =
    UtilKDecimalFormat.getStrDecimalOf1(this)

fun Double.getStrDecimalOf2(): String =
    UtilKDecimalFormat.getStrDecimalOf2(this)

fun Float.getStrDecimalOf1(): String =
    UtilKDecimalFormat.getStrDecimalOf1(this)

fun Float.getStrDecimalOf2(): String =
    UtilKDecimalFormat.getStrDecimalOf2(this)

//////////////////////////////////////////////////////////////////////

fun Double.getDoubleDecimalOf1(): Double =
    UtilKDecimalFormat.getDoubleDecimalOf1(this)

fun Double.getDoubleDecimalOf2(): Double =
    UtilKDecimalFormat.getDoubleDecimalOf2(this)

//////////////////////////////////////////////////////////////////////

fun Float.getFloatDecimalOf1(): Float =
    UtilKDecimalFormat.getFloatDecimalOf1(this)

fun Float.getFloatDecimalOf2(): Float =
    UtilKDecimalFormat.getFloatDecimalOf2(this)

//////////////////////////////////////////////////////////////////////

object UtilKDecimalFormat {
    @JvmStatic
    fun get(pattern: String): DecimalFormat =
        DecimalFormat(pattern)

    @JvmStatic
    fun getOf(bit: Int): DecimalFormat {
        var pattern = "#."
        repeat(bit) {
            pattern += "0"
        }
        return get(pattern)
    }

    @JvmStatic
    fun getOf(bit: Int, roundingMode: RoundingMode): DecimalFormat =
        getOf(bit).apply { this.roundingMode = roundingMode }

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrDecimal(obj: Any, bit: Int): String =
        getOf(bit).format(obj).replaceDot()

    @JvmStatic
    fun getStrDecimal(obj: Any, bit: Int, roundingMode: RoundingMode): String =
        getOf(bit, roundingMode).format(obj).replaceDot()

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getDoubleDecimal(obj: Any, bit: Int): Double =
        getStrDecimal(obj, bit).toDouble()

    @JvmStatic
    fun getFloatDecimal(obj: Any, bit: Int): Float =
        getStrDecimal(obj, bit).toFloat()

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getStrDecimalOf1(obj: Any): String =
        getStrDecimal(obj, 1)

    @JvmStatic
    fun getStrDecimalOf2(obj: Any): String =
        getStrDecimal(obj, 2)

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getDoubleDecimalOf1(obj: Any): Double =
        getStrDecimalOf1(obj).toDouble()

    @JvmStatic
    fun getDoubleDecimalOf2(obj: Any): Double =
        getStrDecimalOf2(obj).toDouble()

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getFloatDecimalOf1(obj: Any): Float =
        getStrDecimalOf1(obj).toFloat()

    @JvmStatic
    fun getFloatDecimalOf2(obj: Any): Float =
        getStrDecimalOf2(obj).toFloat()
}