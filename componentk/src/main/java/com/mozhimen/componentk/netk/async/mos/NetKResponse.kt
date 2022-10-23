package com.mozhimen.componentk.netk.async.mos

import java.io.Serializable

/**
 * @ClassName NetKResponse
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:05
 * @Version 1.0
 */
open class NetKResponse<T> : Serializable {

    companion object {
        const val SUCCESS = 0
        const val CACHE_SUCCESS: Int = 1
        const val ERROR = -1
    }

    var rawData: String? = null//原始数据

    var code = 0//业务状态码
    var msg: String? = null//错误信息
    var data: T? = null//业务数据
    //var errorData: Map<String, String>? = null//错误状态下的数据

    open fun isSuccessful(): Boolean =
        data != null && (code == SUCCESS || code == CACHE_SUCCESS)
}