package com.mozhimen.servicek

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.servicek.commons.IBaseServiceConnListener
import com.mozhimen.servicek.commons.IBaseServiceResListener
import com.mozhimen.basick.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.servicek.commons.IServiceKProxy
import com.mozhimen.kotlin.utilk.android.util.e
import com.mozhimen.basick.utils.runOnMainThread

/**
 * @ClassName ServiceKProxy
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Version 1.0
 */

@OApiCall_BindLifecycle
@OApiInit_ByLazy
open class ServiceKProxy<A>(
    private val _activity: A,
    private val _serviceClazz: Class<*>,
    private val _resListener: IBaseServiceResListener
) : BaseWakeBefDestroyLifecycleObserver(), IServiceKProxy
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
            Intent(_activity, _serviceClazz), _serviceConnection, AppCompatActivity.BIND_AUTO_CREATE
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
            e.message?.e(TAG)
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
                e.message?.e(TAG)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }
}
