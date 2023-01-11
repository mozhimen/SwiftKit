package com.mozhimen.componentk.netk.http

import android.util.Log
import com.mozhimen.basick.permissionk.annors.APermissionRequire
import com.mozhimen.basick.permissionk.cons.CApplication
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.utilk.UtilKJson
import com.mozhimen.componentk.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @ClassName CoroutineFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 16:01
 * @Version 1.0
 */
@APermissionRequire(CPermission.INTERNET, CApplication.USES_CLEAR_TEXT_TRAFFIC_TRUE)
open class NetKHttp(
    baseUrl: String,
    interceptors: List<Interceptor> = emptyList()
) {
    companion object {
        private const val TAG = "NetKHttp>>>>>"
    }

    private val _intercepters: ArrayList<Interceptor> = ArrayList()
    private val _okHttpClient by lazy {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor { msg -> Log.d(TAG, msg) }.also { it.level = HttpLoggingInterceptor.Level.BODY })
                if (_intercepters.isNotEmpty()) _intercepters.forEach { addInterceptor(it) }
            }
        }.build()
    }

    private var _retrofit: Retrofit? = null
        get() {
            if (field != null) return field
            return initRetrofit(baseUrl).also { field = it }
        }

    var baseUrl: String = baseUrl
        set(value) {
            field = value
            initRetrofit(value).also { _retrofit = it }
        }

    init {
        if (interceptors.isNotEmpty()) this._intercepters.addAll(interceptors)
    }

    @Synchronized
    fun <SERVICE : Any> create(service: Class<SERVICE>): SERVICE {
        return _retrofit!!.create(service) as SERVICE
    }

    private fun initRetrofit(url: String): Retrofit =
        Retrofit.Builder().baseUrl(url)
            .client(_okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(UtilKJson.moshiBuilder))
            .build()
}