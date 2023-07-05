package com.mozhimen.basick.elemk.handler.bases

import android.os.Looper

/**
 * @ClassName UtilKHandler
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 17:56
 * @Version 1.0
 */
open class BaseWeakClazzMainHandler<C>(clazz: C) : BaseWeakClazzHandler<C>(clazz, Looper.getMainLooper())

//open class BaseWeakClazzMainHandler<T>(clazz: T) : Handler(Looper.getMainLooper()) {
//    protected var _weakReference: WeakReference<T>? = null
//
//    init {
//        _weakReference = WeakReference(clazz)
//    }
//
//    fun getRef(): T? {
//        return _weakReference?.get()
//    }
//
//    fun clear() {
//        _weakReference?.clear()
//    }
//}