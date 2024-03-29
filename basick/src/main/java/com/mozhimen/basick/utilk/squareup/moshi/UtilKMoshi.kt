package com.mozhimen.basick.utilk.squareup.moshi

import com.squareup.moshi.JsonAdapter

/**
 * @ClassName UtilKMoshiWrapper
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/3/28 19:43
 * @Version 1.0
 */
object UtilKMoshi {
    @Throws(Exception::class)
    @JvmStatic
    fun <T> indent_toJson(adapter: JsonAdapter<T>, t: T, indent: String = ""): String =
        adapter.indent(indent).toJson(t)

    @Throws(Exception::class)
    @JvmStatic
    fun <T> fromJson(adapter: JsonAdapter<T>, strJson: String): T? =
        adapter.fromJson(strJson)
}