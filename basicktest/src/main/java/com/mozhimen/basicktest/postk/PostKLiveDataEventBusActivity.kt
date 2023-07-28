package com.mozhimen.basicktest.postk

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.postk.event.PostKEventLiveData
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityPostkLiveDataEventBusBinding

class PostKLiveDataEventBusActivity : BaseActivityVB<ActivityPostkLiveDataEventBusBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        PostKEventLiveData.with<String>("stickyData").observeSticky(this) {
            "黏性事件: $it".showToast()
        }

        PostKEventLiveData.with<String>("stickyData").observe(this) {
            "非黏性事件: $it".showToast()
        }

        vb.utilkDataBusMsgBtn.setOnClickListener {
            PostKEventLiveData.with<String>("stickyData").setStickyValue("即时消息当前界面")
        }
    }
}