package com.mozhimen.componentktest.netk.http

import android.os.Bundle
import android.util.Log
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVBVM
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionKRequire
import com.mozhimen.basick.permissionk.cons.CApplication
import com.mozhimen.componentktest.databinding.ActivityNetkHttpBinding

@APermissionKRequire(CPermission.INTERNET, CApplication.USES_CLEAR_TEXT_TRAFFIC_TRUE)
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