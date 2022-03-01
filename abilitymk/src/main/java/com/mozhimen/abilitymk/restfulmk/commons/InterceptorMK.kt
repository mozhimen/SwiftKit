package com.mozhimen.abilitymk.restfulmk.commons

import com.mozhimen.abilitymk.restfulmk.mos.RequestMK
import com.mozhimen.abilitymk.restfulmk.mos.ResponseMK

/**
 * @ClassName InterceptorMK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:39
 * @Version 1.0
 */
interface InterceptorMK {
    fun intercept(chain: Chain): Boolean

    /**
     * Chain对象会在派发拦截器的时候创建
     */
    interface Chain {
        val isRequestPeriod: Boolean get() = false

        fun request(): RequestMK

        /**
         * 这个response对象在网络发起之前,是为空的
         */
        fun response(): ResponseMK<*>?
    }
}