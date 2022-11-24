package com.mozhimen.abilityktest

import android.view.View
import com.mozhimen.abilityktest.databinding.ActivityAbilitykBinding
import com.mozhimen.abilityktest.opencvk.OpenCVKActivity
import com.mozhimen.abilityktest.scank.ScanKActivity
import com.mozhimen.abilityktest.transk.TransKActivity
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start

class AbilityKActivity : BaseActivityVB<ActivityAbilitykBinding>() {

    fun goScanK(view: View) {
        start<ScanKActivity>()
    }

    fun goTransK(view: View) {
        start<TransKActivity>()
    }

    fun goOpenCVK(view: View) {
        start<OpenCVKActivity>()
    }
}