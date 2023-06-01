package com.mozhimen.componentk.netk.http.helpers

import com.google.gson.JsonParseException
import com.mozhimen.componentk.netk.http.mos.MNetKThrowable
import org.json.JSONException
import retrofit2.HttpException
import java.lang.NullPointerException
import java.net.ConnectException
import javax.net.ssl.SSLHandshakeException
import com.mozhimen.componentk.netk.http.cons.CResCode
/**
 * @ClassName ExceptionParser
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 13:30
 * @Version 1.0
 */
object NetKRepErrorParser {
    private const val TAG = "NetKRepErrorParser>>>>>"

    @JvmStatic
    fun getThrowable(e: Throwable): MNetKThrowable {
        e.printStackTrace()
        e.message?.et(TAG)
        return when (e) {
            is HttpException -> {
                val message = when (e.code()) {
                    CResCode.NETWORK_ERROR -> e.message() ?: "网络异常: 未知"
                    CResCode.UNAUTHORIZED -> "网络异常: 未授权"
                    CResCode.FORBIDDEN -> "网络异常: 拒绝访问"
                    CResCode.NOT_FOUND -> "网络异常: 未找到网址"
                    CResCode.REQUEST_TIMEOUT -> "网络异常: 请求超时"
                    CResCode.GATEWAY_TIMEOUT -> "网络异常: 网关超时"
                    CResCode.INTERNAL_SERVER_ERROR -> "网络异常: 内部服务器错误"
                    CResCode.BAD_GATEWAY -> "网络异常: 错误网关"
                    CResCode.SERVICE_UNAVAILABLE -> "网络异常: 服务未达"
                    else -> e.message() ?: "网络异常: 未知"
                }
                MNetKThrowable(e.code(), message)
            }

            is JsonParseException, is JSONException -> {
                MNetKThrowable(CResCode.PARSE_ERROR, "解析失败")
            }

            is ConnectException -> {
                MNetKThrowable(CResCode.NET_WORD_ERROR, "网络连接拒绝,请检查IP或端口号")
            }

            is SSLHandshakeException -> {
                MNetKThrowable(CResCode.SSL_ERROR, "证书验证失败")
            }

            is NullPointerException -> {
                MNetKThrowable(CResCode.NULL_POINT, "空指针错误")
            }

            else -> {
                MNetKThrowable(CResCode.UNKNOWN, e.message ?: "未知异常")
            }
        }
    }
}