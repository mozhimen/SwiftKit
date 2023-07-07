package com.mozhimen.basick.taskk.bases

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.AOptLazyInit
import com.mozhimen.basick.elemk.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver

@AOptLazyInit
abstract class BaseWakeBefDestroyTaskK : BaseWakeBefDestroyLifecycleObserver() {

    abstract fun isActive(): Boolean

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        cancel()
        super.onDestroy(owner)
    }

    abstract fun cancel()
}