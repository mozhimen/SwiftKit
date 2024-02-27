package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry

/**
 * @ClassName UtilKLifecycleRegistry
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/27
 * @Version 1.0
 */
fun LifecycleRegistry.handleLifecycleEventDestroyed() {
    UtilKLifecycleRegistry.handleLifecycleEventDestroyed(this)
}

object UtilKLifecycleRegistry {
    @JvmStatic
    fun handleLifecycleEventDestroyed(lifecycleRegistry: LifecycleRegistry) {
        if (lifecycleRegistry.currentState != Lifecycle.State.INITIALIZED) {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }
    }
}