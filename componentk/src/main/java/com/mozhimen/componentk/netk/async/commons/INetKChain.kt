package com.mozhimen.componentk.netk.async.commons

import com.mozhimen.componentk.netk.async.mos.NetKRequest
import com.mozhimen.componentk.netk.async.mos.NetKResponse

/**
 * @ClassName INetKChain
 * @Description Chain对象会在派发拦截器的时候创建
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/21 21:25
 * @Version 1.0
 */
interface INetKChain {
    val isRequestPeriod: Boolean get() = false

    fun request(): NetKRequest

    /**
     * 这个response对象在网络发起之前,是为空的
     */
    fun response(): NetKResponse<*>?
}