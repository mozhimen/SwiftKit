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
 * @Version 1.0
 */
class WeakCallbackRefHandler : BaseWeakRefHandler<Callback> {
    constructor() : super()

    constructor(looper: Looper) : super(looper)

    constructor(callbackRef: WeakReference<Callback>?) : super(callbackRef)

    constructor(callbackRef: WeakReference<Callback>?, looper: Looper) : super(callbackRef, looper)

    ////////////////////////////////////////////////////////////////////////////////

    override fun handleMessage(msg: Message) {
        getRef()?.handleMessage(msg)
    }
}