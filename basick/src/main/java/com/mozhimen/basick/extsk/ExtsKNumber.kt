package com.mozhimen.basick.extsk

import com.mozhimen.basick.utilk.UtilKNumber

/**
 * @ClassName ExtsKNumber
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/16 19:39
 * @Version 1.0
 */
fun Float.normalize(range: IntRange) =
    UtilKNumber.normalize(this.toInt(), range)

fun Int.normalize(range: IntRange) =
    UtilKNumber.normalize(this, range)