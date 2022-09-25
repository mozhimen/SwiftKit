package com.mozhimen.componentk.netk

import com.mozhimen.componentk.netk.helpers.ClientBuilder
import okhttp3.Interceptor
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
    private val _baseUrl: String
) {
    private var _retrofit: Retrofit? = null
    private val _interceptors = mutableListOf<Interceptor>()

    /**
     * 增加拦截器
     * @param interceptors Array<Interceptor>
     * @return NetKCoroutine
     */
    fun addInterceptors(interceptors: Array<Interceptor>): NetKCoroutine {
        _interceptors.addAll(interceptors)
        return this
    }

    /**
     * 增加拦截器
     * @param interceptor Interceptor
     * @return NetKCoroutine
     */
    fun addInterceptor(interceptor: Interceptor): NetKCoroutine {
        _interceptors.add(interceptor)
        return this
    }

    /**
     * 初始化
     */
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