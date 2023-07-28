package com.mozhimen.basick.taskk.bases

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefPauseLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle

@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
abstract class BaseWakeBefPauseTaskK : BaseWakeBefPauseLifecycleObserver() {

    abstract fun isActive(): Boolean

    @CallSuper
    override fun onPause(owner: LifecycleOwner) {
        cancel()
        super.onDestroy(owner)
    }

    abstract fun cancel()
}