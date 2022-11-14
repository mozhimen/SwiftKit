package com.mozhimen.basick.taskk.coroutine.commons

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ITaskK(owner: LifecycleOwner) : DefaultLifecycleObserver {
    init {
        owner.lifecycleScope.launch(Dispatchers.Main){
            owner.lifecycle.addObserver(this@ITaskK)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        cancel()
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }

    abstract fun cancel()
}