package com.mozhimen.basick.basek.service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.IBaseKServiceConnListener
import com.mozhimen.basick.IBaseKServiceResListener

/**
 * @ClassName BaseKServiceProxy
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/28 16:02
 * @Version 1.0
 */
class BaseKServiceActivityDelegate<T>(
    private val _activity: T,
    private val _service: Class<*>,
    private val _resListener: IBaseKServiceResListener
) : DefaultLifecycleObserver
        where T : AppCompatActivity, T : LifecycleOwner {
    private var _connListener: IBaseKServiceConnListener? = null
    private var _serviceConnection: ServiceConnection = BaseKServiceConnection()
    private var _isBindService = false

    init {
        _activity.lifecycle.addObserver(this)
        bindService()
    }

    fun bindService() {
        _isBindService = _activity.bindService(
            Intent(_activity, _service), _serviceConnection, AppCompatActivity.BIND_AUTO_CREATE
        )
    }

    fun unbindService() {
        try {
            _connListener?.apply {
                unRegisterListener(_resListener)
                onServiceStop()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        _activity.unbindService(
            _serviceConnection
        )
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (_isBindService) {
            unbindService()
            _isBindService = false
        }
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }

    inner class BaseKServiceConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            _connListener = IBaseKServiceConnListener.Stub.asInterface(service)
            try {
                _connListener?.apply {
                    registerListener(_resListener)
                    onServiceStart()
                }
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }
}
