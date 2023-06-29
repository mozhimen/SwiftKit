package com.mozhimen.basick.utilk.kotlin

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.max
import kotlin.math.min

/**
 * @ClassName UtilKNumber
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/31 17:33
 * @Version 1.0
 */
fun Double.keepDigits(digit: Int): Double =
    UtilKNumber.keepDigits(this, digit)

fun Float.keepDigits(digit: Int): Float =
    UtilKNumber.keepDigits(this, digit)

fun Double.keepDigitsStr(digit: Int): String =
    UtilKNumber.keepDigitsStr(this, digit)

fun Float.keepDigitsStr(digit: Int): String =
    UtilKNumber.keepDigitsStr(this, digit)

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

object UtilKNumber {
    private val TAG = "UtilKNumber>>>>>"

    /**
     * ASCII转整型
     * '5' ascci 是 53。 输入 int 53，输出 int 5
     * @param ascii Int
     * @return Int
     */
    @JvmStatic
    fun ascii2Int(ascii: Int): Int {
        return Character.getNumericValue(ascii)
    }

    ////////////////////////////////////////////////////////////

    @JvmStatic
    fun keepDigits(value: Double, @androidx.annotation.IntRange(from = 1) digit: Int): Double =
        keepDigitsStr(value, digit).toDouble()

    @JvmStatic
    fun keepDigits(value: Float, @androidx.annotation.IntRange(from = 1) digit: Int): Float =
        keepDigitsStr(value, digit).toFloat()

    @JvmStatic
    fun keepDigitsStr(value: Any, @androidx.annotation.IntRange(from = 1) digit: Int): String {
        val stringBuilder = StringBuilder("#.")
        repeat(digit) {
            stringBuilder.append("#")
        }
        val format = DecimalFormat(stringBuilder.toString())
        format.roundingMode = RoundingMode.HALF_UP
        return format.format(value)
    }

    ////////////////////////////////////////////////////////////

    @JvmStatic
    fun normalize(value: Long, min: Long, max: Long): Long {
        val tempRange = min(min, max) to max(min, max)
        return when {
            value < tempRange.first -> tempRange.first
            value > tempRange.second -> tempRange.second
            else -> value
        }
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
    fun normalize(value: Double, min: Double, max: Double): Double {
        val tempRange = min(min, max) to max(min, max)
        return when {
            value < tempRange.first -> min
            value > tempRange.second -> max
            else -> value
        }
    }

    @JvmStatic
    fun normalize(value: Int, min: Int, max: Int): Int {
        val tempRange = min(min, max) to max(min, max)
        return when {
            value < tempRange.first -> tempRange.first
            value > tempRange.second -> tempRange.second
            else -> value
        }
    }

    ////////////////////////////////////////////////////////////

    @JvmStatic
    fun percent(value: Double, start: Double, end: Double): Double {
        if (start == end) return 0.0
        val tempRange = min(start, end) to max(start, end)
        return (normalize(value, tempRange.first, tempRange.second) - tempRange.first) / (tempRange.second - tempRange.first)
    }

    @JvmStatic
    fun percent(value: Float, start: Float, end: Float): Float {
        if (start == end) return 0f
        val tempRange = min(start, end) to max(start, end)
        return (normalize(value, tempRange.first, tempRange.second) - tempRange.first) / (tempRange.second - tempRange.first)
    }

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