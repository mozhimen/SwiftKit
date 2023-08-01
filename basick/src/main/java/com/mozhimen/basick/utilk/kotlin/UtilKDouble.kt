package com.mozhimen.basick.utilk.kotlin

import java.text.DecimalFormat

/**
 * @ClassName UtilKDouble
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/1 16:34
 * @Version 1.0
 */
fun Double.decimal2str(pattern: String = "#.0"): String =
    UtilKDouble.decimal2str(this, pattern)

object UtilKDouble {
    @JvmStatic
    fun decimal2str(double: Double, pattern: String = "#.0"): String =
        DecimalFormat(pattern).format(double)
}