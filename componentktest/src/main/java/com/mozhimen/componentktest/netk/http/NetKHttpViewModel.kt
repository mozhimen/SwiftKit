package com.mozhimen.componentktest.netk.http

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentk.netk.http.helpers.NetKHelper
import com.mozhimen.componentk.netk.http.helpers.asNetKRes
import com.mozhimen.componentktest.netk.http.customs.ApiFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName NetKViewModel
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/11 23:44
 * @Version 1.0
 */
class NetKHttpViewModel : BaseKViewModel() {

    val uiWeather2 = MutableLiveData("")

    fun getRealtimeWeatherCoroutine() {
        val time = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.IO) {
            NetKHelper.createFlow { ApiFactory.api.getRealTimeWeatherCoroutine("121.321504,31.194874") }.asNetKRes(
                onSuccess = { data ->
                    uiWeather2.postValue(data.result.realtime.temperature.toString() + " ${System.currentTimeMillis() - time}")
                }, onFail = { code, msg ->
                    uiWeather2.postValue("$code $msg ${System.currentTimeMillis() - time}")
                })
        }
    }
}