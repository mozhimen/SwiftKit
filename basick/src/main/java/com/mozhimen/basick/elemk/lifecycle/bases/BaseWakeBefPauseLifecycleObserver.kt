package com.mozhimen.basick.elemk.lifecycle.bases

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.lifecycle.commons.IDefaultLifecycleObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @ClassName BaseLifecycleObserverForView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/24 17:47
 * @Version 1.0
 */
open class BaseWakeBefPauseLifecycleObserver : IDefaultLifecycleObserver {
    protected open val TAG = "${this.javaClass.simpleName}>>>>>"

    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.lifecycleScope.launch(Dispatchers.Main) {
            owner.lifecycle.removeObserver(this@BaseWakeBefPauseLifecycleObserver)
            owner.lifecycle.addObserver(this@BaseWakeBefPauseLifecycleObserver)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this@BaseWakeBefPauseLifecycleObserver)
    }
}