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
fun LifecycleRegistry.handleLifecycleEventOnCreate() {
    UtilKLifecycleRegistry.handleLifecycleEventOnCreate(this)
}

fun LifecycleRegistry.handleLifecycleEventOnStart() {
    UtilKLifecycleRegistry.handleLifecycleEventOnStart(this)
}

fun LifecycleRegistry.handleLifecycleEventOnResume() {
    UtilKLifecycleRegistry.handleLifecycleEventOnResume(this)
}

fun LifecycleRegistry.handleLifecycleEventOnPause() {
    UtilKLifecycleRegistry.handleLifecycleEventOnPause(this)
}

fun LifecycleRegistry.handleLifecycleEventOnStop() {
    UtilKLifecycleRegistry.handleLifecycleEventOnStop(this)
}

fun LifecycleRegistry.handleLifecycleEventOnDestroy() {
    UtilKLifecycleRegistry.handleLifecycleEventOnDestroy(this)
}

object UtilKLifecycleRegistry {
    @JvmStatic
    fun handleLifecycleEventOnCreate(lifecycleRegistry: LifecycleRegistry) {
        if (lifecycleRegistry.currentState != Lifecycle.State.CREATED) {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }
    }

    @JvmStatic
    fun handleLifecycleEventOnStart(lifecycleRegistry: LifecycleRegistry) {
        if (lifecycleRegistry.currentState != Lifecycle.State.STARTED) {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }
    }

    @JvmStatic
    fun handleLifecycleEventOnResume(lifecycleRegistry: LifecycleRegistry) {
        if (lifecycleRegistry.currentState != Lifecycle.State.RESUMED) {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        }
    }

    @JvmStatic
    fun handleLifecycleEventOnPause(lifecycleRegistry: LifecycleRegistry) {
        if (lifecycleRegistry.currentState != Lifecycle.State.STARTED) {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        }
    }

    @JvmStatic
    fun handleLifecycleEventOnStop(lifecycleRegistry: LifecycleRegistry) {
        if (lifecycleRegistry.currentState != Lifecycle.State.CREATED) {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        }
    }

    @JvmStatic
    fun handleLifecycleEventOnDestroy(lifecycleRegistry: LifecycleRegistry) {
        if (lifecycleRegistry.currentState != Lifecycle.State.INITIALIZED) {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        }
    }
}