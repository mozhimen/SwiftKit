package com.mozhimen.basick.elemk.android.app.bases

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

    private var _lifecycleRegistry: LifecycleRegistry? = null
    protected val lifecycleRegistry: LifecycleRegistry
        get() = _lifecycleRegistry ?: LifecycleRegistry(this).also {
            _lifecycleRegistry = it
        }

    //////////////////////////////////////////////////////////////////////////

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    @CallSuper
    override fun onBind(intent: Intent): IBinder {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        return binder
    }

    @CallSuper
    override fun onDestroy() {
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        super.onDestroy()
    }

    override fun getLifecycle(): Lifecycle =
        lifecycleRegistry
}
