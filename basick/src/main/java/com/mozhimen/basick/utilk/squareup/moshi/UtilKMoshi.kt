package com.mozhimen.basick.utilk.squareup.moshi

import com.mozhimen.basick.utilk.java.lang.UtilKReflectGenericKotlin
import com.squareup.moshi.JsonAdapter
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
inline fun <reified T : Any> T.t2strJsonMoshi(indent: String = ""): String =
    UtilKMoshi.t2strJsonMoshi(this, indent)

@Throws(Exception::class)
inline fun <reified T> String.strJson2tMoshi(): T? =
    UtilKMoshi.strJson2tMoshi(this)

object UtilKMoshi {

    val moshiBuilder by lazy { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2strJsonMoshi(adapter: JsonAdapter<T>, t: T, indent: String = ""): String =
        adapter.indent(indent).toJson(t)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJson2tMoshi(adapter: JsonAdapter<T>, strJson: String): T? =
        adapter.fromJson(strJson)

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2strJsonMoshi(t: T, indent: String = ""): String =
        t2strJsonMoshi(moshiBuilder.adapter(UtilKReflectGenericKotlin.getGenericType<T>()!!), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2strJsonMoshi2(t: T, indent: String = ""): String =
        t2strJsonMoshi(moshiBuilder.adapter(T::class.java), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2tMoshi(strJson: String): T? =
        strJson2tMoshi(moshiBuilder.adapter<T>(UtilKReflectGenericKotlin.getGenericType<T>()!!), strJson)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJson2tMoshi2(strJson: String): T? =
        strJson2tMoshi(moshiBuilder.adapter(T::class.java), strJson)

    /////////////////////////////////////////////////////////////////////////////

    /**
     * 将 T 序列化为 strJson，指定 parameterizedType，适合复杂类型
     */
    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2strJsonMoshi(t: T, parameterizedType: ParameterizedType, indent: String = ""): String =
        t2strJsonMoshi(moshiBuilder.adapter(parameterizedType), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJson2tMoshi(strJson: String, parameterizedType: ParameterizedType): T? =
        strJson2tMoshi(moshiBuilder.adapter<T>(parameterizedType), strJson)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJsonMoshi2list(strJson: String): MutableList<T>? =
        strJson2tMoshi<MutableList<T>>(strJson, Types.newParameterizedType(MutableList::class.java, UtilKReflectGenericKotlin.getGenericType<T>()!!))

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJsonMoshi2list2(strJson: String): MutableList<T>? =
        strJson2tMoshi<MutableList<T>>(strJson, Types.newParameterizedType(MutableList::class.java, T::class.java))
}