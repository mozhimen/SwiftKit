package com.mozhimen.componentk.netk.helpers

import com.mozhimen.componentk.netk.commons.NetKRep
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

    fun <T> createFlow(call: suspend () -> T?): Flow<NetKRep<T>> = flow {
        val result: T? = call()
        result?.let {
            emit(NetKRep.MSuccess(result))
        } ?: emit(NetKRep.Empty)
    }.onStart {
        emit(NetKRep.Loading)
    }.catch { e ->
        emit(NetKRep.MError(e))
    }.flowOn(Dispatchers.IO)
}