package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.commons.I_Listener
import com.mozhimen.basick.utilk.java.lang.UtilKThread
import kotlinx.coroutines.CoroutineScope

/**
 * @ClassName UtilKLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/12 14:47
 * @Version 1.0
 */
fun LifecycleOwner.runOnBackThread(block: I_Listener) {
    UtilKLifecycle.runOnBackThread(this, block)
}

fun LifecycleOwner.runOnMainThread(block: I_Listener) {
    UtilKLifecycle.runOnMainThread(this, block)
}

fun LifecycleOwner.runOnMainScope(block: suspend CoroutineScope.() -> Unit) {
    UtilKLifecycle.runOnMainScope(this, block)
}

object UtilKLifecycle {
    /**
     * 在子线程上运行
     * @param lifecycleOwner LifecycleOwner
     * @param block Function0<Unit>
     */
    @JvmStatic
    fun runOnBackThread(lifecycleOwner: LifecycleOwner, block: I_Listener) {
        UtilKThread.runOnBackThread(lifecycleOwner, block)
    }

    @JvmStatic
    fun runOnMainThread(lifecycleOwner: LifecycleOwner, block: I_Listener) {
        UtilKThread.runOnMainThread(lifecycleOwner, block)
    }

    @JvmStatic
    fun runOnMainScope(lifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        UtilKThread.runOnMainScope(lifecycleOwner, block)
    }
}