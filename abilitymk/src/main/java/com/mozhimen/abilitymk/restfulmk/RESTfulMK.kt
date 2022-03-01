package com.mozhimen.abilitymk.restfulmk

import com.mozhimen.abilitymk.restfulmk.commons.CallMK
import com.mozhimen.abilitymk.restfulmk.commons.InterceptorMK
import com.mozhimen.abilitymk.restfulmk.helpers.MethodParser
import com.mozhimen.abilitymk.restfulmk.helpers.Scheduler
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

/**
 * @ClassName RESTfulMK
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/9/26 20:46
 * @Version 1.0
 */
open class RESTfulMK constructor(
    private val baseUrl: String,
    private val callFactory: CallMK.Factory
) {
    private var interceptors = mutableListOf<InterceptorMK>()
    private var methodService = ConcurrentHashMap<Method, MethodParser>()
    private val schedulerMK by lazy {
        Scheduler(callFactory, interceptors)
    }

    fun addIntercept(interceptorMK: InterceptorMK) {
        interceptors.add(interceptorMK)
    }

    /**
     * interface ApiService{
     *  @Headers("auth-token:token","accountId:123456")
     *  @BaseUrl("http://api.mozhimen.top/as/")
     *  @Post("/cities/{province}")
     *  @Get("/cities")
     *  fun listCities(@Path("province") pronvice:Int ,@Filed("page") page:Int): CallMK<JsonObject>
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
                    return schedulerMK.newCall(newRequest)
                }
            }) as T
    }
}