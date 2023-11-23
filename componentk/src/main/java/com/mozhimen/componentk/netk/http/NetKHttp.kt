package com.mozhimen.componentk.netk.http

import android.util.Log
import com.mozhimen.basick.elemk.android.util.cons.CLogPriority
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CApplication
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.util.UtilKLog
import com.mozhimen.basick.utilk.android.util.UtilKLogSupport
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.squareup.moshi.UtilKMoshi
import com.mozhimen.componentk.BuildConfig
import com.mozhimen.underlayk.logk.cons.CLogKCons
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
) : BaseUtilK() {
    private val _intercepters: ArrayList<Interceptor> = ArrayList()
    private val _okHttpClient by lazy {
        OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor { msg -> UtilKLog.longLog(CLogPriority.V, TAG, msg) }.also { it.level = HttpLoggingInterceptor.Level.BODY })
                if (_intercepters.isNotEmpty())
                    for (interceptor in _intercepters) addInterceptor(interceptor)
            }
        }.build()
    }

    private var _retrofit: Retrofit? = null
        get() {
            if (field != null) return field
            return initRetrofit(baseUrl).also { field = it }
        }

    /////////////////////////////////////////////////////////////////////////

    val okHttpClient: OkHttpClient
        get() = _okHttpClient

    val retrofit: Retrofit
        get() = _retrofit!!

    var baseUrl: String = baseUrl
        set(value) {
            field = value
            _retrofit = initRetrofit(value)
        }

    /////////////////////////////////////////////////////////////////////////

    init {
        if (interceptors.isNotEmpty()) this._intercepters.addAll(interceptors)
    }

    /////////////////////////////////////////////////////////////////////////

    @Synchronized
    fun <SERVICE : Any> create(service: Class<SERVICE>): SERVICE {
        if (_retrofit == null) {
            _retrofit = initRetrofit(baseUrl)
        }
        return _retrofit!!.create(service) as SERVICE
    }

    inline fun <reified SERVICE : Any> create(): SERVICE {
        return create(SERVICE::class.java)
    }

    /////////////////////////////////////////////////////////////////////////

    private fun initRetrofit(url: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .client(_okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(UtilKMoshi.moshiBuilder))
            .build()
}