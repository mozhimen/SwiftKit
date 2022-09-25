package com.mozhimen.componentk.netk.commons

import com.mozhimen.componentk.netk.mos.NetKResponse
import java.lang.reflect.Type

/**
 * @ClassName Converter
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 20:47
 * @Version 1.0
 */
interface INetKConverter {
    val TAG: String
        get() = "${this.javaClass.simpleName}>>>>>"

    fun <T> convert(rawData: String, dataType: Type): NetKResponse<T>
}