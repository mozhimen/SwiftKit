package com.mozhimen.basicktest.utilk.androidx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basick.utilk.androidx.lifecycle.UtilKDataBus
import com.mozhimen.basicktest.R
import com.mozhimen.basicktest.databinding.ActivityUtilkAndroidxBinding

class UtilKAndroidXActivity : BaseActivityVB<ActivityUtilkAndroidxBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        UtilKDataBus.with<String>("stickyData").setStickyData("即时消息主界面")
        super.initData(savedInstanceState)
    }

    fun goUtilKDataBus(view: View) {
        startContext<UtilKDataBusActivity>()
    }
}