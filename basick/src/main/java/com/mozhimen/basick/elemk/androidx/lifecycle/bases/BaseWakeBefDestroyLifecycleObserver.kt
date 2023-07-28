package com.mozhimen.basick.elemk.androidx.lifecycle.bases

import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.commons.IDefaultLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName BaseWakeBefDestroyLifecycleObserver
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/11/21 21:22
 * @Version 1.0
 */
@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
open class BaseWakeBefDestroyLifecycleObserver : IDefaultLifecycleObserver, BaseUtilK() {

    override fun bindLifecycle(owner: LifecycleOwner) {
        owner.runOnMainThread {
            owner.lifecycle.removeObserver(this@BaseWakeBefDestroyLifecycleObserver)
            owner.lifecycle.addObserver(this@BaseWakeBefDestroyLifecycleObserver)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this@BaseWakeBefDestroyLifecycleObserver)
    }
}