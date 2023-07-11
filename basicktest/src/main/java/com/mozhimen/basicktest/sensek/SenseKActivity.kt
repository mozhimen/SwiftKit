package com.mozhimen.basicktest.sensek

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.sensek.eventbus.SenseKLiveDataEventBus
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivitySensekBinding

class SenseKActivity : BaseActivityVB<ActivitySensekBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        SenseKLiveDataEventBus.with<String>("stickyData").setStickyValue("即时消息主界面")
        super.initData(savedInstanceState)
    }

    fun goLiveDataEventBus(view: View) {
        startContext<LiveDataEventBusActivity>()
    }

    fun goSystemBar(view: View) {
        startContext<SystemBarActivity>()
    }

    fun goNetConn(view: View) {
        startContext<NetConnActivity>()
    }
}