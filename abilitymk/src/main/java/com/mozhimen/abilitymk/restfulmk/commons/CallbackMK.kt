package com.mozhimen.abilitymk.restfulmk.commons

import com.mozhimen.abilitymk.restfulmk.mos.ResponseMK

/**
 * @ClassName CallbackMK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:03
 * @Version 1.0
 */
interface CallbackMK<T> {
    fun onSuccess(responseMK: ResponseMK<T>)
    fun onFail(throwable: Throwable)
}