package com.mozhimen.componentk.netk.async.commons

import com.mozhimen.componentk.netk.async.mos.NetKRequest
import com.mozhimen.componentk.netk.async.mos.NetKResponse

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
}