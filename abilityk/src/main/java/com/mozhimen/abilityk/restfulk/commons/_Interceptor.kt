package com.mozhimen.abilityk.restfulk.commons

import com.mozhimen.abilityk.restfulk.mos._Request
import com.mozhimen.abilityk.restfulk.mos._Response

/**
 * @ClassName Interceptor
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:39
 * @Version 1.0
 */
interface _Interceptor {
    fun intercept(IChain: IChain): Boolean

    /**
     * Chain对象会在派发拦截器的时候创建
     */
    interface IChain {
        val isRequestPeriod: Boolean get() = false

        fun request(): _Request

        /**
         * 这个response对象在网络发起之前,是为空的
         */
        fun response(): _Response<*>?
    }
}