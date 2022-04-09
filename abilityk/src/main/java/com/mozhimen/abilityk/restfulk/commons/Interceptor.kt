package com.mozhimen.abilityk.restfulk.commons

import com.mozhimen.abilityk.restfulk.mos.RequestK
import com.mozhimen.abilityk.restfulk.mos.ResponseK

/**
 * @ClassName InterceptorK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:39
 * @Version 1.0
 */
interface InterceptorK {
    fun intercept(chain: Chain): Boolean

    /**
     * Chain对象会在派发拦截器的时候创建
     */
    interface Chain {
        val isRequestPeriod: Boolean get() = false

        fun request(): RequestK

        /**
         * 这个response对象在网络发起之前,是为空的
         */
        fun response(): ResponseK<*>?
    }
}