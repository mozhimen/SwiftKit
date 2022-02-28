package com.mozhimen.app.basicsmk.databusmk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.databinding.ActivityDatabusmkBinding
import com.mozhimen.basicsmk.databusmk.DataBusMK

class DataBusMKActivity : AppCompatActivity() {
    private val vb by lazy { ActivityDatabusmkBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)

        DataBusMK.with<String>("stickyData").observeSticky(this, true, {
            vb.databusmkTxtSticky.text = "黏性事件: $it"
        })

        DataBusMK.with<String>("stickyData").observeSticky(this, false, {
            vb.databusmkTxtUnSticky.text = "非黏性事件: $it"
        })

        vb.databusmkBtnMsg.setOnClickListener {
            DataBusMK.with<String>("stickyData").setStickyData("即时消息${(0..10).random()}")
        }
    }
}