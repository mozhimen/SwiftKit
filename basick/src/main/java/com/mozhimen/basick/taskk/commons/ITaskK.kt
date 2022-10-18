package com.mozhimen.basick.taskk.commons

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

open class ITaskK(_lifecycleOwner: LifecycleOwner) : DefaultLifecycleObserver {
    init {
        _lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onPause(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
        super.onPause(owner)
    }
}