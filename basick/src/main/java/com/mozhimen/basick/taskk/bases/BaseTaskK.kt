package com.mozhimen.basick.taskk.bases

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.lifecycle.bases.BaseLifecycleObserver

abstract class BaseTaskK(owner: LifecycleOwner) : BaseLifecycleObserver(owner) {

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        cancel()
        super.onDestroy(owner)
    }

    abstract fun cancel()
}