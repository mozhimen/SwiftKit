package com.mozhimen.abilityktest

import android.os.Bundle
import android.view.View
import com.mozhimen.abilityktest.databinding.ActivityAbilitykBinding
import com.mozhimen.abilityktest.opencvk.OpenCVKActivity
import com.mozhimen.abilityktest.scank.ScanKActivity
import com.mozhimen.abilityktest.transk.TransKActivity
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start

class AbilityKActivity : BaseKActivityVB<ActivityAbilitykBinding>() {

    fun goScanK(view: View) {
        start<ScanKActivity>()
    }

    fun goTransK(view: View) {
        start<TransKActivity>()
    }

    fun goOpenCVK(view: View) {
        start<OpenCVKActivity>()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}