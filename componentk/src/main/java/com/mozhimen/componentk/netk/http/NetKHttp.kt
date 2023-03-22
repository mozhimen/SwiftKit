package com.mozhimen.componentk.netk.http

import android.util.Log
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.java.datatype.json.UtilKJson
import com.mozhimen.basick.utilk.java.datatype.json.UtilKJsonMoshi
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
@AManifestKRequire(CPermission.INTERNET, CApplication.USES_CLEAR_TEXT_TRAFFIC_TRUE)
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
                addInterceptor(HttpLoggingInterceptor { msg -> Log.v(TAG, msg) }.also { it.level = HttpLoggingInterceptor.Level.BODY })
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
            _retrofit = initRetrofit(value)
        }

    init {
        if (interceptors.isNotEmpty()) this._intercepters.addAll(interceptors)
    }

    @Synchronized
    fun <SERVICE : Any> create(service: Class<SERVICE>): SERVICE {
        if (_retrofit == null) {
            _retrofit = initRetrofit(baseUrl)
        }
        return _retrofit!!.create(service) as SERVICE
    }

    private fun initRetrofit(url: String): Retrofit =
        Retrofit.Builder().baseUrl(url)
            .client(_okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(UtilKJsonMoshi.moshiBuilder))
            .build()
}