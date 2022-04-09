package com.mozhimen.app.componentk.basek

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityBasekActivityBinding
import com.mozhimen.componentk.basek.BaseKActivity

class BaseKDemoActivity :
    BaseKActivity<ActivityBasekActivityBinding, BaseKDemoViewModel>(R.layout.activity_basek_activity) {

    override fun assignVM() {
        vb.vm = vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initView()
    }

    private var _sum = 0

    @SuppressLint("SetTextI18n")
    override fun initView() {
        vb.basekActivityTxt.text = "SUM: $_sum"
        vb.basekActivityBtn.setOnClickListener {
            _sum++
            vb.basekActivityTxt.text = "SUM: $_sum"
            Log.i(TAG, "initView: $_sum")
        }
    }
}