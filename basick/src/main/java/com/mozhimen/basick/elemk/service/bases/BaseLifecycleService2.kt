package com.mozhimen.basick.elemk.service.bases

import android.content.Intent
import android.os.IBinder
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * @ClassName BaseService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:36
 * @Version 1.0
 */
open class BaseLifecycleService2 : BaseService(), LifecycleOwner {

    private val _lifecycleRegistry: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        _lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    @CallSuper
    override fun onBind(intent: Intent): IBinder {
        _lifecycleRegistry.currentState = Lifecycle.State.STARTED
        return binder
    }

    @CallSuper
    override fun onDestroy() {
        _lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onDestroy()
    }

    override fun getLifecycle(): Lifecycle {
        return _lifecycleRegistry
    }
}
