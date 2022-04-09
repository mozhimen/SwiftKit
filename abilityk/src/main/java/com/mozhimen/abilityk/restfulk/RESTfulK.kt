package com.mozhimen.abilityk.restfulk

import com.mozhimen.abilityk.restfulk.commons.CallK
import com.mozhimen.abilityk.restfulk.commons.InterceptorK
import com.mozhimen.abilityk.restfulk.helpers.MethodParser
import com.mozhimen.abilityk.restfulk.helpers.Scheduler
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName RESTfulK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:46
 * @Version 1.0
 */
open class RESTfulK constructor(
    private val baseUrl: String,
    private val callFactory: CallK.Factory
) {
    private var interceptors = mutableListOf<InterceptorK>()
    private var methodService = ConcurrentHashMap<Method, MethodParser>()
    private val schedulerK by lazy {
        Scheduler(callFactory, interceptors)
    }

    fun addIntercept(interceptorK: InterceptorK) {
        interceptors.add(interceptorK)
    }

    /**
     * interface ApiService{
     *  @Headers("auth-token:token","accountId:123456")
     *  @BaseUrl("http://api.mozhimen.top/as/")
     *  @Post("/cities/{province}")
     *  @Get("/cities")
     *  fun listCities(@Path("province") pronvice:Int ,@Filed("page") page:Int): CallK<JsonObject>
     * }
     */
    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service),
            object : InvocationHandler {
                override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
                    var methodParser = methodService[method]
                    if (methodParser == null) {
                        methodParser = MethodParser.parse(baseUrl, method)
                        methodService[method] = methodParser
                    }

                    //bugFix: 此处应当考虑methodParser复用,每次调用都应当解析入参
                    val newRequest = methodParser.newRequest(method, args)
                    return schedulerK.newCall(newRequest)
                }
            }) as T
    }
}