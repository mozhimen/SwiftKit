package com.mozhimen.abilityk.netk.commons

import com.mozhimen.abilityk.netk.mos.NetKRequest
import com.mozhimen.abilityk.netk.mos.NetKResponse

/**
 * @ClassName Interceptor
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:39
 * @Version 1.0
 */
interface INetKInterceptor {
    val TAG: String
        get() = "${this.javaClass.simpleName}>>>>>"

    fun intercept(chain: INetKChain): Boolean

    /**
     * Chain对象会在派发拦截器的时候创建
     */
    interface INetKChain {
        val isRequestPeriod: Boolean get() = false

        fun request(): NetKRequest

        /**
         * 这个response对象在网络发起之前,是为空的
         */
        fun response(): NetKResponse<*>?
    }
}