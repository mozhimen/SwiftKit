package com.mozhimen.basick.utilk.kotlin

import kotlin.math.max
import kotlin.math.min

/**
 * @ClassName UtilKNumber
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/31 17:33
 * @Version 1.0
 */
fun Double.constraint(min: Double, max: Double): Double =
    UtilKNumber.constraint(this, min, max)

fun Long.constraint(min: Long, max: Long): Long =
    UtilKNumber.constraint(this, min, max)

fun Float.constraint(min: Float, max: Float): Float =
    UtilKNumber.constraint(this, min, max)

fun Int.constraint(min: Int, max: Int): Int =
    UtilKNumber.constraint(this, min, max)

/////////////////////////////////////////////////////

fun Double.constraint(pair: Pair<Double, Double>): Double =
    UtilKNumber.constraint(this, pair.first, pair.second)

fun Long.constraint(pair: Pair<Long, Long>): Long =
    UtilKNumber.constraint(this, pair.first, pair.second)

fun Float.constraint(pair: Pair<Float, Float>): Float =
    UtilKNumber.constraint(this, pair.first, pair.second)

fun Int.constraint(pair: Pair<Int, Int>): Int =
    UtilKNumber.constraint(this, pair.first, pair.second)

/////////////////////////////////////////////////////

fun Int.constraint(range: IntRange): Int =
    UtilKNumber.constraint(this, range)

fun Long.constraint(range: LongRange): Long =
    UtilKNumber.constraint(this, range)

/////////////////////////////////////////////////////

fun Float.percent(start: Float, end: Float): Float =
    UtilKNumber.percent(this, start, end)

fun Float.percent(pair: Pair<Float, Float>): Float =
    UtilKNumber.percent(this, pair.first, pair.second)

fun Double.percent(start: Double, end: Double): Double =
    UtilKNumber.percent(this, start, end)

fun Double.percent(pair: Pair<Double, Double>): Double =
    UtilKNumber.percent(this, pair.first, pair.second)

/////////////////////////////////////////////////////

object UtilKNumber {

    /**
     * Complement by0
     * 左补0
     */
    @JvmStatic
    fun complement_of0(number: Number, decimal: Int): String =
        String.format("%0${decimal}d", number)

    ////////////////////////////////////////////////////////////

    @JvmStatic
    fun constraint(value: Long, min: Long, max: Long): Long {
        val tempRange = min(min, max) to max(min, max)
        return value.coerceIn(tempRange.first, tempRange.second)
    }

    @JvmStatic
    fun constraint(value: Float, min: Float, max: Float): Float {
        val tempRange = min(min, max) to max(min, max)
        return value.coerceIn(tempRange.first, tempRange.second)
    }

    @JvmStatic
    fun constraint(value: Double, min: Double, max: Double): Double {
        val tempRange = min(min, max) to max(min, max)
        return value.coerceIn(tempRange.first, tempRange.second)
    }

    @JvmStatic
    fun constraint(value: Int, min: Int, max: Int): Int {
        val tempRange = min(min, max) to max(min, max)
        return value.coerceIn(tempRange.first, tempRange.second)
    }

    @JvmStatic
    fun constraint(value: Int, range: IntRange): Int =
        value.coerceIn(range)

    @JvmStatic
    fun constraint(value: Long, range: LongRange): Long =
        value.coerceIn(range)

    ////////////////////////////////////////////////////////////

    @JvmStatic
    fun percent(value: Double, start: Double, end: Double): Double {
        if (start == end) return 0.0
        val tempRange = min(start, end) to max(start, end)
        return (constraint(value, tempRange.first, tempRange.second) - tempRange.first) / (tempRange.second - tempRange.first)
    }

    @JvmStatic
    fun percent(value: Float, start: Float, end: Float): Float {
        if (start == end) return 0f
        val tempRange = min(start, end) to max(start, end)
        return (constraint(value, tempRange.first, tempRange.second) - tempRange.first) / (tempRange.second - tempRange.first)
    }

    ////////////////////////////////////////////////////////////

    @JvmStatic
    fun random(start: Int, end: Int): Int =
        random(IntRange(start, end))

    @JvmStatic
    fun random(range: IntRange): Int =
        range.random()
}