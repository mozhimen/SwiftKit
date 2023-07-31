package com.mozhimen.basick.utilk.google.gson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.lang.UtilKGeneric
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
fun <T : Any> T.asJsonGson(): String =
    UtilKJsonGson.t2json(this)

@Throws(Exception::class)
fun <T> String.asTGson(): T? =
    UtilKJsonGson.json2t(this)

//fun Any.toJsonWithExposeGson(): String =
//    UtilKJsonGson.obj2JsonWithExpose(this)
//
//fun <T> String.toTWithExposeGson(clazz: Class<T>): T? =
//    UtilKJsonGson.json2TWithExpose(this, clazz)

object UtilKJsonGson : BaseUtilK() {
    val gson by lazy { Gson() }

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2json(gson: Gson, t: T): String =
        gson.toJson(t)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> t2json(t: T): String =
        t2json(gson, t)

    @Throws(Exception::class)
    @JvmStatic
    fun obj2json(obj: Any): String =
        t2json(obj)

    /////////////////////////////////////////////////////////////////////////////

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2t(gson: Gson, json: String, typeToken: TypeToken<T>): T =
        gson.fromJson(json, typeToken.type)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2t(gson: Gson, json: String, clazz: Class<T>): T? =
        gson.fromJson(json, clazz)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2t(gson: Gson, json: String, type: Type): T? =
        gson.fromJson(json, type)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2t(json: String, typeToken: TypeToken<T>): T =
        json2t(gson, json, typeToken)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2t(json: String, clazz: Class<T>): T? =
        json2t(gson, json, clazz)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2t(json: String, type: Type): T? =
        json2t(gson, json, type)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> json2t(json: String): T? =
        json2t(json, UtilKGeneric.getGenericType<T>()!!)

    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> json2t2(json: String): T? =
        json2t(json, T::class.java)

    ///////////////////////////////////////////////////////////////////////////////

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

    @Throws(Exception::class)
    @JvmStatic
    fun json2jsonElement(json: String): JsonElement? =
        json2t(json.trim { it <= ' ' }, JsonElement::class.java)
}