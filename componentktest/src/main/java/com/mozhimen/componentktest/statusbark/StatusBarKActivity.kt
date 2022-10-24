package com.mozhimen.componentktest.statusbark

import android.os.Bundle
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.componentk.statusbark.StatusBarK
import com.mozhimen.componentk.statusbark.annors.StatusBarKAnnor
import com.mozhimen.componentk.statusbark.annors.StatusBarKType
import com.mozhimen.componentktest.databinding.ActivityStatusbarkBinding

@StatusBarKAnnor(statusBarType = StatusBarKType.CUSTOM, isFontIconDark = false, bgColorLight = android.R.color.black)
class StatusBarKActivity : BaseKActivityVB<ActivityStatusbarkBinding>() {
    override fun initFlag() {
        StatusBarK.initStatusBar(this)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}