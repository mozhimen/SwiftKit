package com.mozhimen.componentk.netk.async.commons

import com.mozhimen.componentk.netk.async.mos.NetKResponse

/**
 * @ClassName CallbackK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:03
 * @Version 1.0
 */
interface INetKListener<T> {
    fun onSuccess(response: NetKResponse<T>)
    fun onFail(throwable: Throwable)
}