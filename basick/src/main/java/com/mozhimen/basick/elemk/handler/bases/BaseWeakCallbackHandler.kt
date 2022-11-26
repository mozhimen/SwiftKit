package com.mozhimen.basick.elemk.handler.bases

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.lang.ref.WeakReference


/**
 * @ClassName ExecHandler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/24 14:33
 * @Version 1.0
 */
class BaseWeakCallbackHandler : Handler {
    private val _weakCallback: WeakReference<Callback>?

    @JvmOverloads
    constructor(callback: WeakReference<Callback>? = null) {
        _weakCallback = callback
    }

    @JvmOverloads
    constructor(looper: Looper, callback: WeakReference<Callback>? = null) : super(looper) {
        _weakCallback = callback
    }

    override fun handleMessage(msg: Message) {
        if (_weakCallback == null) return
        val callback = _weakCallback.get() ?: return
        callback.handleMessage(msg)
    }
}