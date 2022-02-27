package com.mozhimen.basicsmk.utilmk

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference

/**
 * @ClassName UtilMKUIHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 17:56
 * @Version 1.0
 */
open class UtilMKUIHandler<T>(cls: T) : Handler(Looper.getMainLooper()) {
    var ref: WeakReference<T>? = null

    init {
        ref = WeakReference(cls)
    }

    fun getRef(): T? {
        return ref?.get()
    }
}