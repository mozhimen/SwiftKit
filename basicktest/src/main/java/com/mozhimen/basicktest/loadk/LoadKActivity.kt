package com.mozhimen.basicktest.loadk

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityLoadkBinding

class LoadKActivity : BaseKActivity<ActivityLoadkBinding, BaseKViewModel>(R.layout.activity_loadk) {
    override fun initData(savedInstanceState: Bundle?) {
        vb.loadkRestart.setOnClickListener {
        }
    }
}