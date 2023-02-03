package com.mozhimen.basick.utilk.json

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken

/**
 * @ClassName UtilKJsonGson
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/3 17:21
 * @Version 1.0
 */
object UtilKJsonGson {
        /**
     * 转Json
     * @param t Any
     * @return String
     */
    @JvmStatic
    fun <T> t2Json(t: T): String =
        Gson().toJson(t)

    /**
     * obj转Json
     * @param any Any
     * @return String
     */
    @JvmStatic
    fun any2Json(any: Any): String =
        Gson().toJson(any)

    /**
     * 从Json转
     * @param json String
     * @param token TypeToken<T>
     * @return T
     */
    @JvmStatic
    fun <T> json2T(json: String, token: TypeToken<T>): T =
        Gson().fromJson(json, token.type)

    /**
     * 从Json转
     * @param json String
     * @param cls Class<T>
     * @return T
     */
    @JvmStatic
    fun <T> json2T(json: String, cls: Class<T>): T =
        Gson().fromJson(json, cls)

    /**
     * obj转jsonStr
     * @param obj Any
     * @return String
     */
    @JvmStatic
    fun obj2JsonOnField(obj: Any): String =
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).create().toJson(obj)

    /**
     * json转JsonEle
     * @param json String
     * @return JsonElement?
     */
    @JvmStatic
    fun json2JsonElement(json: String): JsonElement? = try {
        UtilKJsonGson.json2T(json.trim { it <= ' ' }, JsonElement::class.java)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}