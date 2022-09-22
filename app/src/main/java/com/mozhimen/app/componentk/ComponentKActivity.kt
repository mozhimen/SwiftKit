package com.mozhimen.app.componentk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mozhimen.app.R
import com.mozhimen.app.basick.eventk.EventKActivity
import com.mozhimen.app.componentk.debugk.DebugKActivity
import com.mozhimen.app.componentk.guidek.GuideKActivity
import com.mozhimen.app.componentk.permissionk.PermissionKActivity
import com.mozhimen.app.componentk.statusbark.StatusBarKActivity
import com.mozhimen.basick.extsk.start

class ComponentKActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_componentk)
    }

    fun goGuideK(view: View) {
        start<GuideKActivity>()
    }

    fun goPermissionK(view: View) {
        start<PermissionKActivity>()
    }

    fun goStatusBarK(view: View) {
        start<StatusBarKActivity>()
    }

    fun goDebugK(view: View) {
        start<DebugKActivity>()
    }
}