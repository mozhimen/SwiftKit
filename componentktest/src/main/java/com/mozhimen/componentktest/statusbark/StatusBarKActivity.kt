package com.mozhimen.componentktest.statusbark

import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.componentk.statusbark.StatusBarK
import com.mozhimen.componentk.statusbark.annors.AStatusBarK
import com.mozhimen.componentk.statusbark.annors.AStatusBarKType
import com.mozhimen.componentktest.databinding.ActivityStatusbarkBinding

@AStatusBarK(statusBarType = AStatusBarKType.CUSTOM, isFontIconDark = false, bgColorLight = android.R.color.black)
class StatusBarKActivity : BaseActivityVB<ActivityStatusbarkBinding>() {
    override fun initFlag() {
        StatusBarK.initStatusBar(this)
    }
}