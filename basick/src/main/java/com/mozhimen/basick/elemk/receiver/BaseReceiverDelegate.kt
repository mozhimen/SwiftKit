package com.mozhimen.basick.elemk.receiver

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.lifecycle.bases.BaseDelegateLifecycleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @ClassName ReceiverDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:32
 * @Version 1.0
 */
open class BaseReceiverDelegate<T>(
    private val _activity: T,
    private val _receiver: BroadcastReceiver,
    private vararg val _actions: String
) : BaseDelegateLifecycleObserver(_activity) where T : Activity, T : LifecycleOwner {
    init {
        _activity.lifecycleScope.launch(Dispatchers.Main) {
            registerReceiver()
        }
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter()
        if (_actions.isNotEmpty()){
            _actions.forEach {
                intentFilter.addAction(it)
            }
        }
        _activity.registerReceiver(_receiver, intentFilter)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _activity.unregisterReceiver(_receiver)
        super.onDestroy(owner)
    }
}