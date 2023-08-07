package com.mozhimen.basick.utilk.squareup.moshi

import com.mozhimen.basick.utilk.java.lang.UtilKGeneric
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
inline fun <reified T : Any> T.t2json(indent: String = ""): String =
    UtilKJsonMoshi.t2json(this, indent)

@Throws(Exception::class)
inline fun <reified T> String.json2t(): T? =
    UtilKJsonMoshi.json2t(this)

object UtilKJsonMoshi {

    val moshiBuilder by lazy { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2json(adapter: JsonAdapter<T>, t: T, indent: String = ""): String =
        adapter.indent(indent).toJson(t)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2json(t: T, indent: String = ""): String =
        t2json(moshiBuilder.adapter(UtilKGeneric.getGenericType<T>()!!), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2json2(t: T, indent: String = ""): String =
        t2json(moshiBuilder.adapter(T::class.java), t, indent)

    /**
     * 将 T 序列化为 json，指定 parameterizedType，适合复杂类型
     */
    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2json(t: T, parameterizedType: ParameterizedType, indent: String = ""): String =
        t2json(moshiBuilder.adapter(parameterizedType), t, indent)

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> json2t(json: String): T? =
        json2t(moshiBuilder.adapter<T>(UtilKGeneric.getGenericType<T>()!!), json)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> json2t2(json: String): T? =
        json2t(moshiBuilder.adapter(T::class.java), json)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2t(adapter: JsonAdapter<T>, json: String): T? =
        adapter.fromJson(json)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2t(json: String, parameterizedType: ParameterizedType): T? =
        json2t(moshiBuilder.adapter<T>(parameterizedType), json)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> json2list(json: String): MutableList<T>? =
        json2t<MutableList<T>>(json, Types.newParameterizedType(MutableList::class.java, UtilKGeneric.getGenericType<T>()!!))

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> json2list2(json: String): MutableList<T>? =
        json2t<MutableList<T>>(json, Types.newParameterizedType(MutableList::class.java, T::class.java))
}