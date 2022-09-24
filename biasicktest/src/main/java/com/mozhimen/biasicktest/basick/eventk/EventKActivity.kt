package com.mozhimen.biasicktest.basick.eventk

import android.annotation.SuppressLint
import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.eventk.EventKDataBus
import com.mozhimen.biasicktest.R
import com.mozhimen.biasicktest.databinding.ActivityEventkBinding

class EventKActivity : BaseKActivity<ActivityEventkBinding, BaseKViewModel>(R.layout.activity_eventk) {

    @SuppressLint("SetTextI18n")
    override fun initData(savedInstanceState: Bundle?) {
        EventKDataBus.with<String>("stickyData").observeSticky(this, true) {
            vb.eventkTxtSticky.text = "黏性事件: $it"
        }

        EventKDataBus.with<String>("stickyData").observeSticky(this, false) {
            vb.eventkTxtUnSticky.text = "非黏性事件: $it"
        }

        vb.eventkBtnMsg.setOnClickListener {
            EventKDataBus.with<String>("stickyData").setStickyData("即时消息${(0..10).random()}")
        }
    }
}