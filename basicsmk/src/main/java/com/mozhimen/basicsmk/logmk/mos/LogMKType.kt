package com.mozhimen.basicsmk.logmk.mos

import android.util.Log
import androidx.annotation.IntDef

/**
 * @ClassName LogMKType
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2021/12/20 16:59
 * @Version 1.0
 */
class LogMKType {
    @IntDef(V, D, I, W, E, A)
    @Retention(AnnotationRetention.SOURCE)
    annotation class TYPE

    companion object {
        const val V = Log.VERBOSE
        const val D = Log.DEBUG
        const val I = Log.INFO
        const val W = Log.WARN
        const val E = Log.ERROR
        const val A = Log.ASSERT
    }
}