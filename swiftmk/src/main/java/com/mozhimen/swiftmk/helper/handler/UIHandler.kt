package com.mozhimen.swiftmk.helper.handler

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference

/**
 * @ClassName UIHandler
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/25 15:51
 * @Version 1.0
 */
open class UIHandler<T>(cls: T) : Handler(Looper.getMainLooper()) {
    var ref: WeakReference<T>? = null

    init {
        ref = WeakReference(cls)
    }

    fun getRef(): T? {
        return ref?.get()
    }
}