package com.mozhimen.uicorektest

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.uicorektest.adaptk.AdaptKActivity
import com.mozhimen.uicorektest.databinding.ActivityUicorekBinding

class UicoreKActivity : BaseActivityVB<ActivityUicorekBinding>() {

    fun goAdaptK(view: View) {
        startContext<AdaptKActivity>()
    }
}