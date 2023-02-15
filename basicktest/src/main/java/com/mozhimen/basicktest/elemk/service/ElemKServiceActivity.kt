package com.mozhimen.basicktest.elemk.service

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.elemk.service.bases.BaseServiceResCallback
import com.mozhimen.basick.elemk.service.ServiceDelegate
import com.mozhimen.basicktest.databinding.ActivityElemkServiceBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ElemKServiceActivity : BaseActivityVB<ActivityElemkServiceBinding>() {

    private lateinit var _serviceDelegate: ServiceDelegate<ElemKServiceActivity>

    private var _resListener: BaseServiceResCallback = object : BaseServiceResCallback() {
        override fun onResString(resString: String?) {
            runOnUiThread {
                vb.elemkServiceTxt.text = resString ?: "loss"
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        _serviceDelegate = ServiceDelegate(this, ElemKService::class.java, _resListener)
        lifecycleScope.launch(Dispatchers.Main) {
            delay(2000)
            Log.d(TAG, "initData: ${_serviceDelegate.getConnListener()?.launchCommand("123")}")
            delay(2000)
            Log.d(TAG, "initData: ${_serviceDelegate.getConnListener()?.launchCommand("456")}")
            delay(2000)
            Log.d(TAG, "initData: ${_serviceDelegate.getConnListener()?.launchCommand("123")}")
        }
        super.initData(savedInstanceState)
    }
}