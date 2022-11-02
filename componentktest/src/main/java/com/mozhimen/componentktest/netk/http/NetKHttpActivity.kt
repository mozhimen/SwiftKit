package com.mozhimen.componentktest.netk.http

import android.Manifest
import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.APermissionK
import com.mozhimen.componentktest.databinding.ActivityNetkBinding
import com.mozhimen.componentktest.databinding.ActivityNetkHttpBinding

@APermissionK([Manifest.permission.INTERNET])
class NetKHttpActivity : BaseKActivityVBVM<ActivityNetkHttpBinding, NetKHttpViewModel>() {
    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
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