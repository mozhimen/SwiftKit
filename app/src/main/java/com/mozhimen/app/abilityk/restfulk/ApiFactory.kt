package com.mozhimen.app.abilityk.restfulk

import com.mozhimen.abilityk.restfulk.RESTfulK

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
object ApiFactory {
    private val baseUrl = ""
    private val restfulK: RESTfulK = RESTfulK(baseUrl, RetrofitCallFactory(baseUrl))

    init {
        restfulK.addIntercept(BizInterceptor())
        restfulK.addIntercept(HttpStatusInterceptor())
    }

    fun <T> create(service: Class<T>): T {
        return restfulK.create(service)
    }
}