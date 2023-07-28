package com.mozhimen.underlayk.logk.helpers

import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.underlayk.R
import com.mozhimen.basick.elemk.android.util.cons.CLogPriority

/**
 * @ClassName LogKHelper
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/23 1:14
 * @Version 1.0
 */
object LogKHelper {

    @JvmStatic
    fun getLevelColor(logLevel: Int): Int = UtilKRes.getColor(
        when (logLevel) {
            CLogPriority.V -> R.color.logk_v
            CLogPriority.D -> R.color.logk_d
            CLogPriority.I -> R.color.logk_i
            CLogPriority.W -> R.color.logk_w
            CLogPriority.E -> R.color.logk_e
            else -> -0x100
        }
    )

    @JvmStatic
    fun getTypeName(level: Int): String = when (level) {
        2 -> "V"
        3 -> "D"
        4 -> "I"
        5 -> "W"
        6 -> "E"
        7 -> "A"
        else -> "UNKNOWN"
    }
}