package com.mozhimen.componentk.netk.coroutine

import com.mozhimen.basick.extsk.toJson
import com.mozhimen.basick.utilk.UtilKJson
import com.mozhimen.componentk.netk.async.commons.INetKConverter
import com.mozhimen.componentk.netk.async.customs.AsyncConverter
import com.mozhimen.componentk.netk.async.helpers.NetKThrowable
import com.mozhimen.componentk.netk.async.helpers.StatusParser
import com.mozhimen.componentk.netk.async.mos.NetKResponse
import com.mozhimen.underlayk.logk.LogK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @ClassName CoroutineClosure
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 22:55
 * @Version 1.0
 */
class NetKCoroutineHelper(val converter: INetKConverter = AsyncConverter()) {
    val TAG = "CoroutineConverter>>>>>"

    suspend inline fun <reified T> coroutineCall(crossinline call: suspend CoroutineScope.() -> T): NetKResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                parseResponse(call())
            } catch (e: Throwable) {
                e.printStackTrace()
                LogK.et(TAG, "coroutineCall Throwable ${e.message ?: ""}")
                StatusParser.getThrowable(e).toResponse()
            }
        }
    }

    inline fun <reified T> parseResponse(response: T): NetKResponse<T> {
        val rawData: String = response!!::class.toJson()
        return converter.convert(rawData, T::class.java)
    }

    fun <T> NetKThrowable.toResponse(): NetKResponse<T> {
        val response = NetKResponse<T>()
        response.code = this.code
        response.msg = this.message
        return response
    }
}

