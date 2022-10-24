package com.mozhimen.componentktest.netk

import androidx.lifecycle.viewModelScope
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.componentk.netk.coroutine.NetKRep
import com.mozhimen.componentktest.netk.customs.ApiFactorys
import com.mozhimen.componentktest.netk.customs.Apis
import com.mozhimen.componentktest.netk.mos.Weather
import kotlinx.coroutines.Dispatchers
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

//    val uiWeather1 = MutableLiveData<String>()
//    val uiWeather2 = MutableLiveData<String>()
//    val uiWeather3 = MutableLiveData<String>()
//    val uiCustom = MutableLiveData<String>()
//    private var _lastTime1 = System.currentTimeMillis()
//    private var _lastTime2 = System.currentTimeMillis()
//    private var _lastTime3 = System.currentTimeMillis()

//    fun getRealtimeWeatherAsync() {
//        _lastTime1 = System.currentTimeMillis()
//        ApiFactorys.createAsync(Apis::class.java).getRealTimeWeatherAsync("121.321504,31.194874").enqueue(object : INetKListener<Weather> {
//            override fun onSuccess(response: NetKResponse<Weather>) {
//                val duration = System.currentTimeMillis() - _lastTime1
//                Log.i(TAG, "getRealtimeWeatherAsync onSuccess duration: $duration")
//                uiWeather1.postValue(response.data?.result?.realtime?.temperature.toString() + " " + duration)
//            }
//
//            override fun onFail(throwable: Throwable) {
//                Log.e(TAG, "getRealtimeWeatherAsync onFail ${throwable.message}")
//            }
//        })
//    }

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
        viewModelScope.launch {
            getRealtimeWeatherCoroutineInRep().collect {
                when (it) {

                }
            }
        }
    }

    fun getRealtimeWeatherCoroutineInRep() = flow {
        emit(NetKRep.Loading)
        val weather = ApiFactorys.netkCoroutineFactory.create(Apis::class.java).getRealTimeWeatherCoroutine("121.321504,31.194874")
        emit(NetKRep.Success(weather))
    }
        .flowOn(Dispatchers.IO)
        .catch { e ->
            emit(NetKRep.Error(e))
        }

//    fun getRealTimeWeatherRxJava() {
//        _lastTime3 = System.currentTimeMillis()
//        ApiFactorys.createRxJava(Apis::class.java).getRealTimeWeatherRxJava("121.321504,31.194874").subscribeOn(Schedulers.io()).subscribe(
//            object : RxJavaResponse<Weather>() {
//                override fun onSuccess(response: NetKResponse<Weather>) {
//                    val duration = System.currentTimeMillis() - _lastTime3
//                    Log.i(TAG, "getRealTimeWeatherRxJava onSuccess duration $duration")
//                    uiWeather3.postValue(response.data?.result?.realtime?.temperature.toString() + " " + duration)
//                }
//
//                override fun onFailed(code: Int, message: String?) {
//                    Log.e(TAG, "getRealTimeWeatherRxJava onFail ${message ?: "msg loss"}")
//                }
//            }
//        )
//    }
}