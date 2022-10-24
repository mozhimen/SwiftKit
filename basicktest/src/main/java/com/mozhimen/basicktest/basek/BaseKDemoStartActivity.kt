package com.mozhimen.basicktest.basek

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.basicktest.databinding.ActivityBasekDemoStartBinding

class BaseKDemoStartActivity : BaseKActivityVB<ActivityBasekDemoStartBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        vb.basekDemoStartBtnGoBasekActivity.setOnClickListener {
            start<BaseKDemoActivity>()
        }
    }

    fun goBaseKDemoServiceActivity(view: View) {
        start<BaseKDemoServiceActivity>()
    }
}