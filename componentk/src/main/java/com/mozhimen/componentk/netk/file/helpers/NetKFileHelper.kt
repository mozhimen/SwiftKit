package com.mozhimen.componentk.netk.file.helpers

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * @ClassName NetKFileHelper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/11/1 18:00
 * @Version 1.0
 */
class NetKFileHelper {
    companion object {
        @JvmStatic
        val instance = NetKFileHelperProvider.holder
    }

    private object NetKFileHelperProvider {
        val holder = NetKFileHelper()
    }

    private var _builder: OkHttpClient.Builder? = null
        get() {
            if (field != null) return field
            val builder = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
            return builder.also { field = it }
        }

    @Throws(IOException::class)
    fun initRequest(url: String, lastModify: String): Response? {
        val request: Request = Request.Builder()
            .url(url)
            .header("Range", "bytes=0-")
            .header("If-Range", lastModify)
            .build()
        return _builder!!.build().newCall(request).execute()
    }
}