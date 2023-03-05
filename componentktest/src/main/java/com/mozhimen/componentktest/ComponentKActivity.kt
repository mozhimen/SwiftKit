package com.mozhimen.componentktest

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
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
        start<AudioKActivity>()
    }

    fun goCameraK(view: View) {
        start<CameraKActivity>()
    }

    fun goCameraXK(view: View) {
        start<CameraXKActivity>()
    }

    fun goInstallK(view: View) {
        start<InstallKActivity>()
    }

    fun goNavigateK(view: View) {
        start<NavigateKActivity>()
    }

    fun goNetK(view: View) {
        start<NetKActivity>()
    }

    fun goVideoK(view: View) {
        start<VideoKActivity>()
    }
}