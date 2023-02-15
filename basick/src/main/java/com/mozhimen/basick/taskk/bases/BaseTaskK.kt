package com.mozhimen.basick.taskk.bases

import androidx.annotation.CallSuper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseTaskK(owner: LifecycleOwner) : DefaultLifecycleObserver {
    protected val TAG = "${this::class.java.simpleName}>>>>>"

    init {
        owner.lifecycleScope.launch(Dispatchers.Main) {
            owner.lifecycle.addObserver(this@BaseTaskK)
        }
    }

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        cancel()
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }

    abstract fun cancel()
}