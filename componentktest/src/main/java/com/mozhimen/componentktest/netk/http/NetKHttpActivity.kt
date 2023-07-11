package com.mozhimen.componentktest.netk.http

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVBVM
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.componentktest.databinding.ActivityNetkHttpBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AManifestKRequire(CPermission.INTERNET, CApplication.USES_CLEAR_TEXT_TRAFFIC_TRUE)
@APermissionCheck(CPermission.INTERNET)
class NetKHttpActivity : BaseActivityVBVM<ActivityNetkHttpBinding, NetKHttpViewModel>() {
    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.requestPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            }
        }
    }

    override fun bindViewVM(vb: ActivityNetkHttpBinding) {
        Log.d(TAG, "bindViewVM: ")
        vb.vm = vm
    }

    @SuppressLint("SetTextI18n")
    override fun initView(savedInstanceState: Bundle?) {

        vb.netkBtn1GetWeather.setOnClickListener {
            vm.getRealtimeWeatherCoroutine()
        }

        vb.netkBtn2GetWeather.setOnClickListener {
            val time = System.currentTimeMillis()
            lifecycleScope.launch(Dispatchers.IO) {
                vm.getRealtimeWeatherCoroutineSync().bean?.let { bean ->
                    withContext(Dispatchers.Main) {
                        vb.netkTxt2.text = bean.result.realtime.temperature.toString() + " ${System.currentTimeMillis() - time}"
                    }
                }
            }
        }
    }
}