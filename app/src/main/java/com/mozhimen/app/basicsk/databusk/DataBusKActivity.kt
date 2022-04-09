package com.mozhimen.app.basicsk.databusk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.databinding.ActivityDatabuskBinding
import com.mozhimen.basicsk.databusk.DataBusK

class DataBusKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityDatabuskBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        DataBusK.with<String>("stickyData").observeSticky(this, true, {
            vb.databuskTxtSticky.text = "黏性事件: $it"
        })

        DataBusK.with<String>("stickyData").observeSticky(this, false, {
            vb.databuskTxtUnSticky.text = "非黏性事件: $it"
        })

        vb.databuskBtnMsg.setOnClickListener {
            DataBusK.with<String>("stickyData").setStickyData("即时消息${(0..10).random()}")
        }
    }
}