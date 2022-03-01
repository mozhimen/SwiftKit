package com.mozhimen.abilitymk.restfulmk.commons

import com.mozhimen.abilitymk.restfulmk.mos.RequestMK
import com.mozhimen.abilitymk.restfulmk.mos.ResponseMK
import java.io.IOException

/**
 * @ClassName CallMK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:28
 * @Version 1.0
 */
interface CallMK<T> {
    @Throws(IOException::class)
    fun execute(): ResponseMK<T>

    fun enqueue(callbackMK: CallbackMK<T>)

    interface Factory {
        fun newCall(requestMK: RequestMK): CallMK<*>
    }
}