package com.mozhimen.swiftmk.utils.http

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @ClassName ServiceCreator
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/15 15:12
 * @Version 1.0
 */
/**
 * 作用: Retrofit构建器
 * 用法: val appService = ServiceCreator("").create<AppService>()
 */
class ServiceCreator(baseUrl: String) {
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}