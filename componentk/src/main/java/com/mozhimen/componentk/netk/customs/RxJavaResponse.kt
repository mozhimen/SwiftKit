package com.mozhimen.componentk.netk.customs

import com.mozhimen.componentk.netk.commons.INetKConverter
import com.mozhimen.componentk.netk.helpers.NetKThrowable
import com.mozhimen.componentk.netk.helpers.StatusParser
import com.mozhimen.componentk.netk.mos.NetKResponse
import com.mozhimen.basick.utilk.UtilKJson
import com.mozhimen.basick.utilk.UtilKT
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @ClassName RxJavaResponse
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/13 17:07
 * @Version 1.0
 */
abstract class RxJavaResponse<T>(private val _converter: INetKConverter = AsyncConverter()) :
    Observer<T> {
    abstract fun onSuccess(response: NetKResponse<T>)
    abstract fun onFailed(code: Int, message: String?)

    override fun onNext(value: T) {
        try {
            val response: NetKResponse<T> = parseResponse(value)
            if (response.isSuccessful()) {
                onSuccess(response)
            } else {
                onFailed(StatusParser.RESPONSE_NULL, "数据请求失败")
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            val ex = StatusParser.getThrowable(e)
            onFailed(ex.code, ex.message)
        }
    }

    override fun onSubscribe(d: Disposable) {}

    override fun onComplete() {}

    override fun onError(e: Throwable) {
        e.printStackTrace()
        val ex: NetKThrowable = if (e is NetKThrowable) e else StatusParser.getThrowable(e)
        onFailed(ex.code, ex.message)
    }

    private fun <T> parseResponse(value: T): NetKResponse<T> {
        val rawData: String = UtilKJson.t2Json(value)
        return _converter.convert(rawData, UtilKT.getT<T>(this@RxJavaResponse, 0)!!)
    }
}