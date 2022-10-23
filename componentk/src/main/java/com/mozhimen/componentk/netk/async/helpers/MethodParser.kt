package com.mozhimen.componentk.netk.async.helpers

import com.mozhimen.componentk.netk.async.annors.*
import com.mozhimen.componentk.netk.async.annors._Field
import com.mozhimen.componentk.netk.async.annors.methods.*
import com.mozhimen.componentk.netk.async.commons.INetKCall
import com.mozhimen.componentk.netk.async.mos.NetKRequest
import com.mozhimen.basick.extsk.throwIllegalStateException
import com.mozhimen.basick.utilk.UtilKDataType
import java.lang.reflect.*

/**
 * @ClassName MethodParser
 * @Description MethodParser
 * @Author mozhimen
 * @Date 2021/9/26 21:28
 * @Version 1.0
 */
class MethodParser(private val baseUrl: String, method: Method) {

    private lateinit var _relativeUrl: String
    private lateinit var _returnType: Type
    private var _replaceRelativeUrl: String? = null
    private var _cacheStrategy: Int = _CacheStrategy.NET_ONLY
    private var _domainUrl: String? = null
    private var _formPost = true
    private var _httpMethod = -1

    private var _headers: MutableMap<String, String> = mutableMapOf()
    private var _parameters: MutableMap<String, String> = mutableMapOf()

    companion object {
        fun parse(baseUrl: String, method: Method): MethodParser {
            return MethodParser(baseUrl, method)
        }
    }

    init {
        //parse method annotations such get,headers,post baseUrl
        parseMethodAnnotations(method)

        //parse method generic return type
        parseMethodReturnType(method)
    }

    /**
     * 新的请求
     * @param method Method
     * @param args Array<out Any>?
     * @return NetKRequest
     */
    fun newRequest(method: Method, args: Array<out Any>?): NetKRequest {
        val arguments: Array<Any> = args as Array<Any>? ?: arrayOf()
        parseMethodParameters(method, arguments)

        val request = NetKRequest()
        request.domainUrl = _domainUrl
        request.returnType = _returnType
        request.relativeUrl = _replaceRelativeUrl ?: _relativeUrl
        request.parameters = _parameters
        request.headers = _headers
        request.httpMethod = _httpMethod
        request.formPost = _formPost
        request.cacheStrategy = _cacheStrategy
        return request
    }

    /**
     * 转化注解
     * @param method Method
     */
    private fun parseMethodAnnotations(method: Method) {
        val annotations = method.annotations
        for (annotation in annotations) {
            when (annotation) {
                is _GET -> {
                    _relativeUrl = annotation.value
                    _httpMethod = _METHOD._GET
                }
                is _POST -> {
                    _relativeUrl = annotation.value
                    _httpMethod = _METHOD._POST
                    _formPost = annotation.isFormPost
                }
                is _PUT -> {
                    _formPost = annotation.formPost
                    _httpMethod = _METHOD._PUT
                    _relativeUrl = annotation.value
                }
                is _DELETE -> {
                    _httpMethod = _METHOD._DELETE
                    _relativeUrl = annotation.value
                }
                is _BaseUrl -> {
                    _domainUrl = annotation.value
                }
                is _CacheStrategy -> {
                    _cacheStrategy = annotation.value
                }
                is _Headers -> {
                    val headersArray = annotation.value
                    //@Headers("auth-token:token","accountId:123456")
                    for (header in headersArray) {
                        val colon = header.indexOf(":")
                        check(!(colon == 0 || colon == -1)) {
                            "@_Headers value must be in the form [form:value], but found [$header]"
                        }
                        val name = header.substring(0, colon)
                        val value = header.substring(colon + 1).trim()
                        _headers[name] = value
                    }
                }
                else -> {
                    "can't handle method annotation: ${annotation.javaClass}".throwIllegalStateException()
                }
            }
        }

        require(
            _httpMethod == _METHOD._GET || _httpMethod == _METHOD._POST ||
                    _httpMethod == _METHOD._PUT || _httpMethod == _METHOD._DELETE
        ) {
            "method ${method.name} must has one of _GET, _POST, _PUT, _DELETE"
        }

        if (_domainUrl == null) {
            _domainUrl = baseUrl
        }
    }


    /**
     * interface ApiService{
     *  @Headers("auth-token:token","accountId:123456")
     *  @BaseUrl("http://api.mozhimen.top/as/")
     *  @Post("/cities/{province}")
     *  @Get("/cities")
     *  fun listCities(@Path("province") province:Int ,@Filed("page") page:Int): CallK<JsonObject>
     * }
     * @param method Method
     */
    private fun parseMethodReturnType(method: Method) {
        require(method.returnType == INetKCall::class.java) {
            "method ${method.name} must be type of INetKCall::class"
        }
        val genericReturnType = method.genericReturnType
        if (genericReturnType is ParameterizedType) {
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1) {
                "method ${method.name} can only has one generic return type"
            }
            val argument = actualTypeArguments[0]
            require(validateGenericType(argument)) {
                "method ${method.name} generic return type must not be an unknown type."
            }
            _returnType = argument
        } else {
            "method ${method.name} must has one generic return type".throwIllegalStateException()
        }
    }

    /**
     * 注解类型是否合法
     * @param type Type
     * @return Boolean
     */
    private fun validateGenericType(type: Type): Boolean {
        /**
         *wrong
         *  fun test():HiCall<Any>
         *  fun test():HiCall<List<*>>
         *  fun test():HiCall<ApiInterface>
         *expect
         *  fun test():HiCall<User>
         */
        //如果指定的泛型是集合类型的，那还检查集合的泛型参数
        if (type is GenericArrayType) {
            return validateGenericType(type.genericComponentType)
        }
        //如果指定的泛型是一个接口 也不行
        if (type is TypeVariable<*>) {
            return false
        }
        //如果指定的泛型是一个通配符 ？extends Number 也不行
        if (type is WildcardType) {
            return false
        }

        return true
    }

    /**
     * 转化方法参数@Path("province") province:Int ,@Filed("page"
     * @param method Method
     * @param args Array<Any>
     */
    private fun parseMethodParameters(method: Method, args: Array<Any>) {
        //每次调用api接口时  应该吧上一次解析到的参数清理掉，因为methodParser存在复用
        _parameters.clear()

        //@Path("province") province:Int ,@Filed("page") page:Int
        val parameterAnnotations = method.parameterAnnotations
        val equals = parameterAnnotations.size == args.size
        require(equals) {
            "arguments annotations count ${parameterAnnotations.size} don't match except count ${args.size}"
        }
        //args
        for (index in args.indices) {
            val annotations = parameterAnnotations[index]
            require(annotations.size <= 1) {
                "field can only has one annotation: index = $index"
            }
            val value = args[index]
            require(UtilKDataType.isPrimitive(value)) {
                "8 basic types are supported for now, index = $index"
            }

            when (val annotation = annotations[0]) {
                is _Field -> {
                    val key = annotation.value
                    val value1 = args[index]
                    _parameters[key] = value1.toString()
                }
                is _Path -> {
                    val replaceName = annotation.value
                    val replacement = value.toString()
                    _replaceRelativeUrl = _relativeUrl.replace("{$replaceName}", replacement)
                }
                is _CacheStrategy -> {
                    _cacheStrategy = value as Int
                }
                else -> {
                    "can't handle parameter annotation: ${annotation.javaClass}".throwIllegalStateException()
                }
            }
        }
    }
}