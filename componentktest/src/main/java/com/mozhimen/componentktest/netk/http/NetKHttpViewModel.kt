package com.mozhimen.componentktest.netk.http

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mozhimen.basick.elemk.mos.MResultIST
import com.mozhimen.basick.elemk.androidx.lifecycle.bases.BaseViewModel
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.componentk.netk.http.helpers.NetKHelper
import com.mozhimen.componentk.netk.http.helpers.asNetKRes
import com.mozhimen.componentk.netk.http.helpers.asNetKResSync
import com.mozhimen.componentktest.netk.http.customs.ApiFactory
import com.mozhimen.componentktest.netk.http.mos.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * @ClassName NetKViewModel
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/11 23:44
 * @Version 1.0
 */
@AManifestKRequire(CPermission.INTERNET, CApplication.USES_CLEAR_TEXT_TRAFFIC_TRUE)
class NetKHttpViewModel : BaseViewModel() {

    val uiWeather2 = MutableLiveData("")

    fun getRealtimeWeatherCoroutine() {
        val time = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.IO) {
            NetKHelper.createFlow { ApiFactory.apis.getRealTimeWeatherCoroutine("121.321504,31.194874") }.asNetKRes(
                onSuccess = { data ->
                    uiWeather2.postValue(data.result.realtime.temperature.toString() + " ${System.currentTimeMillis() - time}")
                }, onFail = { code, msg ->
                    uiWeather2.postValue("$code $msg ${System.currentTimeMillis() - time}")
                })
        }
    }

    suspend fun getRealtimeWeatherCoroutine1(): String = suspendCancellableCoroutine { coroutine ->
        viewModelScope.launch(Dispatchers.IO) {
            val time = System.currentTimeMillis()
            NetKHelper.createFlow { ApiFactory.apis.getRealTimeWeatherCoroutine("121.321504,31.194874") }.asNetKRes(
                onSuccess = { data ->
                    coroutine.resume(data.result.realtime.temperature.toString() + " ${System.currentTimeMillis() - time}")
                }, onFail = { code, msg ->
                    coroutine.resume("$code $msg ${System.currentTimeMillis() - time}")
                })
        }
    }

    suspend fun getRealtimeWeatherCoroutineSync(): MResultIST<Weather?> =
        NetKHelper.createFlow { ApiFactory.apis.getRealTimeWeatherCoroutine("121.321504,31.194874") }.asNetKResSync()
}