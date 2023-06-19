package com.mozhimen.basick.elemk.service.bases

import android.content.Intent
import android.os.IBinder
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher

/**
 * @ClassName BaseLifecycleService
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/6/10 18:57
 * @Version 1.0
 */
class BaseLifecycleService : BaseService(), LifecycleOwner {

    private val _serviceLifecycleDispatcher = ServiceLifecycleDispatcher(this)

    @CallSuper
    override fun onCreate() {
        _serviceLifecycleDispatcher.onServicePreSuperOnCreate()
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        _serviceLifecycleDispatcher.onServicePreSuperOnBind()
        return binder
    }

    @Deprecated("Deprecated in Java")
    @Suppress("deprecation")
    @CallSuper
    override fun onStart(intent: Intent?, startId: Int) {
        _serviceLifecycleDispatcher.onServicePreSuperOnStart()
        super.onStart(intent, startId)
    }

    // this method is added only to annotate it with @CallSuper.
    // In usual service super.onStartCommand is no-op, but in LifecycleService
    // it results in mDispatcher.onServicePreSuperOnStart() call, because
    // super.onStartCommand calls onStart().
    @CallSuper
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    @CallSuper
    override fun onDestroy() {
        _serviceLifecycleDispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }

    override fun getLifecycle(): Lifecycle {
        return _serviceLifecycleDispatcher.lifecycle
    }
}