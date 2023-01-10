package com.mozhimen.basicktest.elemk.service

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.elemk.service.bases.BaseServiceResCallback
import com.mozhimen.basick.elemk.service.LifecycleServiceDelegate
import com.mozhimen.basicktest.databinding.ActivityElemkServiceBinding

class ElemKServiceActivity : BaseActivityVB<ActivityElemkServiceBinding>() {

    private lateinit var _elemKServiceDelegate: LifecycleServiceDelegate<ElemKServiceActivity>

    private var _resListener: BaseServiceResCallback = object : BaseServiceResCallback() {
        override fun onResString(resString: String?) {
            runOnUiThread {
                vb.elemkServiceTxt.text = resString ?: "loss"
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        _elemKServiceDelegate = LifecycleServiceDelegate(this, ElemKService::class.java, _resListener)
        super.initData(savedInstanceState)
    }
}