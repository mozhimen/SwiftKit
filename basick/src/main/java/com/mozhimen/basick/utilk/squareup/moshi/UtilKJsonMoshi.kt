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
fun <T : Any> T.toJsonMoshi(indent: String = ""): String =
    UtilKJsonMoshi.t2Json(this, indent)

@Throws(Exception::class)
fun <T> String.toTMoshi(): T? =
    UtilKJsonMoshi.json2T(this)

object UtilKJsonMoshi {

    val moshiBuilder by lazy { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2Json(adapter: JsonAdapter<T>, t: T, indent: String = ""): String =
        adapter.indent(indent).toJson(t)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2Json(t: T, indent: String = ""): String =
        t2Json(moshiBuilder.adapter(UtilKGeneric.getGenericType<T>()!!), t, indent)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2Json2(t: T, indent: String = ""): String =
        t2Json(moshiBuilder.adapter(T::class.java), t, indent)

    /**
     * 将 T 序列化为 json，指定 parameterizedType，适合复杂类型
     */
    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2Json(t: T, parameterizedType: ParameterizedType, indent: String = ""): String =
        t2Json(moshiBuilder.adapter(parameterizedType), t, indent)

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2T(adapter: JsonAdapter<T>, json: String): T? =
        adapter.fromJson(json)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2T(json: String, parameterizedType: ParameterizedType): T? =
        json2T(moshiBuilder.adapter<T>(parameterizedType), json)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2List(json: String): MutableList<T>? =
        json2T<MutableList<T>>(json, Types.newParameterizedType(MutableList::class.java, UtilKGeneric.getGenericType<T>()!!))

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> json2List2(json: String): MutableList<T>? =
        json2T<MutableList<T>>(json, Types.newParameterizedType(MutableList::class.java, T::class.java))

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2T(json: String): T? =
        json2T(moshiBuilder.adapter<T>(UtilKGeneric.getGenericType<T>()!!), json)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> json2T2(json: String): T? =
        json2T(moshiBuilder.adapter(T::class.java), json)


}