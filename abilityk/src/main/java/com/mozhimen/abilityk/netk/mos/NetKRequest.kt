package com.mozhimen.abilityk.netk.mos

import android.text.TextUtils
import android.util.Log
import com.mozhimen.abilityk.netk.annors._CacheStrategy
import com.mozhimen.abilityk.netk.annors.methods._METHOD
import com.mozhimen.basick.logk.LogK
import java.lang.Exception
import java.lang.StringBuilder
import java.lang.reflect.Type
import java.net.URLEncoder

/**
 * @ClassName NetKRequest
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:32
 * @Version 1.0
 */
open class NetKRequest {
    private val TAG = "NetKRequest>>>>>"

    @_METHOD
    var httpMethod = 0
    var headers: MutableMap<String, String>? = null
    var parameters: MutableMap<String, String>? = null
    var domainUrl: String? = null
    var returnType: Type? = null
    var relativeUrl: String? = null
    var formPost: Boolean = true
    var cacheStrategy: Int = _CacheStrategy.NET_ONLY

    private var _cacheStrategyKKey: String = ""

    /**
     * 返回的是请求的完整Url
     * scheme-host-port:443
     * https:www.mozhimen.top/basicsk/ ---relativeUrl: user/login==>https:www.mozhimen.top/basicsk/user/login
     * 另外一个场景
     * https:www.mozhimen.top/uicorek/
     * https:www.mozhimen.top/uicorek/ ---relativeUrl: /v2/user/login==>https:www.mozhimen.top/uicorek/user/login
     */
    fun endPointUrl(): String {
        require(relativeUrl != null) {
            "relative url must not be null "
        }
        if (!relativeUrl!!.startsWith("/")) {
            return domainUrl + relativeUrl
        }

        val indexOf = domainUrl!!.indexOf("/")
        return domainUrl!!.substring(0, indexOf) + relativeUrl
    }

    fun addHeaders(name: String, value: String) {
        if (headers == null) {
            headers = mutableMapOf()
        }
        headers!![name] = value
    }

    fun getCacheKey(): String {
        if (!TextUtils.isEmpty(_cacheStrategyKKey)) {
            return _cacheStrategyKKey
        }
        val builder = StringBuilder()
        val endUrl = endPointUrl()
        builder.append(endUrl)
        if (endUrl.indexOf("?") > 0 || endUrl.indexOf("&") > 0) {
            builder.append("&")
        } else {
            builder.append("?")
        }

        _cacheStrategyKKey = if (parameters != null) {
            for ((key, value) in parameters!!) {
                try {
                    val encodeValue = URLEncoder.encode(value, "UTF-8")
                    builder.append(key).append("=").append(encodeValue).append("&")
                } catch (e: Exception) {
                    LogK.et(TAG, "getCacheKey Exception ${e.message}")
                }
            }
            builder.deleteCharAt(builder.length - 1).toString()
        } else {
            endUrl
        }

        return _cacheStrategyKKey
    }
}
