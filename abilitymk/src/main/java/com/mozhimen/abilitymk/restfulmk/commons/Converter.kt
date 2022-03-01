package com.mozhimen.abilitymk.restfulmk.commons

import com.mozhimen.abilitymk.restfulmk.mos.ResponseMK
import java.lang.reflect.Type

/**
 * @ClassName Converter
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2021/12/13 20:47
 * @Version 1.0
 */
interface Converter {
    fun <T> convert(rawData: String, dataType: Type): ResponseMK<T>
}