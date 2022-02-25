package com.mozhimen.app.componentmk.basemk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityBasemkActivityBinding
import com.mozhimen.componentmk.basemk.BaseMKActivity

class BaseMKDemoActivity :
    BaseMKActivity<ActivityBasemkActivityBinding, BaseMKDemoViewModel>(R.layout.activity_basemk_activity) {

    override fun assignVM() {
        vb.vm = vm
    }

    private var _sum = 0
    override fun initView() {
        vb.basemkActivityTxt.text = "Sum: $_sum"
        vb.basemkActivityBtn.setOnClickListener {
            _sum++
            vb.basemkActivityTxt.text = "Sum: $_sum"
        }
    }
}