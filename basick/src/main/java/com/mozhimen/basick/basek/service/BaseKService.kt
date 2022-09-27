package com.mozhimen.basick.basek.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.os.RemoteException
import com.mozhimen.basick.IBaseKServiceConnListener
import com.mozhimen.basick.IBaseKServiceResListener

/**
 * @ClassName BaseKService
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/9/27 0:36
 * @Version 1.0
 */
abstract class BaseKService : Service() {
    private val _listeners = RemoteCallbackList<IBaseKServiceResListener>()

    private val _binder = object : IBaseKServiceConnListener.Stub() {
        override fun onServiceStart() {

        }

        override fun registerListener(listener: IBaseKServiceResListener?) {
            listener?.let { _listeners.register(it) }
        }

        override fun unRegisterListener(listener: IBaseKServiceResListener?) {
            listener?.let { _listeners.unregister(it) }
        }
    }

    override fun onBind(intent: Intent?): IBinder {
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

    override fun onDestroy() {
        _listeners.kill()
        super.onDestroy()
    }
}
