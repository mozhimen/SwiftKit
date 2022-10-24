package com.mozhimen.componentk.netk

import android.util.Log
import com.mozhimen.componentk.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName CoroutineFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 16:01
 * @Version 1.0
 */
open class NetKCoroutineFactory(
    baseUrl: String,
    interceptors: List<Interceptor> = emptyList()
) {
    private val TAG = "NetKCoroutine>>>>>"
    private val _intercepters: ArrayList<Interceptor> = ArrayList()
    private val _services = ConcurrentHashMap<Int, Any>()
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
    fun <T : Any> create(service: Class<T>): T {
        val id = service::class.java.hashCode()
        if (!_services.containsKey(id)) {
            _retrofit!!.create(service).apply {
                _services[id] = this
                return this
            }
        }
        return _services[id] as T
    }

    private fun initRetrofit(url: String): Retrofit =
        Retrofit.Builder().baseUrl(url)
            .client(_okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
}