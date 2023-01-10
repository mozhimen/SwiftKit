package com.mozhimen.basick.elemk.receiver.helpers

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @ClassName LifecycleReceiverDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:32
 * @Version 1.0
 */
class LifecycleReceiverDelegate<T>(
    private val _activity: T,
    private val _actions: Array<String>,
    private val _receiver: BroadcastReceiver
) : DefaultLifecycleObserver where T : Activity, T : LifecycleOwner {
    init {
        _activity.lifecycleScope.launch(Dispatchers.Main) {
            _activity.lifecycle.addObserver(this@LifecycleReceiverDelegate)
            registerReceiver()
        }
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
        _actions.forEach {
            intentFilter.addAction(it)
        }
        _activity.registerReceiver(_receiver, intentFilter)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _activity.unregisterReceiver(_receiver)
        super.onDestroy(owner)
    }
}