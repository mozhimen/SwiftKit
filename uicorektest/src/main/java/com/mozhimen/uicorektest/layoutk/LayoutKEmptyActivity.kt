package com.mozhimen.uicorektest.layoutk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.uicorektest.R
import com.mozhimen.uicorektest.databinding.ActivityLayoutkEmptyBinding

class LayoutKEmptyActivity : BaseKActivityVB<ActivityLayoutkEmptyBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        vb.layoutkEmptyContainer.setButton("点击进入后台") {
            "点击进入后台".showToast()
        }
    }
}