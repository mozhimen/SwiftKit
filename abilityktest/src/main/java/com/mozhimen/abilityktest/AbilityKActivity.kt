package com.mozhimen.abilityktest

import android.view.View
import com.mozhimen.abilityktest.databinding.ActivityAbilitykBinding
import com.mozhimen.abilityktest.hotfixk.HotFixKActivity
import com.mozhimen.abilityktest.opencvk.OpenCVKActivity
import com.mozhimen.abilityktest.scank.ScanKActivity
import com.mozhimen.abilityktest.transk.TransKActivity
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start

class AbilityKActivity : BaseKActivity<ActivityAbilitykBinding, BaseKViewModel>(R.layout.activity_abilityk) {

    fun goHotfixK(view: View) {
        start<HotFixKActivity>()
    }

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