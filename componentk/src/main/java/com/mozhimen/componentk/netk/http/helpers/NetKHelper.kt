package com.mozhimen.componentk.netk.http.helpers

import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.componentk.netk.http.commons.NetKRep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * @ClassName NetKHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/26 11:01
 * @Version 1.0
 */
object NetKHelper : BaseUtilK() {
    fun <T> createFlow(invoke: suspend () -> T?): Flow<NetKRep<T>> = flow {
        val result: T? = invoke()
        result?.let {
            emit(NetKRep.MSuccess(result))
        } ?: emit(NetKRep.Empty)
    }.onStart {
        emit(NetKRep.Loading)
    }.catch { e ->
        emit(NetKRep.MError(e))
    }.flowOn(Dispatchers.IO)
}