package com.mozhimen.componentktest.animk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityAnimkBinding

class AnimKActivity : BaseKActivity<ActivityAnimkBinding,BaseKViewModel>(R.layout.activity_animk) {
    override fun initData(savedInstanceState: Bundle?) {
        vb.animkBgAlphaFlash
    }
}