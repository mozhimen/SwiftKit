package com.mozhimen.basick.taskk.bases

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle

@AOptInNeedCallBindLifecycle
@AOptInInitByLazy
abstract class BaseWakeBefDestroyTaskK : BaseWakeBefDestroyLifecycleObserver() {

    abstract fun isActive(): Boolean

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        cancel()
        super.onDestroy(owner)
    }

    abstract fun cancel()
}