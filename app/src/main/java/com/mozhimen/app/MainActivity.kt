package com.mozhimen.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mozhimen.app.databinding.ActivityMainBinding
import com.mozhimen.app.demo.DemoActivity
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start

class MainActivity : BaseActivityVB<ActivityMainBinding>() {
    fun goDemo(view: View) {
        start<DemoActivity>()
    }
}