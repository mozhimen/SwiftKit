package com.mozhimen.basicktest.postk

import android.os.Bundle
import com.mozhimen.basick.elemk.androidx.appcompat.bases.databinding.BaseActivityVDB
import com.mozhimen.basick.postk.event.PostKEventLiveData
import com.mozhimen.basick.utilk.android.widget.showToast
import com.mozhimen.basicktest.databinding.ActivityPostkLiveDataEventBusBinding

class PostKLiveDataEventBusActivity : BaseActivityVDB<ActivityPostkLiveDataEventBusBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        PostKEventLiveData.instance.with<String>("stickyData").observeSticky(this) {
            "黏性事件: $it".showToast()
        }

        PostKEventLiveData.instance.with<String>("stickyData").observe(this) {
            "非黏性事件: $it".showToast()
        }

        vdb.utilkDataBusMsgBtn.setOnClickListener {
            PostKEventLiveData.instance.with<String>("stickyData").setStickyValue("即时消息当前界面")
        }
    }
}