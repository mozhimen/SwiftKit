package com.mozhimen.basick.utilk.google.gson

import com.google.gson.Gson
import java.lang.reflect.Type
import kotlin.jvm.Throws

/**
 * @ClassName UtilKGson
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/27
 * @Version 1.0
 */
object UtilKGson {
    @JvmStatic
    @Throws(Exception::class)
    fun <T> toJson(gson: Gson, t: T): String =
        gson.toJson(t)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> fromJson(gson: Gson, strJson: String, clazz: Class<T>): T? =
        gson.fromJson(strJson, clazz)

    @JvmStatic
    @Throws(Exception::class)
    fun <T> fromJson(gson: Gson, strJson: String, typeOfT: Type): T? =
        gson.fromJson(strJson, typeOfT)
}