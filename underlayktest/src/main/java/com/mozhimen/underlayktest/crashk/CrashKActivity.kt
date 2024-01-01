package com.mozhimen.underlayktest.crashk

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVB
import com.mozhimen.underlayktest.databinding.ActivityCrashkBinding

class CrashKActivity : BaseActivityVB<ActivityCrashkBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        val a = 12 / 0
    }
}