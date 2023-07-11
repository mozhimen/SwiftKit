package com.mozhimen.basicktest.sensek

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.sensek.eventbus.SenseKLiveDataEventBus
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityUtilkDataBusBinding

class LiveDataEventBusActivity : BaseActivityVB<ActivityUtilkDataBusBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        SenseKLiveDataEventBus.with<String>("stickyData").observeSticky(this, true) {
            "黏性事件: $it".showToast()
        }

        SenseKLiveDataEventBus.with<String>("stickyData").observe(this) {
            "非黏性事件: $it".showToast()
        }

        vb.utilkDataBusMsgBtn.setOnClickListener {
            SenseKLiveDataEventBus.with<String>("stickyData").setStickyData("即时消息当前界面")
        }
    }
}