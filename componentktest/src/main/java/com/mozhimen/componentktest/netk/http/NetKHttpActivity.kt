package com.mozhimen.componentktest.netk.http

import android.Manifest
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVBVM
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.componentktest.databinding.ActivityNetkHttpBinding

@APermissionK([Manifest.permission.INTERNET])
class NetKHttpActivity : BaseActivityVBVM<ActivityNetkHttpBinding, NetKHttpViewModel>() {
    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
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

        vb.netkBtn1GetWeather.setOnClickListener {
            vm.getRealtimeWeatherCoroutine()
        }
    }
}