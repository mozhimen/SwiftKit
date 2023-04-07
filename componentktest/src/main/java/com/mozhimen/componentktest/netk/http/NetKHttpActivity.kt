package com.mozhimen.componentktest.netk.http

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVBVM
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.componentktest.databinding.ActivityNetkHttpBinding

@AManifestKRequire(CPermission.INTERNET, CApplication.USES_CLEAR_TEXT_TRAFFIC_TRUE)
@APermissionCheck(CPermission.INTERNET)
class NetKHttpActivity : BaseActivityVBVM<ActivityNetkHttpBinding, NetKHttpViewModel>() {
    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            }
        }
    }

    override fun bindViewVM(vb: ActivityNetkHttpBinding) {
        Log.d(TAG, "bindViewVM: ")
        vb.vm = vm
    }

    override fun initView(savedInstanceState: Bundle?) {

        VB.netkBtn1GetWeather.setOnClickListener {
            vm.getRealtimeWeatherCoroutine()
        }
    }
}