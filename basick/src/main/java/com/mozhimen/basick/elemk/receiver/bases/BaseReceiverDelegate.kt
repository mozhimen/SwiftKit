package com.mozhimen.basick.elemk.receiver.bases

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.lintk.optin.annors.ADescription
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit
import com.mozhimen.basick.elemk.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @ClassName BaseReceiverDelegate
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/10 14:32
 * @Version 1.0
 */
@AOptLazyInit
open class BaseReceiverDelegate<A>(
    private val _activity: A,
    private val _receiver: BroadcastReceiver,
    private vararg val _actions: String
) : BaseWakeBefDestroyLifecycleObserver() where A : Activity, A : LifecycleOwner {
    
    init {
        _activity.lifecycleScope.launch(Dispatchers.Main) {
            registerReceiver()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        _activity.unregisterReceiver(_receiver)
        super.onDestroy(owner)
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
}