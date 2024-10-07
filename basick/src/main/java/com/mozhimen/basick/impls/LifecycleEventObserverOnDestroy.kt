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
class LifecycleEventObserverOnDestroy(private var _lifecycle: Lifecycle?,private val _onDestroy: I_Listener) :
    LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        val lifecycleState = source.lifecycle.currentState
        if (lifecycleState == Lifecycle.State.DESTROYED) {
            _onDestroy.invoke()
            _lifecycle?.apply {
                removeObserver(this@LifecycleEventObserverOnDestroy)
                _lifecycle = null
            }
        }
    }
}