package com.mozhimen.basick.utils

import androidx.lifecycle.Lifecycle
import com.mozhimen.basick.impls.LifecycleEventObserverOnDestroy
import com.mozhimen.kotlin.elemk.commons.I_Listener

/**
 * @ClassName UtilKLifecycle
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/29 0:50
 * @Version 1.0
 */

fun Lifecycle.addObserverOnDestroy(onDestroy: I_Listener) {
    UtilKLifecycle.addObserverOnDestroy(this, onDestroy)
}

//////////////////////////////////////////////////////////

object UtilKLifecycle {
    @JvmStatic
    fun addObserverOnDestroy(lifecycle: Lifecycle, onDestroy: I_Listener) {
        lifecycle.addObserver(LifecycleEventObserverOnDestroy(lifecycle, onDestroy))
    }
}