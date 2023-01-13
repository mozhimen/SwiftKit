package com.mozhimen.componentktest

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.componentktest.audiok.AudioKActivity
import com.mozhimen.componentktest.cameraxk.CameraXKActivity
import com.mozhimen.componentktest.databinding.ActivityComponentkBinding
import com.mozhimen.componentktest.installk.InstallKActivity
import com.mozhimen.componentktest.navigatek.NavigateKActivity
import com.mozhimen.componentktest.netk.NetKActivity

class ComponentKActivity : BaseActivityVB<ActivityComponentkBinding>() {

    fun goAudioK(view: View) {
        start<AudioKActivity>()
    }

    fun goCameraXK(view: View) {
        start<CameraXKActivity>()
    }

    fun goInstallK(view: View) {
        start<InstallKActivity>()
    }

    fun goNetK(view: View) {
        start<NetKActivity>()
    }

    fun goNavigateK(view: View) {
        start<NavigateKActivity>()
    }

}