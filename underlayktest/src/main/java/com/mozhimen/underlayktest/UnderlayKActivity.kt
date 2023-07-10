package com.mozhimen.underlayktest

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.underlayk.logk.LogK
import com.mozhimen.underlayktest.crashk.CrashKActivity
import com.mozhimen.underlayktest.databinding.ActivityUnderlaykBinding
import com.mozhimen.underlayktest.fpsk.FpsKActivity
import com.mozhimen.underlayktest.logk.LogKActivity

class UnderlayKActivity : BaseActivityVB<ActivityUnderlaykBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.underlaykPrintLog.setOnClickListener { LogK.w("这是一个测试") }
    }

    fun goCrashK(view: View) {
        startContext<CrashKActivity>()
    }

    fun goFpsK(view: View) {
        startContext<FpsKActivity>()
    }

    fun goLogK(view: View) {
        startContext<LogKActivity>()
    }
}