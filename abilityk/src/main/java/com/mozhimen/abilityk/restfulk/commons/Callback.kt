package com.mozhimen.abilityk.restfulk.commons

import com.mozhimen.abilityk.restfulk.mos.ResponseK

/**
 * @ClassName CallbackK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:03
 * @Version 1.0
 */
interface Callback<T> {
    fun onSuccess(responseK: ResponseK<T>)
    fun onFail(throwable: Throwable)
}