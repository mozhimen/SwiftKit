package com.mozhimen.app.basick.eventk

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.databinding.ActivityEventkBinding
import com.mozhimen.basick.eventk.EventKDataBus

class EventKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityEventkBinding.inflate(layoutInflater) }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

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