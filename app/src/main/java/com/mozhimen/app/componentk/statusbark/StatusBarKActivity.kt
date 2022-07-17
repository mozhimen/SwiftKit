package com.mozhimen.app.componentk.statusbark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityStatusbarkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentk.statusbark.StatusBarK
import com.mozhimen.componentk.statusbark.annors.StatusBarKAnnor
import com.mozhimen.componentk.statusbark.annors.StatusBarKType

@StatusBarKAnnor(statusBarType = StatusBarKType.CUSTOM, isFontIconDark = false, bgColorLight = android.R.color.black)
class StatusBarKActivity : BaseKActivity<ActivityStatusbarkBinding, BaseKViewModel>(R.layout.activity_statusbark) {
    override fun initFlag() {
        StatusBarK.initStatusBar(this)
    }
}