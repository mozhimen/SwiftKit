package com.mozhimen.basick.elemk.android.os

import android.os.Handler.Callback
import android.os.Looper
import android.os.Message
import com.mozhimen.basick.elemk.android.os.bases.BaseWeakRefHandler
import java.lang.ref.WeakReference


/**
 * @ClassName ExecHandler
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/24 14:33
 * @Version 1.0
 */
class CallbackWeakRefHandler @JvmOverloads constructor(looper: Looper, handlerCallbackRef: WeakReference<Callback>? = null) : BaseWeakRefHandler<Callback>(looper, handlerCallbackRef) {

    override fun handleMessage(msg: Message) {
        getRef()?.handleMessage(msg)
    }
}