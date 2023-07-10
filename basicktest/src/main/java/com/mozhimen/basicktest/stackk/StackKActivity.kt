package com.mozhimen.basicktest.stackk

import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityStackkBinding

class StackKActivity : BaseActivityVB<ActivityStackkBinding>() {

    fun goStackKCb(view: View) {
        startContext<StackKCbActivity>()
    }

    fun goStackKProcess(view: View) {
        startContext<StackKProcessActivity>()
    }
}