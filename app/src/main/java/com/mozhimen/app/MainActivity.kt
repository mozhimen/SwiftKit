package com.mozhimen.app

import android.view.View
import com.mozhimen.app.databinding.ActivityMainBinding
import com.mozhimen.app.demo.DemoActivity
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.startContext

class MainActivity : BaseActivityVB<ActivityMainBinding>() {
    fun goDemo(view: View) {
        startContext<DemoActivity>()
    }
}