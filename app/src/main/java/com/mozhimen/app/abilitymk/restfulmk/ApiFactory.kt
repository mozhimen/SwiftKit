package com.mozhimen.app.abilitymk.restfulmk

import com.mozhimen.abilitymk.restfulmk.RESTfulMK

/**
 * @ClassName ApiFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 22:16
 * @Version 1.0
 */
object ApiFactory {
    private val baseUrl = ""
    private val restfulMK: RESTfulMK = RESTfulMK(baseUrl, RetrofitCallFactory(baseUrl))

    init {
        restfulMK.addIntercept(BizInterceptor())
        restfulMK.addIntercept(HttpStatusInterceptor())
    }

    fun <T> create(service: Class<T>): T {
        return restfulMK.create(service)
    }
}