package com.mozhimen.basicktest.statusbark

import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.statusbark.StatusBarK
import com.mozhimen.basick.statusbark.annors.AStatusBarK
import com.mozhimen.basick.statusbark.annors.AStatusBarKType
import com.mozhimen.basicktest.databinding.ActivityStatusbarkBinding

@AStatusBarK(statusBarType = AStatusBarKType.CUSTOM, isFontIconDark = false, bgColorLight = android.R.color.black)
class StatusBarKActivity : BaseActivityVB<ActivityStatusbarkBinding>() {
    override fun initFlag() {
        StatusBarK.initStatusBar(this)
    }
}