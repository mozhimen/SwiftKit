package com.mozhimen.componentk.netk.http.helpers

import com.mozhimen.basick.elemk.commons.ISuspendAB_Listener
import com.mozhimen.basick.elemk.commons.ISuspendA_Listener
import com.mozhimen.basick.elemk.commons.ISuspend_AListener
import com.mozhimen.basick.elemk.mos.MResultIST
import com.mozhimen.basick.utilk.android.util.vt
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.componentk.netk.http.commons.NetKRep
import com.mozhimen.componentk.netk.http.cons.CResCode
import com.mozhimen.underlayk.logk.ket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * @ClassName NetKHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/10/26 11:01
 * @Version 1.0
 */
suspend fun <T> Flow<NetKRep<T>>.asNetKRes(
    onSuccess: ISuspendA_Listener<T>,
    onFail: ISuspendAB_Listener<Int, String>/*onSuccess: suspend (data: R) -> Unit, onFail: suspend (code: Int, msg: String) -> Unit*/
) {
    NetKHelper.asNetRes(this, onSuccess, onFail)
}

suspend fun <T> Flow<NetKRep<T>>.asNetKResSync(): MResultIST<T?> =
    NetKHelper.asNetKResSync(this)

object NetKHelper : BaseUtilK() {
    @JvmStatic
    fun <R> createFlow(invoke: ISuspend_AListener<R?>): Flow<NetKRep<R>> = flow {
        emit(NetKRep.Uninitialized)
        val result: R? = invoke()
        result?.let {
            emit(NetKRep.MSuccess(result))
        } ?: emit(NetKRep.Empty)
    }.onStart {
        emit(NetKRep.Loading)
    }.catch { e ->
        emit(NetKRep.MError(e))
    }.flowOn(Dispatchers.IO)

    @JvmStatic
    suspend fun <T> asNetRes(flow: Flow<NetKRep<T>>, onSuccess: ISuspendA_Listener<T>, onFail: ISuspendAB_Listener<Int, String>) {
        flow.onEach {
            "asNetRes: ${it::class.simpleName}".vt(TAG)
        }.collectLatest {
            when (it) {
                NetKRep.Empty -> onFail(CResCode.Empty, "result is null")
                is NetKRep.MSuccess -> onSuccess(it.data)
                is NetKRep.MError -> {
                    val netKThrowable = NetKRepErrorParser.getThrowable(it.exception)
                    "asNetKRes: Error code ${netKThrowable.code} message ${netKThrowable.message}".ket(TAG)
                    onFail(netKThrowable.code, netKThrowable.message)
                }

                else -> {}
            }
        }
    }

    @JvmStatic
    suspend fun <T> asNetKResSync(flow: Flow<NetKRep<T>>): MResultIST<T?> {
        var res: MResultIST<T?> = MResultIST(CResCode.Empty, null, null)
        flow.onEach {
            "asNetKResSync: ${it::class.simpleName}".vt(TAG)
        }.collectLatest {
            res = when (it) {
                NetKRep.Empty -> MResultIST(CResCode.Empty, "result is null", null)
                is NetKRep.MSuccess -> MResultIST(CResCode.SUCCESS, null, it.data)
                is NetKRep.MError -> {
                    val netKThrowable = NetKRepErrorParser.getThrowable(it.exception)
                    "asNetKResSync: Error code ${netKThrowable.code} message ${netKThrowable.message}".ket(TAG)
                    MResultIST(netKThrowable.code, netKThrowable.message, null)
                }

                else -> MResultIST(CResCode.UNKNOWN, "result is error", null)
            }
        }
        return res
    }
}