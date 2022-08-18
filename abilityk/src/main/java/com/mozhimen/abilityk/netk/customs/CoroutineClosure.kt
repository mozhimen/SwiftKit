package com.mozhimen.abilityk.netk.customs

import android.util.Log
import com.mozhimen.abilityk.netk.commons.INetKConverter
import com.mozhimen.abilityk.netk.helpers.NetKThrowable
import com.mozhimen.abilityk.netk.helpers.StatusParser
import com.mozhimen.abilityk.netk.mos.NetKCommon
import com.mozhimen.abilityk.netk.mos.NetKResponse
import com.mozhimen.basick.logk.LogK
import com.mozhimen.basick.utilk.UtilKJson
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
                LogK.et(TAG, "coroutineCall Throwable ${e.message}")
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
        Log.d(TAG, "parseResponse: rawData $rawData")
        return converter.convert(rawData, T::class.java)
    }

    inline fun <reified T> parseResponseWithCommon(response: NetKCommon<T>): NetKResponse<T> {
        val rawData: String = UtilKJson.t2Json(response)
        Log.d(TAG, "parseResponse: rawData $rawData")
        return converter.convert(rawData, T::class.java)
    }


    fun <T> NetKThrowable.toResponse(): NetKResponse<T> {
        val response = NetKResponse<T>()
        response.code = this.code
        response.msg = this.message
        return response
    }
}

