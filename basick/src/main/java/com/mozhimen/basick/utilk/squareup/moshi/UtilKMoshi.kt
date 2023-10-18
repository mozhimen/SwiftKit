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
inline fun <reified T : Any> T.t2jsonMoshi(indent: String = ""): String =
    UtilKMoshi.t2jsonMoshi(this, indent)

@Throws(Exception::class)
inline fun <reified T> String.jsonMoshi2t(): T? =
    UtilKMoshi.jsonMoshi2t(this)

object UtilKMoshi {

    val moshiBuilder by lazy { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2jsonMoshi(adapter: JsonAdapter<T>, t: T, indent: String = ""): String =
        adapter.indent(indent).toJson(t)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> jsonMoshi2t(adapter: JsonAdapter<T>, json: String): T? =
        adapter.fromJson(json)

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2jsonMoshi(t: T, indent: String = ""): String =
        t2jsonMoshi(moshiBuilder.adapter(UtilKReflectGenericKotlin.getGenericType<T>()!!), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2jsonMoshi2(t: T, indent: String = ""): String =
        t2jsonMoshi(moshiBuilder.adapter(T::class.java), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> jsonMoshi2t(json: String): T? =
        jsonMoshi2t(moshiBuilder.adapter<T>(UtilKReflectGenericKotlin.getGenericType<T>()!!), json)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> jsonMoshi2t2(json: String): T? =
        jsonMoshi2t(moshiBuilder.adapter(T::class.java), json)

    /////////////////////////////////////////////////////////////////////////////

    /**
     * 将 T 序列化为 json，指定 parameterizedType，适合复杂类型
     */
    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2jsonMoshi(t: T, parameterizedType: ParameterizedType, indent: String = ""): String =
        t2jsonMoshi(moshiBuilder.adapter(parameterizedType), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> jsonMoshi2t(json: String, parameterizedType: ParameterizedType): T? =
        jsonMoshi2t(moshiBuilder.adapter<T>(parameterizedType), json)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> jsonMoshi2list(json: String): MutableList<T>? =
        jsonMoshi2t<MutableList<T>>(json, Types.newParameterizedType(MutableList::class.java, UtilKReflectGenericKotlin.getGenericType<T>()!!))

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> jsonMoshi2list2(json: String): MutableList<T>? =
        jsonMoshi2t<MutableList<T>>(json, Types.newParameterizedType(MutableList::class.java, T::class.java))
}