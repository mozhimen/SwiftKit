package com.mozhimen.abilitymk.restfulmk.mos

import android.text.TextUtils
import com.mozhimen.abilitymk.restfulmk.annors.CacheStrategyMK
import com.mozhimen.abilitymk.restfulmk.annors.methods.METHODMK
import java.lang.Exception
import java.lang.StringBuilder
import java.lang.reflect.Type
import java.net.URLEncoder

/**
 * @ClassName RequestMK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:32
 * @Version 1.0
 */
open class RequestMK {
    @METHODMK
    var httpMethod = 0
    var headers: MutableMap<String, String>? = null
    var parameters: MutableMap<String, String>? = null
    var domainUrl: String? = null
    var returnType: Type? = null
    var relativeUrl: String? = null
    var formPost: Boolean = true
    var cacheStrategyMK: Int = CacheStrategyMK.NET_ONLY
    private var cacheStrategyMKKey: String = ""

    constructor(
        httpMethod: Int,
        headers: MutableMap<String, String>?,
        parameters: MutableMap<String, String>?,
        domainUrl: String?,
        returnType: Type?,
        relativeUrl: String?,
        formPost: Boolean,
        cacheStrategy: Int
    ) {
        this.httpMethod = httpMethod
        this.headers = headers
        this.parameters = parameters
        this.domainUrl = domainUrl
        this.returnType = returnType
        this.relativeUrl = relativeUrl
        this.formPost = formPost
        this.cacheStrategyMK = cacheStrategy
    }

    /**
     * 返回的是请求的完整Url
     * scheme-host-port:443
     * https:www.mozhimen.top/basicsmk/ ---relativeUrl: user/login===>https:www.mozhimen.top/basicsmk/user/login
     * 另外一个场景
     * https:www.mozhimen.top/uicoremk/
     * https:www.mozhimen.top/uicoremk/ ---relativeUrl: /v2/user/login===>https:www.mozhimen.top/uicoremk/user/login
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
        if (!TextUtils.isEmpty(cacheStrategyMKKey)) {
            return cacheStrategyMKKey
        }
        val builder = StringBuilder()
        val endUrl = endPointUrl()
        builder.append(endUrl)
        if (endUrl.indexOf("?") > 0 || endUrl.indexOf("&") > 0) {
            builder.append("&")
        } else {
            builder.append("?")
        }

        cacheStrategyMKKey = if (parameters != null) {
            for ((key, value) in parameters!!) {
                try {
                    val encodeValue = URLEncoder.encode(value, "UTF-8")
                    builder.append(key).append("=").append(encodeValue).append("&")
                } catch (e: Exception) {
                    //ignore
                }
            }
            builder.deleteCharAt(builder.length - 1).toString()
        } else {
            endUrl
        }

        return cacheStrategyMKKey
    }
}
