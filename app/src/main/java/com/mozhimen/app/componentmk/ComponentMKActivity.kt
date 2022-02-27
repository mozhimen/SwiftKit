package com.mozhimen.app.componentmk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.componentmk.basemk.BaseMKDemoActivity
import com.mozhimen.basicsmk.databusmk.DataBusMK

class ComponentMKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_componentmk)

        //黏性事件测试
        DataBusMK.with<String>("stickyData").setStickyData("stickyData from ComponentMKActivity")
    }

    fun goBaseMKDemoActivity(view: View) {
        startActivity(Intent(this, BaseMKDemoActivity::class.java))
    }
}