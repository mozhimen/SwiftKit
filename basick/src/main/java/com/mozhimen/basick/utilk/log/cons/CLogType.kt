package com.mozhimen.basick.utilk.log.cons

import android.util.Log


/**
 * @ClassName CLogType
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/31 13:29
 * @Version 1.0
 */
object CLogType {
    const val V = Log.VERBOSE
    const val D = Log.DEBUG
    const val I = Log.INFO
    const val W = Log.WARN
    const val E = Log.ERROR
    const val A = Log.ASSERT

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