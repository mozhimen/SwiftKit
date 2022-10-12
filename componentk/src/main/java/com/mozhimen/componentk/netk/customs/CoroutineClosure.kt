package com.mozhimen.componentk.netk.customs

import com.mozhimen.basick.utilk.UtilKJson
import com.mozhimen.componentk.netk.commons.INetKConverter
import com.mozhimen.componentk.netk.helpers.NetKThrowable
import com.mozhimen.componentk.netk.helpers.StatusParser
import com.mozhimen.componentk.netk.mos.NetKCommon
import com.mozhimen.componentk.netk.mos.NetKResponse
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
class CoroutineClosure(val converter: INetKConverter = AsyncConverter()) {
    val TAG = "CoroutineConverter>>>>>"

    suspend inline fun <reified T> coroutineCall(crossinline call: suspend CoroutineScope.() -> T): NetKResponse<T> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                parseResponse(call())
            } catch (e: Throwable) {
                e.printStackTrace()
                LogK.et(TAG, "coroutineCall Throwable ${e.message ?: ""}")
                return@withContext StatusParser.getThrowable(e).toResponse<T>()
            }
        }
    }

    suspend inline fun <reified T> coroutineCallWidthCommon(crossinline call: suspend CoroutineScope.() -> NetKCommon<T>): NetKResponse<T> {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                parseResponseWithCommon(call())
            } catch (e: Throwable) {
                e.printStackTrace()
                LogK.et(TAG, "coroutineCall Throwable ${e.message}")
                return@withContext StatusParser.getThrowable(e).toResponse<T>()
            }
        }
    }

    inline fun <reified T> parseResponse(response: T): NetKResponse<T> {
        val rawData: String = UtilKJson.t2Json(response)
        LogK.dt(TAG, "parseResponse: rawData $rawData")
        return converter.convert(rawData, T::class.java)
    }

    inline fun <reified T> parseResponseWithCommon(response: NetKCommon<T>): NetKResponse<T> {
        val rawData: String = UtilKJson.t2Json(response)
        LogK.dt(TAG, "parseResponse: rawData $rawData")
        return converter.convert(rawData, T::class.java)
    }


    fun <T> NetKThrowable.toResponse(): NetKResponse<T> {
        val response = NetKResponse<T>()
        response.code = this.code
        response.msg = this.message
        return response
    }
}

