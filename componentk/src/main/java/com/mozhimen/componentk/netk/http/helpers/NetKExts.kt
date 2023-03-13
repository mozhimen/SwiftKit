package com.mozhimen.componentk.netk.http.helpers

import android.util.Log
import com.mozhimen.componentk.netk.http.commons.NetKRep
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

/**
 * @ClassName NetKExts
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/26 13:19
 * @Version 1.0
 */
suspend fun <T> Flow<NetKRep<T>>.asNetKRes(onSuccess: suspend (data: T) -> Unit, onFail: suspend (code: Int, msg: String) -> Unit) {
    collect {
        when (it) {
            is NetKRep.Uninitialized -> {
                Log.v(NetKHelper.TAG, "asNetKRes: Uninitialized")
            }
            is NetKRep.Loading -> {
                Log.v(NetKHelper.TAG, "asNetKRes: Loading")
            }
            is NetKRep.MSuccess -> {
                Log.v(NetKHelper.TAG, "asNetKRes: Success data ${it.data}")
                onSuccess(it.data)
            }
            is NetKRep.Empty -> {
                Log.v(NetKHelper.TAG, "asNetKRes: Empty")
                onFail(-1, "result is null")
            }
            is NetKRep.MError -> {
                val netKThrowable = NetKRepErrorParser.getThrowable(it.exception)
                Log.v(NetKHelper.TAG, "asNetKRes: Error code ${netKThrowable.code} message ${netKThrowable.message}")
                onFail(netKThrowable.code, netKThrowable.message)
            }
        }
    }
}