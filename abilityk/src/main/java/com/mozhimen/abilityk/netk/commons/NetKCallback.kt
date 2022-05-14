package com.mozhimen.abilityk.netk.commons

import com.mozhimen.abilityk.netk.mos.NetKResponse

/**
 * @ClassName NetKCallback
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/11 22:23
 * @Version 1.0
 */
open class NetKCallback<T> : INetKListener<T> {
    override fun onSuccess(response: NetKResponse<T>) {}

    override fun onFail(throwable: Throwable) {}
}