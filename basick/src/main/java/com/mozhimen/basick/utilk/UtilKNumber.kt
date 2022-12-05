package com.mozhimen.basick.utilk

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.*

/**
 * @ClassName UtilKNumber
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/31 17:33
 * @Version 1.0
 */
object UtilKNumber {
    @JvmStatic
    fun keepTwoDigits(value: Float): Float {
        val format = DecimalFormat("#.##")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toFloat()
    }

    @JvmStatic
    fun keepOneDigits(value: Float): Float {
        val format = DecimalFormat("#.#")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toFloat()
    }

    @JvmStatic
    fun angleCos(adjacent: Float, hypotenuse: Float): Float =
        Math.toDegrees(acos(adjacent / hypotenuse).toDouble()).toFloat()

    @JvmStatic
    fun angleSin(opposite: Float, hypotenuse: Float): Float =
        Math.toDegrees(asin(opposite / hypotenuse).toDouble()).toFloat()

    @JvmStatic
    fun center(ax: Float, ay: Float, bx: Float, by: Float): Pair<Float, Float> =
        Pair((ax + bx) / 2f, (ay + by) / 2f)

    @JvmStatic
    fun distance(ax: Float, ay: Float, bx: Float, by: Float): Float {
        val distance1 = abs(bx - ax)
        val distance2 = abs(by - ay)
        return sqrt(distance1 * distance1 + distance2 * distance2)
    }

    @JvmStatic
    fun normalize(value: Float, min: Float, max: Float): Float {
        val tempRange = min(min, max) to max(min, max)
        return when {
            value < tempRange.first -> tempRange.first
            value > tempRange.second -> tempRange.second
            else -> value
        }
    }

    @JvmStatic
    fun normalize(value: Float, min: Int, max: Int): Float =
        normalize(value, min.toFloat(), max.toFloat())

    @JvmStatic
    fun normalize(value: Float, range: IntRange): Float =
        normalize(value, range.first.toFloat(), range.last.toFloat())

    @JvmStatic
    fun normalize(value: Float, range: Pair<Float, Float>): Float =
        normalize(value, range.first, range.second)

    @JvmStatic
    fun normalize(value: Int, range: IntRange): Int {
        val tempRange = min(range.first, range.last) to max(range.first, range.last)
        return when {
            value < tempRange.first -> tempRange.first
            value > tempRange.second -> tempRange.second
            else -> value
        }
    }

    @JvmStatic
    fun percent(value: Float, range: Pair<Float, Float>): Float {
        if (range.first == range.second) return 0f
        val tempRange = min(range.first, range.second) to max(range.first, range.second)
        return (normalize(value, tempRange) - tempRange.first) / (tempRange.second - tempRange.first)
    }

    @JvmStatic
    fun percent(value: Float, range: IntRange): Float =
        percent(value, range.first.toFloat() to range.last.toFloat())

    @JvmStatic
    fun percent(value: Long, range: LongRange): Float =
        percent(value.toFloat(), range.first.toFloat() to range.last.toFloat())

    @JvmStatic
    fun percent(value: Float, rangeStart: Float, rangeEnd: Float): Float =
        percent(value, rangeStart to rangeEnd)

    @JvmStatic
    fun percent(value: Long, rangeStart: Long, rangeEnd: Long): Float =
        percent(value, rangeStart..rangeEnd)

    @JvmStatic
    fun random(start: Int, end: Int): Int =
        random(IntRange(start, end))

    @JvmStatic
    fun random(range: IntRange): Int =
        range.random()

    @JvmStatic
    fun max(nums: Array<Int>): Int =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: Array<Short>): Short =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: Array<Long>): Long =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: Array<Float>): Float =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: Array<Double>): Double =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: Array<Byte>): Byte =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: ArrayList<Int>): Int =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: ArrayList<Short>): Short =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: ArrayList<Long>): Long =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: ArrayList<Float>): Float =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: ArrayList<Double>): Double =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: ArrayList<Byte>): Byte =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: List<Int>): Int =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: List<Short>): Short =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: List<Long>): Long =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: List<Float>): Float =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: List<Double>): Double =
        nums.maxOf { it }

    @JvmStatic
    fun max(nums: List<Byte>): Byte =
        nums.maxOf { it }

    @JvmStatic
    fun min(nums: Array<Int>): Int =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: Array<Short>): Short =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: Array<Long>): Long =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: Array<Float>): Float =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: Array<Double>): Double =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: Array<Byte>): Byte =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: ArrayList<Int>): Int =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: ArrayList<Short>): Short =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: ArrayList<Long>): Long =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: ArrayList<Float>): Float =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: ArrayList<Double>): Double =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: ArrayList<Byte>): Byte =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: List<Int>): Int =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: List<Short>): Short =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: List<Long>): Long =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: List<Float>): Float =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: List<Double>): Double =
        nums.minOf { it }

    @JvmStatic
    fun min(nums: List<Byte>): Byte =
        nums.minOf { it }
}