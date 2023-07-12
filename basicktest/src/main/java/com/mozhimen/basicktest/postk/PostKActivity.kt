package com.mozhimen.basicktest.postk

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.postk.livedata.PostKLiveDataEventBus
import com.mozhimen.basick.utilk.android.content.startContext
import com.mozhimen.basicktest.databinding.ActivityPostkBinding

class PostKActivity : BaseActivityVB<ActivityPostkBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        PostKLiveDataEventBus.with<String>("stickyData").setStickyValue("即时消息主界面")
        super.initData(savedInstanceState)
    }

    fun goLiveDataEventBus(view: View) {
        startContext<PostKLiveDataEventBusActivity>()
    }
}