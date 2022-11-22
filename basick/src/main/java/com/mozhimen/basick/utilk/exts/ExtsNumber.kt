package com.mozhimen.basick.utilk.exts

import com.mozhimen.basick.utilk.UtilKNumber

/**
 * @ClassName ExtsKNumber
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/16 19:39
 * @Version 1.0
 */
fun Float.normalize(range: Pair<Float, Float>): Float =
    UtilKNumber.normalize(this, range)

fun Float.normalize(min: Float, max: Float): Float =
    UtilKNumber.normalize(this, min, max)

fun Int.normalize(range: IntRange) =
    UtilKNumber.normalize(this, range)

fun Float.percent(range: Pair<Float, Float>): Float =
    UtilKNumber.percent(this, range)

fun Float.percent(rangeStart: Int, rangeEnd: Int): Float =
    UtilKNumber.percent(this, rangeStart, rangeEnd)
