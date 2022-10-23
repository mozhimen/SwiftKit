package com.mozhimen.componentk.netk.async.commons

import com.mozhimen.componentk.netk.async.mos.NetKRequest

/**
 * @ClassName INetKFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/10/21 21:25
 * @Version 1.0
 */
interface INetKFactory {
    fun newCall(request: NetKRequest): INetKCall<*>
}