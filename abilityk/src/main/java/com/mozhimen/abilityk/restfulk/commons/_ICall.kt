package com.mozhimen.abilityk.restfulk.commons

import com.mozhimen.abilityk.restfulk.mos._Request
import com.mozhimen.abilityk.restfulk.mos._Response
import java.io.IOException

/**
 * @ClassName CallK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:28
 * @Version 1.0
 */
interface _ICall<T> {
    @Throws(IOException::class)
    fun execute(): _Response<T>

    fun enqueue(ICallback: _ICallback<T>)

    interface IFactory {
        fun newCall(request: _Request): _ICall<*>
    }
}