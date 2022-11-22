package com.mozhimen.underlayktest

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayktest.databinding.ActivityUnderlaykBinding
import com.mozhimen.underlayktest.logk.LogKActivity

class UnderlayKActivity : BaseActivityVB<ActivityUnderlaykBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.underlaykPrintLog.setOnClickListener { LogK.w("这是一个测试") }
    }

    fun goLogK(view: View) {
        start<LogKActivity>()
    }
}