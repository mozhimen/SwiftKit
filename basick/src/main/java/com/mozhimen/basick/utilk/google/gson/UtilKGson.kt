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
fun <T : Any> T.t2strJsonGson(): String =
    UtilKGson.t2strJsonGson(this)

@Throws(Exception::class)
inline fun <reified T> String.strJsonGson2t(): T? =
    UtilKGson.strJsonGson2t(this)

fun <T> String.strJsonGson2t(clazz: Class<T>): T? =
    UtilKGson.strJsonGson2t(this, clazz)

object UtilKGson : BaseUtilK() {
    val gson by lazy { Gson() }

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2strJsonGson(gson: Gson, t: T): String =
        gson.toJson(t)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJsonGson2t(gson: Gson, strJson: String, typeToken: TypeToken<T>): T =
        gson.fromJson(strJson, typeToken.type)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJsonGson2t(gson: Gson, strJson: String, clazz: Class<T>): T? =
        gson.fromJson(strJson, clazz)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJsonGson2t(gson: Gson, strJson: String, type: Type): T? =
        gson.fromJson(strJson, type)

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2strJsonGson(t: T): String =
        t2strJsonGson(gson, t)

    @Throws(Exception::class)
    @JvmStatic
    fun obj2strJsonGson(obj: Any): String =
        t2strJsonGson(obj)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJsonGson2t(strJson: String, typeToken: TypeToken<T>): T =
        strJsonGson2t(gson, strJson, typeToken)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJsonGson2t(strJson: String, clazz: Class<T>): T? =
        strJsonGson2t(gson, strJson, clazz)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> strJsonGson2t(strJson: String, type: Type): T? =
        strJsonGson2t(gson, strJson, type)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJsonGson2t(strJson: String): T? =
        strJsonGson2t(strJson, UtilKReflectGenericKotlin.getGenericType<T>()!!)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> strJsonGson2t2(strJson: String): T? =
        strJsonGson2t(strJson, T::class.java)

    ///////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun strJsonGson2jsonElement(strJson: String): JsonElement? =
        strJsonGson2t(strJson.trim { it <= ' ' }, JsonElement::class.java)
}

//    private val _gsonWithField by lazy { GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create() }
//    private val _gsonWithExpose by lazy { GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() }
//
//    @JvmStatic
//    fun obj2JsonWithField(obj: Any): String =
//        _gsonWithField.toJson(obj)
//
//    @JvmStatic
//    fun <T> json2TWithField(strJson: String, clazz: Class<T>): T =
//        _gsonWithField.fromJson(strJson, clazz)
//
//    @JvmStatic
//    fun obj2JsonWithExpose(obj: Any): String =
//        _gsonWithExpose.toJson(obj)
//
//    @JvmStatic
//    fun <T> json2TWithExpose(strJson: String, clazz: Class<T>): T? =
//        _gsonWithExpose.fromJson(strJson, clazz)

//fun Any.toJsonWithExposeGson(): String =
//    UtilKJsonGson.obj2JsonWithExpose(this)
//
//fun <T> String.toTWithExposeGson(clazz: Class<T>): T? =
//    UtilKJsonGson.json2TWithExpose(this, clazz)