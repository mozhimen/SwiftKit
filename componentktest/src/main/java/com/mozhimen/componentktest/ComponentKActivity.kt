package com.mozhimen.componentktest

import android.view.View
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.start
import com.mozhimen.componentktest.audiok.AudioKActivity
import com.mozhimen.componentktest.cameraxk.CameraXKActivity
import com.mozhimen.componentktest.databinding.ActivityComponentkBinding
import com.mozhimen.componentktest.navigatek.NavigateKActivity
import com.mozhimen.componentktest.netk.NetKActivity
import com.mozhimen.componentktest.permissionk.PermissionKActivity
import com.mozhimen.componentktest.statusbark.StatusBarKActivity

class ComponentKActivity : BaseActivityVB<ActivityComponentkBinding>() {

    fun goAudioK(view: View){
        start<AudioKActivity>()
    }

    fun goCameraXK(view: View) {
        start<CameraXKActivity>()
    }

    fun goPermissionK(view: View) {
        start<PermissionKActivity>()
    }

    fun goStatusBarK(view: View) {
        start<StatusBarKActivity>()
    }

    fun goNetK(view: View) {
        start<NetKActivity>()
    }

    fun goNavigateK(view: View) {
        start<NavigateKActivity>()
    }

}