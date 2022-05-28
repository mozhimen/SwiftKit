package com.mozhimen.app.componentk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.componentk.eventk.EventKActivity
import com.mozhimen.app.componentk.guidek.GuideKActivity
import com.mozhimen.app.componentk.permissionk.PermissionKActivity
import com.mozhimen.basick.extsk.start

class ComponentKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_componentk)

        //黏性事件测试
        //DataBusK.with<String>("stickyData").setStickyData("stickyData from ComponentKActivity")
    }

    fun goGuideKActivity(view: View) {
        start<GuideKActivity>()
    }

    fun goPermissionKActivity(view: View) {
        start<PermissionKActivity>()
    }

    fun goEventKActivity(view: View) {
        start<EventKActivity>()
    }
}