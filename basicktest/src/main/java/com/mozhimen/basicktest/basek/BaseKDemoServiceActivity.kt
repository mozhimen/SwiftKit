package com.mozhimen.basicktest.basek

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.service.BaseKServiceResCallback
import com.mozhimen.basick.prefabk.service.PrefabKServiceDelegate
import com.mozhimen.basicktest.databinding.ActivityBasekDemoServiceBinding

class BaseKDemoServiceActivity : BaseKActivityVB<ActivityBasekDemoServiceBinding>() {

    private lateinit var _prefabKServiceDelegate: PrefabKServiceDelegate<BaseKDemoServiceActivity>

    private var _resListener: BaseKServiceResCallback = object : BaseKServiceResCallback() {
        override fun onResString(resString: String?) {
            runOnUiThread {
                vb.basekServiceTxt.text = resString ?: "loss"
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        _prefabKServiceDelegate = PrefabKServiceDelegate(this, BaseKDemoService::class.java, _resListener)
    }

    override fun onDestroy() {
        Log.w(TAG, "onDestroy: service is stop")
        super.onDestroy()
    }
}