package com.mozhimen.basick.utilk.google.gson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.lang.UtilKReflectGenericKotlin
import java.lang.reflect.Type
import kotlin.jvm.Throws

/**
 * @ClassName UtilKJsonGson
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 17:21
 * @Version 1.0
 */
@Throws(Exception::class)
fun <T : Any> T.t2jsonGson(): String =
    UtilKGson.t2jsonGson(this)

@Throws(Exception::class)
inline fun <reified T> String.jsonGson2t(): T? =
    UtilKGson.jsonGson2t(this)

fun <T> String.jsonGson2t(clazz: Class<T>): T? =
    UtilKGson.jsonGson2t(this, clazz)

object UtilKGson : BaseUtilK() {
    val gson by lazy { Gson() }

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2jsonGson(gson: Gson, t: T): String =
        gson.toJson(t)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> jsonGson2t(gson: Gson, json: String, typeToken: TypeToken<T>): T =
        gson.fromJson(json, typeToken.type)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> jsonGson2t(gson: Gson, json: String, clazz: Class<T>): T? =
        gson.fromJson(json, clazz)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> jsonGson2t(gson: Gson, json: String, type: Type): T? =
        gson.fromJson(json, type)

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2jsonGson(t: T): String =
        t2jsonGson(gson, t)

    @Throws(Exception::class)
    @JvmStatic
    fun obj2jsonGson(obj: Any): String =
        t2jsonGson(obj)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> jsonGson2t(json: String, typeToken: TypeToken<T>): T =
        jsonGson2t(gson, json, typeToken)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> jsonGson2t(json: String, clazz: Class<T>): T? =
        jsonGson2t(gson, json, clazz)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> jsonGson2t(json: String, type: Type): T? =
        jsonGson2t(gson, json, type)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> jsonGson2t(json: String): T? =
        jsonGson2t(json, UtilKReflectGenericKotlin.getGenericType<T>()!!)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> jsonGson2t2(json: String): T? =
        jsonGson2t(json, T::class.java)

    ///////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun jsonGson2jsonElement(json: String): JsonElement? =
        jsonGson2t(json.trim { it <= ' ' }, JsonElement::class.java)
}

//    private val _gsonWithField by lazy { GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create() }
//    private val _gsonWithExpose by lazy { GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() }
//
//    @JvmStatic
//    fun obj2JsonWithField(obj: Any): String =
//        _gsonWithField.toJson(obj)
//
//    @JvmStatic
//    fun <T> json2TWithField(json: String, clazz: Class<T>): T =
//        _gsonWithField.fromJson(json, clazz)
//
//    @JvmStatic
//    fun obj2JsonWithExpose(obj: Any): String =
//        _gsonWithExpose.toJson(obj)
//
//    @JvmStatic
//    fun <T> json2TWithExpose(json: String, clazz: Class<T>): T? =
//        _gsonWithExpose.fromJson(json, clazz)

//fun Any.toJsonWithExposeGson(): String =
//    UtilKJsonGson.obj2JsonWithExpose(this)
//
//fun <T> String.toTWithExposeGson(clazz: Class<T>): T? =
//    UtilKJsonGson.json2TWithExpose(this, clazz)