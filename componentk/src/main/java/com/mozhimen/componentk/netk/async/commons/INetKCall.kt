package com.mozhimen.componentk.netk.async.commons

import com.mozhimen.componentk.netk.async.mos.NetKRequest
import com.mozhimen.componentk.netk.async.mos.NetKResponse
import java.io.IOException

/**
 * @ClassName CallK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:28
 * @Version 1.0
 */
interface INetKCall<T> {
    @Throws(IOException::class)
    fun execute(): NetKResponse<T>

    fun enqueue(callback: INetKListener<T>)
}