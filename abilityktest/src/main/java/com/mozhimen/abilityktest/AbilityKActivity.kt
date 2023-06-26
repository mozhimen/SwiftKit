package com.mozhimen.abilityktest

import android.view.View
import com.mozhimen.abilityktest.databinding.ActivityAbilitykBinding
import com.mozhimen.abilityktest.scank.ScanKActivity
import com.mozhimen.abilityktest.transk.TransKActivity
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext

class AbilityKActivity : BaseActivityVB<ActivityAbilitykBinding>() {

    fun goScanK(view: View) {
        startContext<ScanKActivity>()
    }

    fun goTransK(view: View) {
        startContext<TransKActivity>()
    }
}