package com.mozhimen.basick.utilk.java.text

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * @ClassName UtilKDecimalFormat
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/10/29 14:40
 * @Version 1.0
 */
fun Double.getStrDecimal(bit: Int):String =
    UtilKDecimalFormat.getStrDecimal(this,bit)

fun Double.getStrDecimal(bit: Int,roundingMode: RoundingMode):String =
    UtilKDecimalFormat.getStrDecimal(this,bit, roundingMode)

fun Double.getStrDecimalOf1():String =
    UtilKDecimalFormat.getStrDecimalOf1(this)

fun Double.getStrDecimalOf2():String =
    UtilKDecimalFormat.getStrDecimalOf2(this)

object UtilKDecimalFormat {
    @JvmStatic
    fun get(pattern:String):DecimalFormat =
        DecimalFormat(pattern)

    @JvmStatic
    fun getOf(bit:Int):DecimalFormat{
        var pattern = "#."
        repeat(bit){
            pattern+="0"
        }
        return get(pattern)
    }

    @JvmStatic
    fun getOf(bit:Int,roundingMode : RoundingMode):DecimalFormat{
        var pattern = "#."
        repeat(bit){
            pattern+="0"
        }
        return get(pattern).apply { this.roundingMode = roundingMode }
    }

    @JvmStatic
    fun getStrDecimal(obj: Any, bit: Int):String =
        getOf(bit).format(obj)

    @JvmStatic
    fun getStrDecimal(obj: Any, bit: Int,roundingMode: RoundingMode):String =
        getOf(bit,roundingMode).format(obj)

    @JvmStatic
    fun getStrDecimalOf1(obj: Any):String =
        getStrDecimal(obj,1)

    @JvmStatic
    fun getStrDecimalOf2(obj: Any):String =
        getStrDecimal(obj,2)
}