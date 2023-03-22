package com.mozhimen.basick.utilk.java.datatype

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

    /**
     * 保留四位
     * @param value Double
     * @return Double
     */
    @JvmStatic
    fun keepFourDigits(value: Double): Double {
        val format = DecimalFormat("#.####")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toDouble()
    }

    /**
     * 保留四位
     * @param value Double
     * @return Double
     */
    @JvmStatic
    fun keepFourDigits(value: Float): Float {
        val format = DecimalFormat("#.####")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toFloat()
    }

    /**
     * 保留三位
     * @param value Double
     * @return Double
     */
    @JvmStatic
    fun keepThreeDigits(value: Double): Double {
        val format = DecimalFormat("#.###")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toDouble()
    }

    /**
     * 保留三位
     * @param value Double
     * @return Double
     */
    @JvmStatic
    fun keepThreeDigits(value: Float): Float {
        val format = DecimalFormat("#.###")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toFloat()
    }

    /**
     * 保留两位
     * @param value Double
     * @return Double
     */
    @JvmStatic
    fun keepTwoDigits(value: Double): Double {
        val format = DecimalFormat("#.##")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toDouble()
    }

    @JvmStatic
    fun keepTwoDigits(value: Float): Float {
        val format = DecimalFormat("#.##")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toFloat()
    }

    @JvmStatic
    fun keepOneDigits(value: Double): Double {
        val format = DecimalFormat("#.#")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toDouble()
    }

    @JvmStatic
    fun keepOneDigits(value: Float): Float {
        val format = DecimalFormat("#.#")
        //舍弃规则，RoundingMode.FLOOR表示直接舍弃。
        format.roundingMode = RoundingMode.FLOOR
        return format.format(value).toFloat()
    }

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