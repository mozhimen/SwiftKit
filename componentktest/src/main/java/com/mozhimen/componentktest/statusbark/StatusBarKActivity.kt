package com.mozhimen.componentktest.statusbark

import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentk.statusbark.StatusBarK
import com.mozhimen.componentk.statusbark.annors.StatusBarKAnnor
import com.mozhimen.componentk.statusbark.annors.StatusBarKType
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityStatusbarkBinding

@StatusBarKAnnor(statusBarType = StatusBarKType.CUSTOM, isFontIconDark = false, bgColorLight = android.R.color.black)
class StatusBarKActivity : BaseKActivity<ActivityStatusbarkBinding, BaseKViewModel>(R.layout.activity_statusbark) {
    override fun initFlag() {
        StatusBarK.initStatusBar(this)
    }
}