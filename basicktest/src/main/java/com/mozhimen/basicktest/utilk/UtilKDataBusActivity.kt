package com.mozhimen.basicktest.utilk

import android.os.Bundle
import com.mozhimen.basick.elemk.activity.commons.BaseActivityVB
import com.mozhimen.basick.extsk.showToast
import com.mozhimen.basick.utilk.UtilKDataBus
import com.mozhimen.basicktest.databinding.ActivityUtilkDataBusBinding

class UtilKDataBusActivity : BaseActivityVB<ActivityUtilkDataBusBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        UtilKDataBus.with<String>("stickyData").observeSticky(this, true) {
            "黏性事件: $it".showToast()
        }

        UtilKDataBus.with<String>("stickyData").observe(this) {
            "非黏性事件: $it".showToast()
        }

        vb.utilkDataBusMsgBtn.setOnClickListener {
            UtilKDataBus.with<String>("stickyData").setStickyData("即时消息当前界面")
        }
    }
}