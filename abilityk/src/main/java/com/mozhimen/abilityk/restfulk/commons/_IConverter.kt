package com.mozhimen.abilityk.restfulk.commons

import com.mozhimen.abilityk.restfulk.mos._Response
import java.lang.reflect.Type

/**
 * @ClassName Converter
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 20:47
 * @Version 1.0
 */
interface _IConverter {
    fun <T> convert(rawData: String, dataType: Type): _Response<T>
}