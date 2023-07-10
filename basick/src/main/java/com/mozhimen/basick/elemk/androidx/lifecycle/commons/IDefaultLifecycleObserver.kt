package com.mozhimen.basick.elemk.androidx.lifecycle.commons

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import com.mozhimen.basick.utilk.bases.IUtilK

/**
 * @ClassName IDefaultLifecycleObserver
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/2/19 12:08
 * @Version 1.0
 */
@AOptInInitByLazy
@AOptInNeedCallBindLifecycle
interface IDefaultLifecycleObserver : DefaultLifecycleObserver, IUtilK {
    fun bindLifecycle(owner: LifecycleOwner)
}