package com.mozhimen.abilityk.netk

import com.mozhimen.abilityk.netk.commons.INetKConverter
import com.mozhimen.abilityk.netk.customs.AsyncConverter
import com.mozhimen.abilityk.netk.helpers.ClientBuilder
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * @ClassName RxJavaFactory
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/12 16:01
 * @Version 1.0
 */
class NetKRxJava(
    private val _baseUrl: String
) {

    private var _retrofit: Retrofit? = null
    private val _interceptors = mutableListOf<Interceptor>()

    fun addInterceptors(interceptors: Array<Interceptor>): NetKRxJava {
        _interceptors.addAll(interceptors)
        return this
    }

    fun addInterceptor(interceptor: Interceptor): NetKRxJava {
        _interceptors.add(interceptor)
        return this
    }

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(_baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(ClientBuilder.getClient(_interceptors))
            .build().also { _retrofit = it }
    }

    /**
     * 创建服务
     * @param service Class<T>
     * @return T
     */
    fun <T> create(service: Class<T>): T {
        return _retrofit?.create(service) ?: initRetrofit().create(service)
    }
}