package com.mozhimen.underlayk.logk.helpers

import com.mozhimen.underlayk.logk.mos.LogKType

/**
 * @ClassName LogKHelper
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/23 1:14
 * @Version 1.0
 */
object LogKHelper {

    @JvmStatic
    fun getColor(logLevel: Int): Int {
        return when (logLevel) {
            LogKType.V -> -0x444445
            LogKType.D -> -0x1
            LogKType.I -> -0x9578a7
            LogKType.W -> -0x444ad7
            LogKType.E -> -0x9498
            else -> -0x100
        }
    }
}