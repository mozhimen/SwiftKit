package com.mozhimen.componentk.netk

import android.util.Log
import com.mozhimen.componentk.netk.helpers.NetKHelper
import com.mozhimen.componentk.netk.helpers.NetKRepErrorParser
import com.mozhimen.componentk.netk.mos.NetKRep
import kotlinx.coroutines.flow.Flow

/**
 * @ClassName NetKExts
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/26 13:19
 * @Version 1.0
 */
suspend fun <T> Flow<NetKRep<T>>.asNetKRes(onSuccess: (data: T) -> Unit, onFail: (code: Int, msg: String) -> Unit) {
    collect {
        when (it) {
            is NetKRep.Uninitialized -> {
                Log.d(NetKHelper.TAG, "getRealtimeWeatherCoroutine: Uninitialized")
            }
            is NetKRep.Loading -> {
                Log.d(NetKHelper.TAG, "getRealtimeWeatherCoroutine: Loading")
            }
            is NetKRep.Success -> {
                Log.d(NetKHelper.TAG, "getRealtimeWeatherCoroutine: Success data ${it.data}")
                onSuccess(it.data)
            }
            is NetKRep.Empty -> {
                Log.d(NetKHelper.TAG, "getRealtimeWeatherCoroutine: Empty")
                onFail(-1, "result is null")
            }
            is NetKRep.Error -> {
                val netKThrowable = NetKRepErrorParser.getThrowable(it.exception)
                Log.d(NetKHelper.TAG, "getRealtimeWeatherCoroutine: Error code ${netKThrowable.code} message ${netKThrowable.message}")
                onFail(netKThrowable.code, netKThrowable.message)
            }
        }
    }
}