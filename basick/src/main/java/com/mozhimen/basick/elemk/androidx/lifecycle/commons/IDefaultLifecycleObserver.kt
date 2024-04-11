package com.mozhimen.basick.elemk.androidx.lifecycle.commons

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basick.utilk.commons.IUtilK

/**
 * @ClassName IDefaultLifecycleObserver
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Version 1.0
 */
@OApiInit_ByLazy
@OApiCall_BindLifecycle
interface IDefaultLifecycleObserver : DefaultLifecycleObserver, IUtilK {
    fun bindLifecycle(owner: LifecycleOwner)
}