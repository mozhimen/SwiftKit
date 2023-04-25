package com.mozhimen.componentktest

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.startContext
import com.mozhimen.componentktest.audiok.AudioKActivity
import com.mozhimen.componentktest.camerak.CameraKActivity
import com.mozhimen.componentktest.cameraxk.CameraXKActivity
import com.mozhimen.componentktest.databinding.ActivityComponentkBinding
import com.mozhimen.componentktest.installk.InstallKActivity
import com.mozhimen.componentktest.navigatek.NavigateKActivity
import com.mozhimen.componentktest.netk.NetKActivity
import com.mozhimen.componentktest.videok.VideoKActivity

class ComponentKActivity : BaseActivityVB<ActivityComponentkBinding>() {

    fun goAudioK(view: View) {
        startContext<AudioKActivity>()
    }

    fun goCameraK(view: View) {
        startContext<CameraKActivity>()
    }

    fun goCameraXK(view: View) {
        startContext<CameraXKActivity>()
    }

    fun goInstallK(view: View) {
        startContext<InstallKActivity>()
    }

    fun goNavigateK(view: View) {
        startContext<NavigateKActivity>()
    }

    fun goNetK(view: View) {
        startContext<NetKActivity>()
    }

    fun goVideoK(view: View) {
        startContext<VideoKActivity>()
    }
}