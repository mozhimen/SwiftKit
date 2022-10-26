package com.mozhimen.componentk.netk.helpers

import com.mozhimen.componentk.netk.mos.NetKRep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * @ClassName NetKHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/26 11:01
 * @Version 1.0
 */
object NetKHelper {
    const val TAG = "NetKHelper>>>>>"

//    suspend fun <T> get(call: suspend () -> T): T? = suspendCancellableCoroutine { coroutine->
//        val currentTime = System.currentTimeMillis()
//        createFlow(call).collect {
//            when (it) {
//                is NetKRep.Uninitialized -> {
//                }
//                is NetKRep.Loading -> {
//                }
//                is NetKRep.Success -> {
//                    Log.d(TAG, "getRealtimeWeatherCoroutine: Success time ${System.currentTimeMillis() - currentTime} data ${it.data}")
//                    coroutine.resume(it.data)
//                }
//                is NetKRep.Empty -> {
//                    Log.d(TAG, "getRealtimeWeatherCoroutine: Empty")
//                    coroutine.resume(null)
//                }
//                is NetKRep.Error -> {
//                    val netKThrowable = NetKRepErrorParser.getThrowable(it.exception)
//                    Log.d(TAG, "getRealtimeWeatherCoroutine: Error code ${netKThrowable.code} message ${netKThrowable.message}")
//                    coroutine.resume(null)
//                }
//            }
//        }
//    }

    fun <T> createFlow(call: suspend () -> T?): Flow<NetKRep<T>> = flow {
        val result: T? = call()
        result?.let {
            emit(NetKRep.Success(result))
        } ?: emit(NetKRep.Empty)
    }.onStart {
        emit(NetKRep.Loading)
    }.catch { e ->
        emit(NetKRep.Error(e))
    }.flowOn(Dispatchers.IO)
}