package com.mozhimen.basick.utilk.androidx.lifecycle

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope

/**
 * @ClassName UtilKProcessLifecycleOwner
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 14:56
 * @Version 1.0
 */
object UtilKProcessLifecycleOwner {
    @JvmStatic
    fun get(): LifecycleOwner =
        ProcessLifecycleOwner.get()

    @JvmStatic
    fun getLifecycleScope(): LifecycleCoroutineScope =
        get().lifecycleScope
}