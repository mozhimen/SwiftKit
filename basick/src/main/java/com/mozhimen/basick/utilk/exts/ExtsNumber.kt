package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.java.datatype.UtilKNumber

/**
 * @ClassName ExtsKNumber
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/16 19:39
 * @Version 1.0
 */
fun Double.keepFourDigits(): Double =
    UtilKNumber.keepFourDigits(this)

fun Double.keepFourDigitsStr(): String =
    UtilKNumber.keepFourDigitsStr(this)

/////////////////////////////////////////////////////

fun Float.keepFourDigits(): Float =
    UtilKNumber.keepFourDigits(this)

fun Float.keepFourDigitsStr(): String =
    UtilKNumber.keepFourDigitsStr(this)

/////////////////////////////////////////////////////

fun Double.keepThreeDigits(): Double =
    UtilKNumber.keepThreeDigits(this)

fun Double.keepThreeDigitsStr(): String =
    UtilKNumber.keepThreeDigitsStr(this)

/////////////////////////////////////////////////////

fun Float.keepThreeDigits(): Float =
    UtilKNumber.keepThreeDigits(this)

fun Float.keepThreeDigitsStr(): String =
    UtilKNumber.keepThreeDigitsStr(this)

/////////////////////////////////////////////////////

fun Double.keepTwoDigits(): Double =
    UtilKNumber.keepTwoDigits(this)

fun Double.keepTwoDigitsStr(): String =
    UtilKNumber.keepTwoDigitsStr(this)

/////////////////////////////////////////////////////

fun Float.keepTwoDigits(): Float =
    UtilKNumber.keepTwoDigits(this)

fun Float.keepTwoDigitsStr(): String =
    UtilKNumber.keepTwoDigitsStr(this)

/////////////////////////////////////////////////////

fun Double.keepOneDigits(): Double =
    UtilKNumber.keepOneDigits(this)

fun Double.keepOneDigitsStr(): String =
    UtilKNumber.keepOneDigitsStr(this)

/////////////////////////////////////////////////////

fun Float.keepOneDigits(): Float =
    UtilKNumber.keepOneDigits(this)

fun Float.keepOneDigitsStr(): String =
    UtilKNumber.keepOneDigitsStr(this)

/////////////////////////////////////////////////////

fun Double.normalize(min: Double, max: Double): Double =
    UtilKNumber.normalize(this, min, max)

fun Long.normalize(min: Long, max: Long): Long =
    UtilKNumber.normalize(this, min, max)

fun Float.normalize(min: Float, max: Float): Float =
    UtilKNumber.normalize(this, min, max)

fun Int.normalize(min: Int, max: Int): Int =
    UtilKNumber.normalize(this, min, max)

/////////////////////////////////////////////////////

fun Double.normalize(range: Pair<Double, Double>): Double =
    UtilKNumber.normalize(this, range.first, range.second)

fun Long.normalize(range: Pair<Long, Long>): Long =
    UtilKNumber.normalize(this, range.first, range.second)

fun Float.normalize(range: Pair<Float, Float>): Float =
    UtilKNumber.normalize(this, range.first, range.second)

fun Int.normalize(range: Pair<Int, Int>): Int =
    UtilKNumber.normalize(this, range.first, range.second)

/////////////////////////////////////////////////////

fun Int.normalize(range: IntRange): Int =
    UtilKNumber.normalize(this, range.first, range.last)

fun Long.normalize(range: LongRange): Long =
    UtilKNumber.normalize(this, range.first, range.last)

/////////////////////////////////////////////////////

fun Float.percent(start: Float, end: Float): Float =
    UtilKNumber.percent(this, start, end)

fun Float.percent(range: Pair<Float, Float>): Float =
    UtilKNumber.percent(this, range.first, range.second)

fun Double.percent(start: Double, end: Double): Double =
    UtilKNumber.percent(this, start, end)

fun Double.percent(range: Pair<Double, Double>): Double =
    UtilKNumber.percent(this, range.first, range.second)
