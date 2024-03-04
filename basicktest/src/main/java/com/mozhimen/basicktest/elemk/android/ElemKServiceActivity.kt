package com.mozhimen.basicktest.elemk.android

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.lintk.optins.OApiInit_ByLazy
import com.mozhimen.basick.elemk.android.app.bases.BaseServiceResCallback
import com.mozhimen.basick.elemk.android.app.ServiceProxy
import com.mozhimen.basick.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.basicktest.databinding.ActivityElemkServiceBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ElemKServiceActivity : BaseActivityVDB<ActivityElemkServiceBinding>() {

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    private val _serviceProxy: ServiceProxy<ElemKServiceActivity> by lazy { ServiceProxy(this, ElemKService::class.java, _resListener) }

    private var _resListener: BaseServiceResCallback = object : BaseServiceResCallback() {
        override fun onResString(resString: String?) {
            runOnUiThread {
                vdb.elemkServiceTxt.text = resString ?: "loss"
            }
        }
    }

    @OptIn(OApiInit_ByLazy::class, OApiCall_BindLifecycle::class)
    override fun initView(savedInstanceState: Bundle?) {
        _serviceProxy.bindLifecycle(this)
        lifecycleScope.launch(Dispatchers.Main) {
            delay(2000)
            Log.d(TAG, "initData: ${_serviceProxy.getConnListener()?.launchCommand("123")}")
            delay(2000)
            Log.d(TAG, "initData: ${_serviceProxy.getConnListener()?.launchCommand("456")}")
            delay(2000)
            Log.d(TAG, "initData: ${_serviceProxy.getConnListener()?.launchCommand("123")}")
        }
    }
}