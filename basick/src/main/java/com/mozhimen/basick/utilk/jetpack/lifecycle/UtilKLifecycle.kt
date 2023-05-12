package com.mozhimen.basick.utilk.jetpack.lifecycle

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.utilk.os.thread.UtilKThread

/**
 * @ClassName UtilKLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/12 14:47
 * @Version 1.0
 */
object UtilKLifecycle {
    @JvmStatic
    fun runOnBackThread(lifecycleOwner: LifecycleOwner, block: () -> Unit) {
        UtilKThread.runOnBackThread(lifecycleOwner, block)
    }
}