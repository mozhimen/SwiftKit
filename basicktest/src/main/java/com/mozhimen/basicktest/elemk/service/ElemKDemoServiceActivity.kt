package com.mozhimen.basicktest.elemk.service

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.elemk.service.commons.BaseServiceResCallback
import com.mozhimen.basick.elemk.service.LifecycleServiceDelegate
import com.mozhimen.basicktest.databinding.ActivityElemkDemoServiceBinding

class ElemKDemoServiceActivity : BaseActivityVB<ActivityElemkDemoServiceBinding>() {

    private lateinit var _elemKServiceDelegate: LifecycleServiceDelegate<ElemKDemoServiceActivity>

    private var _resListener: BaseServiceResCallback = object : BaseServiceResCallback() {
        override fun onResString(resString: String?) {
            runOnUiThread {
                vb.elemkServiceTxt.text = resString ?: "loss"
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        _elemKServiceDelegate = LifecycleServiceDelegate(this, ElemKDemoService::class.java, _resListener)
        super.initData(savedInstanceState)
    }
}