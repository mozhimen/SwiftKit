package com.mozhimen.taskk.bases

import androidx.annotation.CallSuper
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.bases.BaseWakeBefPauseLifecycleObserver
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle

@OApiCall_BindLifecycle
@OApiInit_ByLazy
abstract class BaseWakeBefPauseTaskK : BaseWakeBefPauseLifecycleObserver() {

    abstract fun isActive(): Boolean

    @CallSuper
    override fun onPause(owner: LifecycleOwner) {
        cancel()
        super.onDestroy(owner)
    }

    abstract fun cancel()
}