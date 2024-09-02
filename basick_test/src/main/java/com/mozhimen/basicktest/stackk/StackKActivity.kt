package com.mozhimen.basicktest.stackk

import android.view.View
import com.mozhimen.kotlin.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.kotlin.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityStackkBinding

class StackKActivity : BaseActivityVDB<ActivityStackkBinding>() {

    fun goStackKCb(view: View) {
        startContext<StackKCbActivity>()
    }

    fun goStackKProcess(view: View) {
        startContext<StackKProcessActivity>()
    }
}