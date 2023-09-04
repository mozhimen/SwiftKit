package com.mozhimen.basicktest.utilk.androidx

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basicktest.databinding.ActivityUtilkActionBarBinding

class UtilKActionBarActivity : BaseActivityVB<ActivityUtilkActionBarBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(vb.utilkActionToolbar)
        supportActionBar?.title = NAME
        supportActionBar?.subtitle = TAG
    }
}