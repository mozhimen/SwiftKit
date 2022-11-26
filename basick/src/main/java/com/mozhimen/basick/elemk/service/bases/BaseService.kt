package com.mozhimen.basick.elemk.service.bases

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.os.RemoteException
import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.mozhimen.basick.elemk.service.commons.IBaseServiceConnListener
import com.mozhimen.basick.elemk.service.commons.IBaseServiceResListener

/**
 * @ClassName BaseService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:36
 * @Version 1.0
 */
open class BaseService : Service(), LifecycleOwner {
    protected val TAG = "${this.javaClass.simpleName}>>>>>"
    private val _listeners = RemoteCallbackList<IBaseServiceResListener>()
    private var _binder: IBaseServiceConnListener.Stub? = BaseServiceBinder()

    inner class BaseServiceBinder : IBaseServiceConnListener.Stub() {
        override fun onServiceStart() {

        }

        override fun registerListener(listener: IBaseServiceResListener?) {
            listener?.let { _listeners.register(it) }
        }

        override fun unRegisterListener(listener: IBaseServiceResListener?) {
            listener?.let { _listeners.unregister(it) }
        }

        override fun onServiceStop() {
            _listeners.kill()
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return _binder
    }

    protected fun onCallback(res: Any) {
        val listenerSize = _listeners.beginBroadcast()
        try {
            for (i in 0 until listenerSize) {
                when (res) {
                    is Int -> {
                        _listeners.getBroadcastItem(i).onResInt(res)
                    }
                    is Long -> {
                        _listeners.getBroadcastItem(i).onResLong(res)
                    }
                    is Boolean -> {
                        _listeners.getBroadcastItem(i).onResBoolean(res)
                    }
                    is Float -> {
                        _listeners.getBroadcastItem(i).onResFloat(res)
                    }
                    is Double -> {
                        _listeners.getBroadcastItem(i).onResDouble(res)
                    }
                    is String -> {
                        _listeners.getBroadcastItem(i).onResString(res)
                    }
                    else -> {}
                }
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        _listeners.finishBroadcast()
    }

    private val _lifecycleRegistry: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        _lifecycleRegistry.currentState = Lifecycle.State.STARTED
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
