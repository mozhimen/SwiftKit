package com.mozhimen.abilityk.netk

import com.mozhimen.abilityk.netk.commons.INetKConverter
import com.mozhimen.abilityk.netk.customs.AsyncConverter
import com.mozhimen.abilityk.netk.customs.CoroutineClosure
import com.mozhimen.abilityk.netk.helpers.ClientBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * @ClassName CoroutineFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 16:01
 * @Version 1.0
 */
open class NetKCoroutine(
    private val _baseUrl: String,
    converter: INetKConverter? = null
) {
    private var _retrofit: Retrofit? = null
    private val _interceptors = mutableListOf<Interceptor>()

    init {
        converter?.let {
            CoroutineClosure.get(converter)
        }
    }

    fun addInterceptors(interceptors: Array<Interceptor>): NetKCoroutine {
        _interceptors.addAll(interceptors)
        return this
    }

    fun addInterceptor(interceptor: Interceptor): NetKCoroutine {
        _interceptors.add(interceptor)
        return this
    }

    fun initRetrofit() {
        _retrofit = Retrofit.Builder()
            .baseUrl(_baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(ClientBuilder.getClient(_interceptors))
            .build()
    }

    /**
     * 创建服务
     * @param service Class<T>
     * @return T
     */
    fun <T> create(service: Class<T>): T {
        return _retrofit!!.create(service)
    }
}