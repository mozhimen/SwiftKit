package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.java.lang.UtilKThread
import com.mozhimen.basick.utilk.kotlinx.coroutines.UtilKCoroutineScope
import kotlinx.coroutines.CoroutineScope

/**
 * @ClassName UtilKLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/12 14:47
 * @Version 1.0
 */
fun LifecycleOwner.runOnMainScope(block: suspend CoroutineScope.() -> Unit) {
    UtilKLifecycleOwner.runOnMainScope(this, block)
}

fun LifecycleOwner.runOnBackScope(block: suspend CoroutineScope.() -> Unit) {
    UtilKLifecycleOwner.runOnBackScope(this, block)
}

////////////////////////////////////////////////////////////////////

fun LifecycleOwner.runOnMainThread(block: I_Listener) {
    UtilKLifecycleOwner.runOnMainThread(this, block)
}

fun LifecycleOwner.runOnBackThread(block: I_Listener) {
    UtilKLifecycleOwner.runOnBackThread(this, block)
}

////////////////////////////////////////////////////////////////////

object UtilKLifecycleOwner {
    @JvmStatic
    fun runOnMainScope(lifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        UtilKCoroutineScope.runOnMainScope(lifecycleOwner, block)
    }

    @JvmStatic
    fun runOnBackScope(lifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        UtilKCoroutineScope.runOnBackScope(lifecycleOwner, block)
    }

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun runOnMainThread(lifecycleOwner: LifecycleOwner, block: I_Listener) {
        UtilKThread.runOnMainThread(lifecycleOwner, block)
    }

    @JvmStatic
    fun runOnBackThread(lifecycleOwner: LifecycleOwner, block: I_Listener) {
        UtilKThread.runOnBackThread(lifecycleOwner, block)
    }
}