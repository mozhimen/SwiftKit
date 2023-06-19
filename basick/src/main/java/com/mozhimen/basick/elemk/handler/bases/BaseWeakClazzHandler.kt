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
open class BaseWeakClazzHandler<T>(clazz: T, looper: Looper) : Handler(looper) {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"
    protected var _weakReference: WeakReference<T>? = null

    init {
        _weakReference = WeakReference(clazz)
    }

    fun getRef(): T? {
        return _weakReference?.get()
    }

    fun clearRef() {
        _weakReference?.clear()
    }
}