package com.mozhimen.basick.elemk.android.content.bases

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.elemk.commons.IConnectionListener
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread


/**
 * @ClassName BaseReceiverProxy
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:32
 * @Version 1.0
 */
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
open class BaseBroadcastReceiverProxy<C> : BaseWakeBefDestroyLifecycleObserver where C : Context, C : LifecycleOwner {
    protected val _activity: C
    protected val _receiver: BroadcastReceiver
    protected val _actions: Array<out String>

    /////////////////////////////////////////////////////////////////////////////

    constructor(activity: C, receiver: BroadcastReceiver, actions: Array<out String>) : super() {
        _activity = activity
        _receiver = receiver
        _actions = actions
        _activity.runOnMainThread(::registerReceiver)
    }

    /////////////////////////////////////////////////////////////////////////////

    override fun onDestroy(owner: LifecycleOwner) {
        _activity.unregisterReceiver(_receiver)
        super.onDestroy(owner)
    }

    /////////////////////////////////////////////////////////////////////////////

    protected open fun registerReceiver() {
        val intentFilter = IntentFilter()
        if (_actions.isNotEmpty()) for (action in _actions) intentFilter.addAction(action)
        _activity.registerReceiver(_receiver, intentFilter)
    }
}