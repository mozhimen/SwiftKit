package com.mozhimen.basick.impls

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.kotlin.elemk.commons.I_Listener

/**
 * @ClassName LifecycleEventObserverDestroyed
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/29 0:46
 * @Version 1.0
 */
class LifecycleEventObserverDestroy(var lifecycle: Lifecycle?, val onDestroy: I_Listener) :
    LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        val lifecycleState = source.lifecycle.currentState
        if (lifecycleState == Lifecycle.State.DESTROYED) {
            onDestroy.invoke()
            lifecycle?.apply {
                removeObserver(this@LifecycleEventObserverDestroy)
                lifecycle = null
            }
        }
    }
}