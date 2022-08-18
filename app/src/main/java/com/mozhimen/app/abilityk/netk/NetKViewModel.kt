package com.mozhimen.app.abilityk.netk

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mozhimen.abilityk.netk.commons.INetKListener
import com.mozhimen.abilityk.netk.customs.AsyncConverter
import com.mozhimen.abilityk.netk.customs.CoroutineClosure
import com.mozhimen.abilityk.netk.customs.RxJavaResponse
import com.mozhimen.abilityk.netk.mos.NetKResponse
import com.mozhimen.app.abilityk.netk.customs.ApiFactory
import com.mozhimen.app.abilityk.netk.customs.Apis
import com.mozhimen.app.abilityk.netk.customs.ApisTest
import com.mozhimen.app.abilityk.netk.mos.*
import com.mozhimen.basick.basek.BaseKViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @ClassName NetKViewModel
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/11 23:44
 * @Version 1.0
 */
class NetKViewModel : BaseKViewModel() {

    val uiWeather1 = MutableLiveData<String>()
    val uiWeather2 = MutableLiveData<String>()
    val uiWeather3 = MutableLiveData<String>()
    val uiCustom = MutableLiveData<String>()
    private var _lastTime1 = System.currentTimeMillis()
    private var _lastTime2 = System.currentTimeMillis()
    private var _lastTime3 = System.currentTimeMillis()

    fun getRealtimeWeatherAsync() {
        _lastTime1 = System.currentTimeMillis()
        ApiFactory.createAsync(Apis::class.java).getRealTimeWeatherAsync("121.321504,31.194874").enqueue(object : INetKListener<Weather> {
            override fun onSuccess(response: NetKResponse<Weather>) {
                val duration = System.currentTimeMillis() - _lastTime1
                Log.i(TAG, "getRealtimeWeatherAsync onSuccess duration: $duration")
                uiWeather1.postValue(response.data?.result?.realtime?.temperature.toString() + " " + duration)
            }

            override fun onFail(throwable: Throwable) {
                Log.e(TAG, "getRealtimeWeatherAsync onFail ${throwable.message}")
            }
        })
    }

    suspend fun getRealtimeWeatherCoroutine() {
        _lastTime2 = System.currentTimeMillis()
        val response: NetKResponse<Weather> = CoroutineClosure().coroutineCall {
            ApiFactory.createCoroutine(Apis::class.java).getRealTimeWeatherCoroutine("121.321504,31.194874")
        }
        if (response.isSuccessful()) {
            val duration = System.currentTimeMillis() - _lastTime2
            Log.i(TAG, "getRealtimeWeatherCoroutine onSuccess duration: $duration")
            uiWeather2.postValue(response.data?.result?.realtime?.temperature.toString() + " " + duration)
        } else {
            Log.e(TAG, "getRealtimeWeatherCoroutine onFail ${response.msg}")
        }
    }

    fun getRealTimeWeatherRxJava() {
        _lastTime3 = System.currentTimeMillis()
        ApiFactory.createRxJava(Apis::class.java).getRealTimeWeatherRxJava("121.321504,31.194874").subscribeOn(Schedulers.io()).subscribe(
            object : RxJavaResponse<Weather>() {
                override fun onSuccess(response: NetKResponse<Weather>) {
                    val duration = System.currentTimeMillis() - _lastTime3
                    Log.i(TAG, "getRealTimeWeatherRxJava onSuccess duration $duration")
                    uiWeather3.postValue(response.data?.result?.realtime?.temperature.toString() + " " + duration)
                }

                override fun onFailed(code: Int, message: String?) {
                    Log.e(TAG, "getRealTimeWeatherRxJava onFail ${message ?: "msg loss"}")
                }
            }
        )
    }


    suspend fun getRingSuHealthStatus(personCode: String, equipNo: String, timeRange: Int) {
        val lingXiReq = LingXiReq(personCode, equipNo, timeRange)
        val response: NetKResponse<LingXiRes> = CoroutineClosure(AsyncConverter(LingXiConstants.LingXiCode.SUCCESS.code)).coroutineCallWidthCommon {
            ApiFactory.createCoroutineTest(ApisTest::class.java).getRingSuHealthStatus(lingXiReq)//NetKCommon<LingXiRes>
        }
        if (response.isSuccessful()) {
            uiCustom.postValue(response.data.toString())
        } else {
            Log.e(TAG, "getRingSuHealthStatus onFail msg ${response.msg} error msg ${response.errorData}")
        }
    }

}