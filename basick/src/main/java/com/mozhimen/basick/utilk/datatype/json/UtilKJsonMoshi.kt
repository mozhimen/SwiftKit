package com.mozhimen.basick.utilk.datatype.json

import com.mozhimen.basick.utilk.java.UtilKGeneric
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

/**
 * @ClassName UtilKJson
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/14 20:23
 * @Version 1.0
 */
object UtilKJsonMoshi {

    val moshiBuilder = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    /**
     * 转json
     * @param t T
     * @param indent String 自定义字符例如
     * @return String
     * @throws Exception
     */
    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> t2Json(t: T, indent: String = ""): String =
        moshiBuilder.adapter<T>(UtilKGeneric.getGenericType<T>()!!).indent(indent).toJson(t)

    /**
     * 转T
     * @param json String
     * @return T?
     * @throws Exception
     */
    @Throws(Exception::class)
    @JvmStatic
    inline fun <reified T> json2T(json: String): T? =
        moshiBuilder.adapter<T>(UtilKGeneric.getGenericType<T>()!!).fromJson(json)
}