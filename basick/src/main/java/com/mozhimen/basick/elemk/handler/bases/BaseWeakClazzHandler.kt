package com.mozhimen.basick.elemk.handler.bases

import android.os.Handler
import android.os.Looper
import com.mozhimen.basick.utilk.bases.IUtilK
import java.lang.ref.WeakReference

/**
 * @ClassName UtilKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 17:56
 * @Version 1.0
 */
open class BaseWeakClazzHandler<C>(clazz: C, looper: Looper) : Handler(looper), IUtilK {
    protected var _weakReference: WeakReference<C>? = null

    init {
        _weakReference = WeakReference(clazz)
    }

    fun getRef(): C? {
        return _weakReference?.get()
    }

    fun clearRef() {
        _weakReference?.clear()
    }
}