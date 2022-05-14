package com.mozhimen.abilityk.netk.commons

import com.mozhimen.abilityk.netk.mos.NetKRequest
import com.mozhimen.abilityk.netk.mos.NetKResponse
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

    interface INetKFactory {
        fun newCall(request: NetKRequest): INetKCall<*>
    }
}