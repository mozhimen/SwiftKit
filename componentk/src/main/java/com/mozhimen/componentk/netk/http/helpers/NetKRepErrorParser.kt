package com.mozhimen.componentk.netk.http.helpers

import com.google.gson.JsonParseException
import com.mozhimen.componentk.netk.http.mos.MNetKThrowable
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
object NetKRepErrorParser {
    private const val TAG = "NetKRepErrorParser>>>>>"

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

    fun getThrowable(e: Throwable): MNetKThrowable {
        e.printStackTrace()
        return when (e) {
            is HttpException -> {
                val message = when (e.code()) {
                    NETWORK_ERROR -> e.message() ?: "网络异常: 未知"
                    UNAUTHORIZED -> "网络异常: 未授权"
                    FORBIDDEN -> "网络异常: 拒绝访问"
                    NOT_FOUND -> "网络异常: 未找到网址"
                    REQUEST_TIMEOUT -> "网络异常: 请求超时"
                    GATEWAY_TIMEOUT -> "网络异常: 网关超时"
                    INTERNAL_SERVER_ERROR -> "网络异常: 内部服务器错误"
                    BAD_GATEWAY -> "网络异常: 错误网关"
                    SERVICE_UNAVAILABLE -> "网络异常: 服务未达"
                    else -> e.message() ?: "网络异常: 未知"
                }
                MNetKThrowable(e.code(), message)
            }
            is JsonParseException, is JSONException -> {
                MNetKThrowable(PARSE_ERROR, "解析失败")
            }
            is ConnectException -> {
                MNetKThrowable(NET_WORD_ERROR, "网络连接失败")
            }
            is SSLHandshakeException -> {
                MNetKThrowable(SSL_ERROR, "证书验证失败")
            }
            is NullPointerException -> {
                MNetKThrowable(NULL_POINT, "空指针错误")
            }
            else -> {
                MNetKThrowable(UNKNOWN, e.message ?: "未知异常")
            }
        }
    }
}