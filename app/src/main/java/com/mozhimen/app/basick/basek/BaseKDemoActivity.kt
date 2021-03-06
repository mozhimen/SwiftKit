package com.mozhimen.app.basick.basek

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityBasekActivityBinding
import com.mozhimen.basick.basek.BaseKActivity

class BaseKDemoActivity :
    BaseKActivity<ActivityBasekActivityBinding, BaseKDemoViewModel>(R.layout.activity_basek_activity) {

    override fun injectVM()  {
        vb.vm = vm
    }

    override fun initData(savedInstanceState: Bundle?) {
        initView(savedInstanceState)
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
}