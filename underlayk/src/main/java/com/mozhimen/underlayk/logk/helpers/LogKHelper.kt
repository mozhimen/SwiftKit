package com.mozhimen.underlayk.logk.helpers

import com.mozhimen.basick.utilk.android.content.UtilKRes
import com.mozhimen.underlayk.R
import com.mozhimen.basick.elemk.cons.CLogType

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
            CLogType.V -> R.color.logk_v
            CLogType.D -> R.color.logk_d
            CLogType.I -> R.color.logk_i
            CLogType.W -> R.color.logk_w
            CLogType.E -> R.color.logk_e
            else -> -0x100
        }
    )
}