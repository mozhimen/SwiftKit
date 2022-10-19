package com.mozhimen.basick.taskk.commons

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

abstract class ITaskK(_lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {
    init {
        _lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        cancel()
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }

    abstract fun cancel()
}