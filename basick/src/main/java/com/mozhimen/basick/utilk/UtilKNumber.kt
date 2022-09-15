package com.mozhimen.basick.utilk

import kotlin.collections.ArrayList

/**
 * @ClassName UtilKNumber
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/31 17:33
 * @Version 1.0
 */
object UtilKNumber {
    @JvmStatic
    fun normalize(value: Int, range: IntRange): Int {
        return when {
            value < range.first -> range.first
            value > range.last -> range.last
            else -> value
        }
    }

    fun random(start: Int, end: Int): Int = random(IntRange(start, end))

    fun random(range: IntRange): Int = range.random()

    fun max(nums: Array<Int>): Int = nums.maxOf { it }

    fun max(nums: Array<Short>): Short = nums.maxOf { it }

    fun max(nums: Array<Long>): Long = nums.maxOf { it }

    fun max(nums: Array<Float>): Float = nums.maxOf { it }

    fun max(nums: Array<Double>): Double = nums.maxOf { it }

    fun max(nums: Array<Byte>): Byte = nums.maxOf { it }

    fun max(nums: ArrayList<Int>): Int = nums.maxOf { it }

    fun max(nums: ArrayList<Short>): Short = nums.maxOf { it }

    fun max(nums: ArrayList<Long>): Long = nums.maxOf { it }

    fun max(nums: ArrayList<Float>): Float = nums.maxOf { it }

    fun max(nums: ArrayList<Double>): Double = nums.maxOf { it }

    fun max(nums: ArrayList<Byte>): Byte = nums.maxOf { it }

    fun max(nums: List<Int>): Int = nums.maxOf { it }

    fun max(nums: List<Short>): Short = nums.maxOf { it }

    fun max(nums: List<Long>): Long = nums.maxOf { it }

    fun max(nums: List<Float>): Float = nums.maxOf { it }

    fun max(nums: List<Double>): Double = nums.maxOf { it }

    fun max(nums: List<Byte>): Byte = nums.maxOf { it }

    fun min(nums: Array<Int>): Int = nums.minOf { it }

    fun min(nums: Array<Short>): Short = nums.minOf { it }

    fun min(nums: Array<Long>): Long = nums.minOf { it }

    fun min(nums: Array<Float>): Float = nums.minOf { it }

    fun min(nums: Array<Double>): Double = nums.minOf { it }

    fun min(nums: Array<Byte>): Byte = nums.minOf { it }

    fun min(nums: ArrayList<Int>): Int = nums.minOf { it }

    fun min(nums: ArrayList<Short>): Short = nums.minOf { it }

    fun min(nums: ArrayList<Long>): Long = nums.minOf { it }

    fun min(nums: ArrayList<Float>): Float = nums.minOf { it }

    fun min(nums: ArrayList<Double>): Double = nums.minOf { it }

    fun min(nums: ArrayList<Byte>): Byte = nums.minOf { it }

    fun min(nums: List<Int>): Int = nums.minOf { it }

    fun min(nums: List<Short>): Short = nums.minOf { it }

    fun min(nums: List<Long>): Long = nums.minOf { it }

    fun min(nums: List<Float>): Float = nums.minOf { it }

    fun min(nums: List<Double>): Double = nums.minOf { it }

    fun min(nums: List<Byte>): Byte = nums.minOf { it }
}