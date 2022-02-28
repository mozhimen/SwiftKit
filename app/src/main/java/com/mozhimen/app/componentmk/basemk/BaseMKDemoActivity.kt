package com.mozhimen.app.componentmk.basemk

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityBasemkActivityBinding
import com.mozhimen.componentmk.basemk.BaseMKActivity

class BaseMKDemoActivity :
    BaseMKActivity<ActivityBasemkActivityBinding, BaseMKDemoViewModel>(R.layout.activity_basemk_activity) {

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
        vb.basemkActivityTxt.text = "SUM: $_sum"
        vb.basemkActivityBtn.setOnClickListener {
            _sum++
            vb.basemkActivityTxt.text = "SUM: $_sum"
            Log.i(TAG, "initView: $_sum")
        }
    }
}