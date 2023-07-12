package com.mozhimen.basicktest.postk

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.postk.livedata.PostKLiveDataEventBus
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityUtilkDataBusBinding

class PostKLiveDataEventBusActivity : BaseActivityVB<ActivityUtilkDataBusBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        PostKLiveDataEventBus.with<String>("stickyData").observeSticky(this) {
            "黏性事件: $it".showToast()
        }

        PostKLiveDataEventBus.with<String>("stickyData").observe(this) {
            "非黏性事件: $it".showToast()
        }

        vb.utilkDataBusMsgBtn.setOnClickListener {
            PostKLiveDataEventBus.with<String>("stickyData").setStickyValue("即时消息当前界面")
        }
    }
}