package com.mozhimen.componentktest

import android.view.View
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.start
import com.mozhimen.componentktest.databinding.ActivityComponentkBinding
import com.mozhimen.componentktest.debugk.DebugKActivity
import com.mozhimen.componentktest.guidek.GuideKActivity
import com.mozhimen.componentktest.permissionk.PermissionKActivity
import com.mozhimen.componentktest.statusbark.StatusBarKActivity

class ComponentKActivity : BaseKActivity<ActivityComponentkBinding, BaseKViewModel>(R.layout.activity_componentk) {

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