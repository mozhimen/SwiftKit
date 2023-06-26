package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.utilk.java.lang.UtilKThread

/**
 * @ClassName UtilKLifecycle
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/12 14:47
 * @Version 1.0
 */
object UtilKLifecycle {
    /**
     * 在子线程上运行
     * @param lifecycleOwner LifecycleOwner
     * @param block Function0<Unit>
     */
    @JvmStatic
    fun runOnBackThread(lifecycleOwner: LifecycleOwner, block: () -> Unit) {
        UtilKThread.runOnBackThread(lifecycleOwner, block)
    }
}