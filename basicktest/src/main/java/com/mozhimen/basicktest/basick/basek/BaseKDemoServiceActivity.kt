package com.mozhimen.basicktest.basick.basek

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.basek.service.BaseKServiceActivityDelegate
import com.mozhimen.basick.basek.service.BaseKServiceResCallback
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityBasekDemoServiceBinding

class BaseKDemoServiceActivity : BaseKActivity<ActivityBasekDemoServiceBinding, BaseKViewModel>(R.layout.activity_basek_demo_service) {

    private lateinit var _baseKServiceActivityDelegate: BaseKServiceActivityDelegate<BaseKDemoServiceActivity>
    private var _resListener: BaseKServiceResCallback = object : BaseKServiceResCallback() {
        override fun onResString(resString: String?) {
            runOnUiThread {
                vb.basekServiceTxt.text = resString ?: "loss"
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        _baseKServiceActivityDelegate = BaseKServiceActivityDelegate(this, BaseKDemoService::class.java, _resListener)
    }

    override fun onDestroy() {
        Log.w(TAG, "onDestroy: service is stop")
        super.onDestroy()
    }
}