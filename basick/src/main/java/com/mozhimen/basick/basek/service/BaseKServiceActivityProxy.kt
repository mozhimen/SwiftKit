package com.mozhimen.basick.basek.service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.IBaseKServiceConnListener

/**
 * @ClassName BaseKServiceActivityProxy
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/9/28 16:02
 * @Version 1.0
 */
/*
class BaseKServiceActivityProxy(private val _activity: AppCompatActivity) {
    private var _connListener: IBaseKServiceConnListener? = null

    private val _serviceConnection = object : ServiceConnection {
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

    private fun unregisterCallback() {
        try {
            _connListener?.unRegisterListener(_resListener)
            unbindService(_serviceConnection)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun bindService() {
        bindService(Intent(this, BaseKDemoService::class.java), _serviceConnection, AppCompatActivity.BIND_AUTO_CREATE)
    }
}*/
