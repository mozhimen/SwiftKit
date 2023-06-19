package com.mozhimen.componentk.netk.http.helpers

import android.util.Log
import com.mozhimen.basick.elemk.mos.MResultBST
import com.mozhimen.basick.elemk.mos.MResultIST
import com.mozhimen.componentk.netk.http.commons.NetKRep
import com.mozhimen.componentk.netk.http.cons.CResCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.suspendCancellableCoroutine

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
            NetKRep.Uninitialized -> {
                Log.v(NetKHelper.TAG, "asNetKRes: Uninitialized")
            }

            NetKRep.Loading -> {
                Log.v(NetKHelper.TAG, "asNetKRes: Loading")
            }

            NetKRep.Empty -> {
                Log.v(NetKHelper.TAG, "asNetKRes: Empty")
                onFail(-1, "result is null")
            }

            is NetKRep.MSuccess -> {
                Log.v(NetKHelper.TAG, "asNetKRes: Success data ${it.data}")
                onSuccess(it.data)
            }

            is NetKRep.MError -> {
                val netKThrowable = NetKRepErrorParser.getThrowable(it.exception)
                Log.v(NetKHelper.TAG, "asNetKRes: Error code ${netKThrowable.code} message ${netKThrowable.message}")
                onFail(netKThrowable.code, netKThrowable.message)
            }
        }
    }
}

suspend fun <T> Flow<NetKRep<T>>.asNetKResSync(): MResultIST<T?> {
    var res: MResultIST<T?> = MResultIST(CResCode.Empty, null, null)
    onEach {
        Log.v(NetKHelper.TAG, "asNetKResSync: " + it::class.simpleName)
    }.collectLatest {
        when (it) {
            is NetKRep.MSuccess -> {
                res = MResultIST(CResCode.SUCCESS, null, it.data)
            }

            NetKRep.Empty -> {
                res = MResultIST(CResCode.Empty, "result is null", null)
            }

            is NetKRep.MError -> {
                val netKThrowable = NetKRepErrorParser.getThrowable(it.exception)
                Log.e(NetKHelper.TAG, "asNetKResSync: Error code ${netKThrowable.code} message ${netKThrowable.message}")
                res = MResultIST(netKThrowable.code, netKThrowable.message, null)
            }

            else -> {
                res = MResultIST(CResCode.UNKNOWN, "result is error", null)
            }
        }
    }
    return res
}