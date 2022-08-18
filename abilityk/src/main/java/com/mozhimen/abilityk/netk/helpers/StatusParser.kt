package com.mozhimen.abilityk.netk.helpers

import com.google.gson.JsonParseException
import com.mozhimen.abilityk.netk.mos.NetKResponse
import org.json.JSONException
import retrofit2.HttpException
import java.lang.NullPointerException
import java.net.ConnectException
import javax.net.ssl.SSLHandshakeException

/**
 * @ClassName ExceptionParser
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 13:30
 * @Version 1.0
 */
object StatusParser {
    private const val TAG = "ExceptionParser>>>>>"

    const val RESPONSE_NULL = -2
    const val NULL_POINT = -1000
    const val SUCCESS = 0
    const val CACHE_SUCCESS: Int = 304
    const val UNAUTHORIZED = 401
    const val FORBIDDEN = 403
    const val NOT_FOUND = 404
    const val REQUEST_TIMEOUT = 408//请求超时
    const val NETWORK_ERROR = 426//网络错误
    const val INTERNAL_SERVER_ERROR = 500//内部服务器错误
    const val BAD_GATEWAY = 502//错误网关
    const val SERVICE_UNAVAILABLE = 503//服务未达
    const val GATEWAY_TIMEOUT = 504//网关超时
    const val UNKNOWN = 1000//未知错误
    const val PARSE_ERROR = 1001//解析错误
    const val NET_WORD_ERROR = 1002//网络错误
    const val HTTP_ERROR = 1003//协议出错
    const val SSL_ERROR = 1005//证书出错

    fun getThrowable(e: Throwable): NetKThrowable {
        e.printStackTrace()
        val ex: NetKThrowable
        return if (e is HttpException && e.code() == NETWORK_ERROR) {
            ex = NetKThrowable(e, NETWORK_ERROR)
            ex.message = e.message()
            ex
        } else if (e is HttpException) {
            ex = NetKThrowable(e, HTTP_ERROR)
            when (e.code()) {
                UNAUTHORIZED -> ex.message = "服务器异常: 未授权"
                FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE ->
                    ex.message = "服务器异常: 请稍后再试"
                else -> ex.message = "服务器异常: 未知"
            }
            ex
        } else if (e is ServerException) {
            val resultException: ServerException = e
            ex = NetKThrowable(resultException, resultException.code)
            ex.message = resultException.message
            ex
        } else if (e is JsonParseException || e is JSONException) {
            ex = NetKThrowable(e, PARSE_ERROR)
            ex.message = "解析错误"
            ex
        } else if (e is ConnectException) {
            ex = NetKThrowable(e, NET_WORD_ERROR)
            ex.message = "连接失败"
            ex
        } else if (e is SSLHandshakeException) {
            ex = NetKThrowable(e, SSL_ERROR)
            ex.message = "证书验证失败"
            ex
        } else if (e is NullPointerException) {
            ex = NetKThrowable(e, NULL_POINT)
            ex.message = "空指针异常"
            ex
        } else {
            ex = NetKThrowable(e, UNKNOWN)
            ex.message = "网络异常"
            ex
        }
    }

    /**
     * ServerException发生后, 将自动转换为ResponseThrowable返回
     * @property code Int
     * @property message String?
     */
    internal class ServerException : RuntimeException() {
        var code = 0
        override var message: String? = null
    }
}