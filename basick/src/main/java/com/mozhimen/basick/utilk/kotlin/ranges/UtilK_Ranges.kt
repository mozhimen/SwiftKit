package com.mozhimen.basick.utilk.kotlin.ranges

import kotlin.math.max
import kotlin.math.min

/**
 * @ClassName UtilKRanges
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/4/11
 * @Version 1.0
 */
fun Double.constraint(min: Double, max: Double): Double =
    UtilK_Ranges.constraint(this, min, max)

fun Long.constraint(min: Long, max: Long): Long =
    UtilK_Ranges.constraint(this, min, max)

fun Float.constraint(min: Float, max: Float): Float =
    UtilK_Ranges.constraint(this, min, max)

fun Int.constraint(min: Int, max: Int): Int =
    UtilK_Ranges.constraint(this, min, max)

/////////////////////////////////////////////////////

fun Double.constraint(pair: Pair<Double, Double>): Double =
    UtilK_Ranges.constraint(this, pair.first, pair.second)

fun Long.constraint(pair: Pair<Long, Long>): Long =
    UtilK_Ranges.constraint(this, pair.first, pair.second)

fun Float.constraint(pair: Pair<Float, Float>): Float =
    UtilK_Ranges.constraint(this, pair.first, pair.second)

fun Int.constraint(pair: Pair<Int, Int>): Int =
    UtilK_Ranges.constraint(this, pair.first, pair.second)

/////////////////////////////////////////////////////

fun Int.constraint(range: IntRange): Int =
    UtilK_Ranges.constraint(this, range)

fun Long.constraint(range: LongRange): Long =
    UtilK_Ranges.constraint(this, range)

/////////////////////////////////////////////////////

fun Float.percent(start: Float, end: Float): Float =
    UtilK_Ranges.percent(this, start, end)

fun Float.percent(pair: Pair<Float, Float>): Float =
    UtilK_Ranges.percent(this, pair.first, pair.second)

fun Double.percent(start: Double, end: Double): Double =
    UtilK_Ranges.percent(this, start, end)

fun Double.percent(pair: Pair<Double, Double>): Double =
    UtilK_Ranges.percent(this, pair.first, pair.second)

/////////////////////////////////////////////////////

object UtilK_Ranges {

    @JvmStatic
    fun constraint(value: Long, min: Long, max: Long): Long {
        val tempPair = min(min, max) to max(min, max)
        return value.coerceIn(tempPair.first, tempPair.second)
    }

    @JvmStatic
    fun constraint(value: Float, min: Float, max: Float): Float {
        val tempPair = min(min, max) to max(min, max)
        return value.coerceIn(tempPair.first, tempPair.second)
    }

    @JvmStatic
    fun constraint(value: Double, min: Double, max: Double): Double {
        val tempPair = min(min, max) to max(min, max)
        return value.coerceIn(tempPair.first, tempPair.second)
    }

    @JvmStatic
    fun constraint(value: Int, min: Int, max: Int): Int {
        val tempPair = min(min, max) to max(min, max)
        return value.coerceIn(tempPair.first, tempPair.second)
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
        val tempPair = min(start, end) to max(start, end)
        return (constraint(value, tempPair.first, tempPair.second) - tempPair.first) / (tempPair.second - tempPair.first)
    }

    @JvmStatic
    fun percent(value: Float, start: Float, end: Float): Float {
        if (start == end) return 0f
        val tempPair = min(start, end) to max(start, end)
        return (constraint(value, tempPair.first, tempPair.second) - tempPair.first) / (tempPair.second - tempPair.first)
    }

    //////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun random(start: Int, end: Int): Int =
        random(IntRange(start, end))

    @JvmStatic
    fun random(range: IntRange): Int =
        range.random()
}