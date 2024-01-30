package com.mozhimen.basick.utilk.kotlinx.coroutines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName UtilKCoroutineScope
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 9:41
 * @Version 1.0
 */
object UtilKCoroutineScope {
    @JvmStatic
    fun runOnMainScope(lifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            this.block()
        }
    }

    @JvmStatic
    fun runOnBackScope(lifecycleOwner: LifecycleOwner, block: suspend CoroutineScope.() -> Unit) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            this.block()
        }
    }
}