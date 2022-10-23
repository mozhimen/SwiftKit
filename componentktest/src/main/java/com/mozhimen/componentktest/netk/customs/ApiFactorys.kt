package com.mozhimen.componentktest.netk.customs

import com.mozhimen.componentk.netk.coroutine.NetKCoroutineFactory

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
object ApiFactorys {
    private val _baseUrl = "https://api.caiyunapp.com/v2.5/cIecnVlovchAFYIk/"

    //private val _netkAsync: NetKAsync = NetKAsync(_baseUrl, AsyncFactory(_baseUrl))
    val netkCoroutineFactory by lazy {
        NetKCoroutineFactory(_baseUrl)
    }
    //private val _netkRxJava: NetKRxJava = NetKRxJava(_baseUrl)

//    init {
//        _netkAsync.addInterceptor(AsyncInterceptorLog())
//    }
//
//    fun <T> createAsync(api: Class<T>): T {
//        return _netkAsync.create(api)
//    }
//
//    fun <T> createCoroutine(api: Class<T>): T {
//        return _netkCoroutineFactory.create(api)
//    }
//
//    fun <T> createRxJava(api: Class<T>): T {
//        return _netkRxJava.create(api)
//    }
}