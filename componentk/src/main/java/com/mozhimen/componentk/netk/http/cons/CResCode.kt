package com.mozhimen.componentk.netk.http.cons

/**
 * @ClassName CResCode
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/5/12 17:44
 * @Version 1.0
 */
object CResCode {
    const val SUCCESS = 200
    const val Empty = -200
    const val NETWORK_ERROR = 426//网络错误
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val REQUEST_TIMEOUT = 408//请求超时
    const val GATEWAY_TIMEOUT = 504//网关超时
    const val INTERNAL_SERVER_ERROR = 500//内部服务器错误
    const val BAD_GATEWAY = 502//错误网关
    const val SERVICE_UNAVAILABLE = 503//服务未达
    const val PARSE_ERROR = 1001//解析错误
    const val NET_WORD_ERROR = 1002//网络错误
    const val SSL_ERROR = 1005//证书出错
    const val NULL_POINT = -1000
    const val UNKNOWN = 1000//未知错误
}