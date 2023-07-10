package com.mozhimen.basick.elemk.android.os.bases

import android.os.Handler.Callback
import android.os.Looper
import android.os.Message
import android.util.Log
import java.lang.ref.WeakReference


/**
 * @ClassName ExecHandler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/24 14:33
 * @Version 1.0
 */
class BaseWeakCallbackHandler @JvmOverloads constructor(looper: Looper, callbackRef: WeakReference<Callback?>? = null) : BaseWeakClazzHandler<Callback?>(callbackRef?.get(), looper) {

    init {
        _weakReference = callbackRef
    }

    override fun handleMessage(msg: Message) {
        if (_weakReference == null) return
        _weakReference?.get()?.handleMessage(msg) ?: kotlin.run {
            Log.d(TAG, "handleMessage: ")
        }
    }
}

//class BaseWeakCallbackHandler : Handler {
//    private val _weakCallback: WeakReference<Callback>?
//
//    @JvmOverloads
//    constructor(callback: WeakReference<Callback>? = null) {
//        _weakCallback = callback
//    }
//
//    @JvmOverloads
//    constructor(looper: Looper, callback: WeakReference<Callback>? = null) : super(looper) {
//        _weakCallback = callback
//    }
//
//    override fun handleMessage(msg: Message) {
//        if (_weakCallback == null) return
//        val callback = _weakCallback.get() ?: return
//        callback.handleMessage(msg)
//    }
//}