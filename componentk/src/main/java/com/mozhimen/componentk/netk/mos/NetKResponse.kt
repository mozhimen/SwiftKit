package com.mozhimen.componentk.netk.mos

import com.mozhimen.componentk.netk.helpers.StatusParser
import java.io.Serializable

/**
 * @ClassName NetKResponse
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:05
 * @Version 1.0
 */
open class NetKResponse<T> : Serializable {

    var rawData: String? = null//原始数据

    var code = 0//业务状态码
    var data: T? = null//业务数据
    var errorData: Map<String, String>? = null//错误状态下的数据
    var msg: String? = null//错误信息

    open fun isSuccessful(): Boolean =
        code == StatusParser.SUCCESS || code == StatusParser.CACHE_SUCCESS || code in 200..299 || data != null
}