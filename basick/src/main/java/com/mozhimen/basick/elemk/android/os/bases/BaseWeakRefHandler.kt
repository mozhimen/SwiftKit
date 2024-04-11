package com.mozhimen.basick.elemk.android.os.bases

import android.os.Handler
import android.os.Looper
import com.mozhimen.basick.utilk.commons.IUtilK
import java.lang.ref.WeakReference

/**
 * @ClassName UtilKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
interface IBaseWeakRefHandler<T> {
    fun getRef(): T?
    fun clearRef()
    fun isRefActive(): Boolean
}

open class BaseWeakRefHandler<T> : Handler, IUtilK, IBaseWeakRefHandler<T> {
    protected var objRef: WeakReference<T>? = null

    ////////////////////////////////////////////////////////////////////////////////

    constructor() : this(null)

    constructor(looper: Looper) : this(null, looper)

    constructor(objRef: WeakReference<T>?) : super() {
        this.objRef = objRef
    }

    constructor(objRef: WeakReference<T>?, looper: Looper) : super(looper) {
        this.objRef = objRef
    }

    ////////////////////////////////////////////////////////////////////////////////


    override fun getRef(): T? =
        objRef?.get()


    override fun clearRef() {
        objRef?.clear()
    }

    override fun isRefActive(): Boolean =
        objRef != null && objRef!!.get() != null
}