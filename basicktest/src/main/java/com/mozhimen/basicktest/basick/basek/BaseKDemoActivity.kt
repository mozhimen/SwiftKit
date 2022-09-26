package com.mozhimen.basicktest.basick.basek

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKService
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityBasekActivityBinding

class BaseKDemoActivity :
    BaseKActivity<ActivityBasekActivityBinding, BaseKDemoViewModel>(R.layout.activity_basek_activity) {

    override fun injectVM() {
        vb.vm = vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        initService()
    }

    private fun initService() {
        val intent = Intent(this, BaseKDemoService::class.java)
        bindService(intent, _serviceConnection, BIND_AUTO_CREATE)
    }

    private val _serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            val binder = service as? BaseKService<BaseKDemoService>.BaseKServiceBinder?
            binder?.service?.setListener {
                Log.d(TAG, "onServiceConnected: success")
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    private var _sum = 0

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {
        vb.basekActivityTxt.text = "SUM: $_sum"
        vb.basekActivityBtn.setOnClickListener {
            _sum++
            vb.basekActivityTxt.text = "SUM: $_sum"
            Log.i(TAG, "initView: $_sum")
        }
    }

    override fun onPause() {
        unbindService(_serviceConnection)
        super.onPause()
    }
}