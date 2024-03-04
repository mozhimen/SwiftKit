package com.mozhimen.basicktest.utilk.androidx

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basicktest.databinding.ActivityUtilkActionBarBinding

class UtilKActionBarActivity : BaseActivityVDB<ActivityUtilkActionBarBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(vdb.utilkActionToolbar)
        supportActionBar?.title = NAME
        supportActionBar?.subtitle = TAG
    }
}