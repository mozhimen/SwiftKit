package com.mozhimen.abilityk.restfulk.commons

import com.mozhimen.abilityk.restfulk.mos.RequestK
import com.mozhimen.abilityk.restfulk.mos.ResponseK
import java.io.IOException

/**
 * @ClassName CallK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:28
 * @Version 1.0
 */
interface Call<T> {
    @Throws(IOException::class)
    fun execute(): ResponseK<T>

    fun enqueue(callback: Callback<T>)

    interface Factory {
        fun newCall(requestK: RequestK): Call<*>
    }
}