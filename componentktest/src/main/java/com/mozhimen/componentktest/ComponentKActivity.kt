package com.mozhimen.componentktest

import android.os.Bundle
import android.view.View
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.extsk.start
import com.mozhimen.componentktest.audiok.AudioKActivity
import com.mozhimen.componentktest.cameraxk.CameraXKActivity
import com.mozhimen.componentktest.databinding.ActivityComponentkBinding
import com.mozhimen.componentktest.navigatek.NavigateKActivity
import com.mozhimen.componentktest.netk.http.NetKHttpActivity
import com.mozhimen.componentktest.permissionk.PermissionKActivity
import com.mozhimen.componentktest.statusbark.StatusBarKActivity

class ComponentKActivity : BaseKActivityVB<ActivityComponentkBinding>() {

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
        start<NetKHttpActivity>()
    }

    fun goNavigateK(view: View) {
        start<NavigateKActivity>()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }
}