package com.mozhimen.app.abilityk.netk

import android.Manifest
import android.os.Bundle
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityNetkBinding
import com.mozhimen.basicsk.basek.BaseKActivity
import com.mozhimen.basicsk.permissionk.PermissionK
import com.mozhimen.basicsk.permissionk.annors.PermissionKAnnor

@PermissionKAnnor([Manifest.permission.INTERNET])
class NetKActivity : BaseKActivity<ActivityNetkBinding, NetKViewModel>(R.layout.activity_netk) {
    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
            }
        }
    }

    override fun injectVM() {
        vb.vm = vm
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.netkBtnGetWeather.setOnClickListener {
            vm.getRealtimeWeatherAsync()
        }

        vb.netkBtn1GetWeather.setOnClickListener {
            vm.getRealtimeWeatherCoroutine()
        }

        vb.netkBtn2GetWeather.setOnClickListener {
            vm.getRealTimeWeatherRxJava()
        }
    }
}