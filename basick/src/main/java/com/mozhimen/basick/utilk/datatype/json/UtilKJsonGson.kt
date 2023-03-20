package com.mozhimen.basick.utilk.datatype.json

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.exts.et

/**
 * @ClassName UtilKJsonGson
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 17:21
 * @Version 1.0
 */
object UtilKJsonGson : BaseUtilK() {
    private val _gson by lazy { Gson() }
    private val _gsonWithField by lazy { GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create() }
    private val _gsonWithExpose: Gson by lazy { GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() }

    /**
     * t转Json
     * @param t Any
     * @return String
     */
    @JvmStatic
    fun <T> t2Json(t: T): String =
        _gson.toJson(t)

    /**
     * obj转Json
     * @param obj Any
     * @return String
     */
    @JvmStatic
    fun obj2Json(obj: Any): String =
        _gson.toJson(obj)

    /**
     * 从Json转
     * @param json String
     * @param token TypeToken<T>
     * @return T
     */
    @JvmStatic
    fun <T> json2T(json: String, token: TypeToken<T>): T =
        _gson.fromJson(json, token.type)

    /**
     * 从Json转
     * @param json String
     * @param clazz Class<T>
     * @return T
     */
    @JvmStatic
    fun <T> json2T(json: String, clazz: Class<T>): T? =
        _gson.fromJson(json, clazz)

    /**
     * obj转json
     * @param obj Any
     * @return String
     */
    @JvmStatic
    fun obj2JsonWithField(obj: Any): String =
        _gsonWithField.toJson(obj)

    /**
     * 从Json转T
     * @param json String
     * @param clazz Class<T>
     * @return T
     */
    @JvmStatic
    fun <T> json2TWithField(json: String, clazz: Class<T>): T =
        _gsonWithField.fromJson(json, clazz)

    /**
     * obj转json
     * @param obj Any
     * @return String
     */
    @JvmStatic
    fun obj2JsonWithExpose(obj: Any): String =
        _gsonWithExpose.toJson(obj)

    /**
     * 从Json转T
     * @param json String
     * @param clazz Class<T>
     * @return T
     */
    @JvmStatic
    fun <T> json2TWithExpose(json: String, clazz: Class<T>): T? =
        _gsonWithExpose.fromJson(json, clazz)

    /**
     * json转JsonElement
     * @param json String
     * @return JsonElement?
     */
    @JvmStatic
    fun json2JsonElement(json: String): JsonElement? = try {
        json2T(json.trim { it <= ' ' }, JsonElement::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        e.message?.et(TAG)
        null
    }
}