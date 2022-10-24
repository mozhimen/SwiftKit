package com.mozhimen.componentktest.animk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.componentktest.databinding.ActivityAnimkBinding

class AnimKActivity : BaseKActivityVB<ActivityAnimkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        vb.animkBgAlphaFlash
    }
}