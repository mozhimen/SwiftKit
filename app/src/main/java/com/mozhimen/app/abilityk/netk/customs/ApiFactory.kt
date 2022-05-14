package com.mozhimen.app.abilityk.netk.customs

import com.mozhimen.abilityk.netk.NetKAsync
import com.mozhimen.abilityk.netk.NetKCoroutine
import com.mozhimen.abilityk.netk.NetKRxJava
import com.mozhimen.abilityk.netk.customs.AsyncConverter
import com.mozhimen.abilityk.netk.customs.AsyncInterceptorLog
import com.mozhimen.abilityk.netk.customs.AsyncFactory

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
object ApiFactory {
    private val _baseUrl = "https://api.caiyunapp.com/v2.5/cIecnVlovchAFYIk/"
    private val _netkAsync: NetKAsync = NetKAsync(_baseUrl, AsyncFactory(_baseUrl))
    private val _netkCoroutine: NetKCoroutine = NetKCoroutine(_baseUrl, AsyncConverter())
    private val _netkRxJava: NetKRxJava = NetKRxJava(_baseUrl)

    init {
        //_netkAsync.addIntercept(BizInterceptor())
        //_netkAsync.addIntercept(RouteInterceptor())
        _netkAsync.addInterceptor(AsyncInterceptorLog())
        _netkCoroutine.initRetrofit()
        _netkRxJava.initRetrofit()
    }

    fun <T> createAsync(api: Class<T>): T {
        return _netkAsync.create(api)
    }

    fun <T> createCoroutine(api: Class<T>): T {
        return _netkCoroutine.create(api)
    }

    fun <T> createRxJava(api: Class<T>): T {
        return _netkRxJava.create(api)
    }
}