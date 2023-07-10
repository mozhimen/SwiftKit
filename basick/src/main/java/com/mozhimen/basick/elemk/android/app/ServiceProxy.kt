package com.mozhimen.basick.elemk.android.app

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.lintk.optin.annors.AOptInInitByLazy
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseWakeBefDestroyLifecycleObserver
import com.mozhimen.basick.elemk.service.commons.IBaseServiceConnListener
import com.mozhimen.basick.elemk.service.commons.IBaseServiceResListener
import com.mozhimen.basick.lintk.optin.annors.AOptInNeedCallBindLifecycle
import com.mozhimen.basick.utilk.android.util.et
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName ServiceProxy
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/28 16:02
 * @Version 1.0
 */
@AOptInNeedCallBindLifecycle
@AOptInInitByLazy
class ServiceProxy<A>(
    private val _activity: A,
    private val _service: Class<*>,
    private val _resListener: IBaseServiceResListener
) : BaseWakeBefDestroyLifecycleObserver()
        where A : AppCompatActivity, A : LifecycleOwner {
    private var _connListener: IBaseServiceConnListener? = null
    private val _serviceConnection: ServiceConnection by lazy { BaseServiceConnection() }
    private var _isBindService = false

    init {
        _activity.lifecycleScope.launch(Dispatchers.Main) {
            bindService()
        }
    }

    fun getConnListener(): IBaseServiceConnListener? = _connListener

    @SuppressLint("LongLogTag")
    fun bindService() {
        _isBindService = true
        _activity.bindService(
            Intent(_activity, _service), _serviceConnection, AppCompatActivity.BIND_AUTO_CREATE
        ).also { Log.d(TAG, "bindService: _isBindService $_isBindService") }
    }

    fun unbindService() {
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

    @SuppressLint("LongLogTag")
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
