package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.UtilKNumber

/**
 * @ClassName ExtsKNumber
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/16 19:39
 * @Version 1.0
 */
fun Float.normalize(min: Float, max: Float): Float =
    UtilKNumber.normalize(this, min, max)

fun Float.normalize(min: Int, max: Int): Float =
    UtilKNumber.normalize(this, min, max)

fun Float.normalize(range: IntRange): Float =
    UtilKNumber.normalize(this, range)

fun Float.normalize(range: Pair<Float, Float>): Float =
    UtilKNumber.normalize(this, range)

fun Int.normalize(range: IntRange): Int =
    UtilKNumber.normalize(this, range)

fun Int.normalize(min: Int, max: Int): Int =
    UtilKNumber.normalize(this, min, max)

fun Float.percent(range: Pair<Float, Float>): Float =
    UtilKNumber.percent(this, range)

fun Float.percent(rangeStart: Float, rangeEnd: Float): Float =
    UtilKNumber.percent(this, rangeStart, rangeEnd)
