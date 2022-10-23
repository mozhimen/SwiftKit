package com.mozhimen.componentktest.netk

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor
import com.mozhimen.componentktest.R
import com.mozhimen.componentktest.databinding.ActivityNetkBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@PermissionKAnnor([Manifest.permission.INTERNET])
class NetKActivity : BaseKActivity<ActivityNetkBinding, NetKViewModel>(R.layout.activity_netk) {
    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
            }
        }
    }

    override fun vbBindVM() {
        vb.vm = vm
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.netkBtnGetWeather.setOnClickListener {
            vm.getRealtimeWeatherAsync()
        }

        vb.netkBtn1GetWeather.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                vm.getRealtimeWeatherCoroutine()
            }
        }

        vb.netkBtn2GetWeather.setOnClickListener {
            vm.getRealTimeWeatherRxJava()
        }

        vb.netkBtn2GetCustom.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
            }
        }
    }
}