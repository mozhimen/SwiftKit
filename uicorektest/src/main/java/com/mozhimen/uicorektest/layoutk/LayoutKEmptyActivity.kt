package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.uicorektest.databinding.ActivityLayoutkEmptyBinding

class LayoutKEmptyActivity : BaseActivityVB<ActivityLayoutkEmptyBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        vb.layoutkEmpty.setButton("点击进入后台") {
            "点击进入后台".showToast()
        }
    }
}