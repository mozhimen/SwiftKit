package com.mozhimen.basick.utilk.squareup.moshi

import com.mozhimen.basick.utilk.java.lang.UtilKReflectGenericKotlin
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType

/**
 * @ClassName UtilKJson
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/14 20:23
 * @Version 1.0
 */

@Throws(Exception::class)
inline fun <reified T : Any> T.t2strJson_ofMoshi(indent: String = ""): String =
    UtilKMoshiWrapper.t2strJson_ofMoshi(this, indent)

@Throws(Exception::class)
inline fun <reified T> String.strJson2t_ofMoshi(): T? =
    UtilKMoshiWrapper.strJson2t_ofMoshi(this)

object UtilKMoshiWrapper {

    val moshiBuilder by lazy { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2strJson_ofMoshi(t: T, indent: String = ""): String =
        UtilKMoshi.indent_toJson(moshiBuilder.adapter(UtilKReflectGenericKotlin.getGenericType<T>()!!), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2strJson_ofMoshi_ofReified(t: T, indent: String = ""): String =
        UtilKMoshi.indent_toJson(moshiBuilder.adapter(T::class.java), t, indent)

    //将 T 序列化为 strJson，指定 parameterizedType，适合复杂类型
    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2strJson_ofMoshi(t: T, parameterizedType: ParameterizedType, indent: String = ""): String =
        UtilKMoshi.indent_toJson(moshiBuilder.adapter(parameterizedType), t, indent)

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2t_ofMoshi(strJson: String): T? =
        UtilKMoshi.fromJson(moshiBuilder.adapter<T>(UtilKReflectGenericKotlin.getGenericType<T>()!!), strJson)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2t_ofMoshi_ofReified(strJson: String): T? =
        UtilKMoshi.fromJson(moshiBuilder.adapter(T::class.java), strJson)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJson2t_ofMoshi(strJson: String, parameterizedType: ParameterizedType): T? =
        UtilKMoshi.fromJson(moshiBuilder.adapter<T>(parameterizedType), strJson)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2list_ofMoshi(strJson: String): MutableList<T>? =
        strJson2t_ofMoshi<MutableList<T>>(strJson, Types.newParameterizedType(MutableList::class.java, UtilKReflectGenericKotlin.getGenericType<T>()!!))

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2list_ofMoshi_ofReified(strJson: String): MutableList<T>? =
        strJson2t_ofMoshi<MutableList<T>>(strJson, Types.newParameterizedType(MutableList::class.java, T::class.java))
}