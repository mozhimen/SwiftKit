package com.mozhimen.componentktest.netk

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentk.netk.NetKRep
import com.mozhimen.componentk.netk.NetKRepErrorParser
import com.mozhimen.componentktest.netk.customs.ApiFactorys
import com.mozhimen.componentktest.netk.customs.Apis
import com.mozhimen.componentktest.netk.mos.Weather
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @ClassName NetKViewModel
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/11 23:44
 * @Version 1.0
 */
class NetKViewModel : BaseKViewModel() {

    val uiWeather2 = MutableLiveData<String>()
    private var _lastTime2 = System.currentTimeMillis()

//    suspend fun getRealtimeWeatherCoroutine() {
//        _lastTime2 = System.currentTimeMillis()
//        val response: NetKResponse<Weather> = NetKCoroutineHelper().coroutineCall {
//            ApiFactorys.createCoroutine(Apis::class.java).getRealTimeWeatherCoroutine("121.321504,31.194874")
//        }
//        if (response.isSuccessful()) {
//            val duration = System.currentTimeMillis() - _lastTime2
//            Log.i(TAG, "getRealtimeWeatherCoroutine onSuccess duration: $duration")
//            uiWeather2.postValue(response.data?.result?.realtime?.temperature.toString() + " " + duration)
//        } else {
//            Log.e(TAG, "getRealtimeWeatherCoroutine onFail ${response.msg}")
//        }
//    }

    fun getRealtimeWeatherCoroutine() {
        _lastTime2 = System.currentTimeMillis()
        viewModelScope.launch {
            getRealtimeWeatherCoroutineInRep().collect {
                when (it) {
                    is NetKRep.Uninitialized -> {
                        Log.d(TAG, "getRealtimeWeatherCoroutine: Uninitialized")
                    }
                    is NetKRep.Loading -> {
                        Log.d(TAG, "getRealtimeWeatherCoroutine: Loading")
                    }
                    is NetKRep.Success -> {
                        val duration = System.currentTimeMillis() - _lastTime2
                        Log.d(TAG, "getRealtimeWeatherCoroutine: Success time $duration data ${it.data}")
                        uiWeather2.postValue(it.data.result?.realtime?.temperature.toString() + " duration:" + duration)
                    }
                    is NetKRep.Empty -> {
                        Log.d(TAG, "getRealtimeWeatherCoroutine: Empty")
                    }
                    is NetKRep.Error -> {
                        val netKThrowable = NetKRepErrorParser.getThrowable(it.exception)
                        Log.d(TAG, "getRealtimeWeatherCoroutine: Error code ${netKThrowable.code} message ${netKThrowable.message}")
                    }
                }
            }
        }
    }

    private fun getRealtimeWeatherCoroutineInRep() = flow {
        emit(NetKRep.Loading)
        val weather: Weather? = ApiFactorys.netkCoroutineFactory.create(Apis::class.java).getRealTimeWeatherCoroutine("121.321504,31.194874")
        weather?.let {
            emit(NetKRep.Success(weather))
        } ?: emit(NetKRep.Empty)
    }.catch { e ->
        emit(NetKRep.Error(e))
    }

}