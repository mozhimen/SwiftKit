package com.mozhimen.abilityk.restfulk.commons

import com.mozhimen.abilityk.restfulk.mos._Response

/**
 * @ClassName CallbackK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:03
 * @Version 1.0
 */
interface _ICallback<T> {
    fun onSuccess(response: _Response<T>)
    fun onFail(throwable: Throwable)
}