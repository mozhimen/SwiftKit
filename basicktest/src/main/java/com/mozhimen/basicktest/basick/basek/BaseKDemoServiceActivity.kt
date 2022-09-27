package com.mozhimen.basicktest.basick.basek

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import com.mozhimen.basick.IBaseKServiceConnListener
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.basek.service.BaseKServiceResCallback
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityBasekDemoServiceBinding

class BaseKDemoServiceActivity : BaseKActivity<ActivityBasekDemoServiceBinding, BaseKViewModel>(R.layout.activity_basek_demo_service) {
    private var _connListener: IBaseKServiceConnListener? = null
    private var _resListener: BaseKServiceResCallback? = object : BaseKServiceResCallback() {
        override fun onResString(resString: String?) {
            runOnUiThread {
                vb.basekServiceTxt.text = resString ?: "loss"
            }
        }
    }

    private val _serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            _connListener = IBaseKServiceConnListener.Stub.asInterface(service)
            try {
                _connListener?.apply {
                    registerListener(_resListener)
                    onStart()
                }
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        bindService(Intent(this, BaseKDemoService::class.java), _serviceConnection, BIND_AUTO_CREATE)
    }

    private fun unregisterCallback() {
        try {
            _connListener?.unRegisterListener(_resListener)
            _resListener = null
            _connListener = null
            unbindService(_serviceConnection)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        unregisterCallback()
        super.onPause()
    }
}