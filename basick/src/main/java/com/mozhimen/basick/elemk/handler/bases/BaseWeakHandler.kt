package com.mozhimen.basick.elemk.handler.bases

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference

/**
 * @ClassName UtilKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 17:56
 * @Version 1.0
 */
open class BaseWeakHandler<T>(cls: T) : Handler(Looper.getMainLooper()) {
    private var _ref: WeakReference<T>? = null

    init {
        _ref = WeakReference(cls)
    }

    fun getRef(): T? {
        return _ref?.get()
    }

    fun clear() {
        _ref?.clear()
    }
}