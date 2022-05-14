package com.mozhimen.basicsk.logk.mos

import android.util.Log
import androidx.annotation.IntDef

/**
 * @ClassName LogKType
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:59
 * @Version 1.0
 */
class LogKType {
    @IntDef(V, D, I, W, E, A)
    @Retention(AnnotationRetention.SOURCE)
    annotation class _TYPE

    companion object {
        const val V = Log.VERBOSE
        const val D = Log.DEBUG
        const val I = Log.INFO
        const val W = Log.WARN
        const val E = Log.ERROR
        const val A = Log.ASSERT

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
}