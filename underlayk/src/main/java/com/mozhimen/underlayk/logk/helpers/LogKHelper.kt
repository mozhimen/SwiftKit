package com.mozhimen.underlayk.logk.helpers

import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.underlayk.R
import com.mozhimen.underlayk.logk.mos.CLogKType

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
            CLogKType.V -> R.color.logk_v
            CLogKType.D -> R.color.logk_d
            CLogKType.I -> R.color.logk_i
            CLogKType.W -> R.color.logk_w
            CLogKType.E -> R.color.logk_e
            else -> -0x100
        }
    )
}