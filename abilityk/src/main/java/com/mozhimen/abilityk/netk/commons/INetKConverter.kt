package com.mozhimen.abilityk.netk.commons

import com.mozhimen.abilityk.netk.mos.NetKResponse
import java.lang.reflect.Type

/**
 * @ClassName Converter
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 20:47
 * @Version 1.0
 */
interface INetKConverter {
    fun <T> convert(rawData: String, dataType: Type): NetKResponse<T>
}