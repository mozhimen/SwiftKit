package com.mozhimen.app.componentk.eventk

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.databinding.ActivityDatabuskBinding
import com.mozhimen.componentk.eventk.EventKDataBus

class EventKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityDatabuskBinding.inflate(layoutInflater) }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        EventKDataBus.with<String>("stickyData").observeSticky(this, true) {
            vb.databuskTxtSticky.text = "黏性事件: $it"
        }

        EventKDataBus.with<String>("stickyData").observeSticky(this, false) {
            vb.databuskTxtUnSticky.text = "非黏性事件: $it"
        }

        vb.databuskBtnMsg.setOnClickListener {
            EventKDataBus.with<String>("stickyData").setStickyData("即时消息${(0..10).random()}")
        }
    }
}