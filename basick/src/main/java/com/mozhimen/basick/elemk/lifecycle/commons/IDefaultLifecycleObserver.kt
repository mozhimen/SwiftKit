package com.mozhimen.basick.elemk.lifecycle.commons

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @ClassName IDefaultLifecycleObserver
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/19 12:08
 * @Version 1.0
 */
interface IDefaultLifecycleObserver : DefaultLifecycleObserver {
    fun bindLifecycle(owner: LifecycleOwner)
}