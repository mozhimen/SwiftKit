package com.mozhimen.abilityk.restfulk.helpers

import com.mozhimen.abilityk.restfulk.annors.*
import com.mozhimen.abilityk.restfulk.annors._Field
import com.mozhimen.abilityk.restfulk.annors.methods.*
import com.mozhimen.abilityk.restfulk.commons._ICall
import com.mozhimen.abilityk.restfulk.mos._Request
import java.lang.reflect.*

/**
 * @ClassName MethodParser
 * @Description MethodParser
 * @Author mozhimen
 * @Date 2021/9/26 21:28
 * @Version 1.0
 */
class MethodParser(private val baseUrl: String, method: Method) {

    private lateinit var relativeUrl: String
    private var replaceRelativeUrl: String? = null
    private var cacheStrategy: Int = _CacheStrategy.NET_ONLY
    private var domainUrl: String? = null
    private var formPost = true
    private var httpMethod = -1
    private lateinit var returnType: Type

    private var headers: MutableMap<String, String> = mutableMapOf()
    private var parameters: MutableMap<String, String> = mutableMapOf()

    init {
        //parse method annotations such get,headers,post baseUrl
        parseMethodAnnotations(method)

        //parse method generic return type
        parseMethodReturnType(method)
    }

    fun newRequest(method: Method, args: Array<out Any>?): _Request{
        val arguments: Array<Any> = args as Array<Any>? ?: arrayOf()
        parseMethodParameters(method, arguments)

        return _Request(
            httpMethod,
            headers,
            parameters,
            domainUrl,
            returnType,
            relativeUrl,
            formPost,
            cacheStrategy
        )
    }

    companion object {
        fun parse(baseUrl: String, method: Method): MethodParser {
            return MethodParser(baseUrl, method)
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
     */
    private fun parseMethodReturnType(method: Method) {
        require(method.returnType == _ICall::class.java) {
            "method ${method.name} must be type of CallK::class"
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
            returnType = argument
        } else {
            "method ${method.name} must has one generic return type".throwIllegalStateException()
        }
    }

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
     * @Path("province") province:Int ,@Filed("page"
     */
    private fun parseMethodParameters(method: Method, args: Array<Any>) {
        //每次调用api接口时  应该吧上一次解析到的参数清理掉，因为methodParser存在复用
        parameters.clear()

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
            require(isPrimitive(value)) {
                "8 basic types are supported for now, index = $index"
            }

            when (val annotation = annotations[0]) {
                is _Field -> {
                    val key = annotation.value
                    val value1 = args[index]
                    parameters[key] = value1.toString()
                }
                is _Path -> {
                    val replaceName = annotation.value
                    val replacement = value.toString()
                    replaceRelativeUrl = relativeUrl.replace("{$replaceName}", replacement)
                }
                is _CacheStrategy -> {
                    cacheStrategy = value as Int
                }
                else -> {
                    "can't handle parameter annotation: ${annotation.javaClass}".throwIllegalStateException()
                }
            }
        }
    }

    private fun isPrimitive(value: Any): Boolean {
        //String
        if (value.javaClass == String::class.java) {
            return true
        }
        try {
            //只适用于int byte short long boolean char double float
            val field = value.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }
        return false
    }

    private fun parseMethodAnnotations(method: Method) {
        val annotations = method.annotations
        for (annotation in annotations) {
            when (annotation) {
                is _GET -> {
                    relativeUrl = annotation.value
                    httpMethod = _METHOD._GETK
                }
                is _POST -> {
                    relativeUrl = annotation.value
                    httpMethod = _METHOD._POSTK
                    formPost = annotation.isFormPost
                }
                is _PUT -> {
                    formPost = annotation.formPost
                    httpMethod = _METHOD._PUTK
                    relativeUrl = annotation.value
                }
                is _DELETE -> {
                    httpMethod = _METHOD._DELETEK
                    relativeUrl = annotation.value
                }
                is _BaseUrl -> {
                    domainUrl = annotation.value
                }
                is _CacheStrategy -> {
                    cacheStrategy = annotation.value
                }
                is _Headers -> {
                    val headersArray = annotation.value
                    //@Headers("auth-token:token","accountId:123456")
                    for (header in headersArray) {
                        val colon = header.indexOf(":")
                        check(!(colon == 0 || colon == -1)) {
                            "@HeadersK value must be in the form [form:value], but found [$header]"
                        }
                        val name = header.substring(0, colon)
                        val value = header.substring(colon + 1).trim()
                        headers[name] = value
                    }
                }
                else -> {
                    "can't handle method annotation: ${annotation.javaClass}".throwIllegalStateException()
                }
            }
        }

        require(
            httpMethod == _METHOD._GETK ||
                    httpMethod == _METHOD._POSTK ||
                    httpMethod == _METHOD._PUTK ||
                    httpMethod == _METHOD._DELETEK
        ) {
            "method ${method.name} must has one of GETK, POSTK, PUTK, DELETEK"
        }

        if (domainUrl == null) {
            domainUrl = baseUrl
        }
    }

    private fun String.throwIllegalStateException() {
        throw IllegalStateException(this)
    }
}