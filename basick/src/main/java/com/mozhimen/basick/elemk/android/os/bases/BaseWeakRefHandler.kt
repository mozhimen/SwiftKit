package com.mozhimen.basick.elemk.android.os.bases

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
interface IBaseWeakRefHandler<T> {
    fun getRef(): T?
    fun clearRef()
    fun isRefActive(): Boolean
}

open class BaseWeakRefHandler<T>(looper: Looper, protected var objRef: WeakReference<T>? = null) : Handler(looper), IUtilK, IBaseWeakRefHandler<T> {
    override fun getRef(): T? =
        objRef?.get()


    override fun clearRef() {
        objRef?.clear()
    }

    override fun isRefActive(): Boolean =
        objRef != null && objRef!!.get() != null
}