package com.mozhimen.basick.elemk.android.app

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.elemk.android.app.commons.IBaseServiceConnListener
import com.mozhimen.basick.elemk.android.app.commons.IBaseServiceResListener
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainThread

/**
 * @ClassName ServiceProxy
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/28 16:02
 * @Version 1.0
 */
interface IServiceProxy {
    fun getConnListener(): IBaseServiceConnListener?
    fun bindService()
    fun unbindService()
}

@OptInApiCall_BindLifecycle
@OptInApiInit_ByLazy
class ServiceProxy<A>(
    private val _activity: A,
    private val _service: Class<*>,
    private val _resListener: IBaseServiceResListener
) : BaseWakeBefDestroyLifecycleObserver(), IServiceProxy
        where A : AppCompatActivity, A : LifecycleOwner {
    private var _connListener: IBaseServiceConnListener? = null
    private val _serviceConnection: ServiceConnection by lazy { BaseServiceConnection() }
    private var _isBindService = false

    init {
        _activity.runOnMainThread(::bindService)
    }

    override fun getConnListener(): IBaseServiceConnListener? =
        _connListener

    override fun bindService() {
        _isBindService = true
        _activity.bindService(
            Intent(_activity, _service), _serviceConnection, AppCompatActivity.BIND_AUTO_CREATE
        ).also { Log.d(TAG, "bindService: _isBindService $_isBindService") }
    }

    override fun unbindService() {
        try {
            _connListener?.apply {
                unRegisterListener(_resListener)
                onServiceStop()
            }
            _activity.unbindService(_serviceConnection)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        if (_isBindService) {
            Log.d(TAG, "onDestroy: unbindService")
            unbindService()
            _isBindService = false
        }
        super.onDestroy(owner)
    }

    inner class BaseServiceConnection : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            _connListener = IBaseServiceConnListener.Stub.asInterface(service)
            try {
                _connListener?.apply {
                    registerListener(_resListener)
                    onServiceStart()
                }
            } catch (e: RemoteException) {
                e.printStackTrace()
                e.message?.et(TAG)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }
}
