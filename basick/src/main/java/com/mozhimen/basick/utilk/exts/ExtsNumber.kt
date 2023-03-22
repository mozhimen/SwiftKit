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

fun Float.keepFourDigits(): Float =
    UtilKNumber.keepFourDigits(this)

fun Double.keepThreeDigits(): Double =
    UtilKNumber.keepThreeDigits(this)

fun Float.keepThreeDigits(): Float =
    UtilKNumber.keepThreeDigits(this)

fun Double.keepTwoDigits(): Double =
    UtilKNumber.keepTwoDigits(this)

fun Float.keepTwoDigits(): Float =
    UtilKNumber.keepTwoDigits(this)

fun Double.keepOneDigits(): Double =
    UtilKNumber.keepOneDigits(this)

fun Float.keepOneDigits(): Float =
    UtilKNumber.keepOneDigits(this)

fun Long.normalize(min: Long, max: Long): Long =
    UtilKNumber.normalize(this, min, max)

fun Float.normalize(min: Float, max: Float): Float =
    UtilKNumber.normalize(this, min, max)

fun Float.normalize(range: Pair<Float, Float>): Float =
    UtilKNumber.normalize(this, range.first, range.second)

fun Double.normalize(min: Double, max: Double): Double =
    UtilKNumber.normalize(this, min, max)

fun Int.normalize(min: Int, max: Int): Int =
    UtilKNumber.normalize(this, min, max)

fun Int.normalize(range: IntRange): Int =
    UtilKNumber.normalize(this, range.first, range.last)

fun Int.normalize(range: Pair<Int, Int>): Int =
    UtilKNumber.normalize(this, range.first, range.second)

fun Float.percent(start: Float, end: Float): Float =
    UtilKNumber.percent(this, start, end)

fun Float.percent(range: Pair<Float, Float>): Float =
    UtilKNumber.percent(this, range.first, range.second)

fun Double.percent(start: Double, end: Double): Double =
    UtilKNumber.percent(this, start, end)

fun Double.percent(range: Pair<Double, Double>): Double =
    UtilKNumber.percent(this, range.first, range.second)
