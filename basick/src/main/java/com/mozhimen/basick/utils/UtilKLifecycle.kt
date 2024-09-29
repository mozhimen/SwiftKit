package com.mozhimen.basick.utils

import androidx.lifecycle.Lifecycle
import com.mozhimen.basick.impls.LifecycleEventObserverDestroy
import com.mozhimen.kotlin.elemk.commons.I_Listener

/**
 * @ClassName UtilKLifecycle
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/9/29 0:50
 * @Version 1.0
 */

fun Lifecycle.addObserverDestroy(onDestroy: I_Listener) {
    UtilKLifecycle.addObserverDestroy(this, onDestroy)
}

//////////////////////////////////////////////////////////

object UtilKLifecycle {
    @JvmStatic
    fun addObserverDestroy(lifecycle: Lifecycle, onDestroy: I_Listener) {
        lifecycle.addObserver(LifecycleEventObserverDestroy(lifecycle, onDestroy))
    }
}