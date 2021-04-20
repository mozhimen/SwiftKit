package com.mozhimen.swiftmk.utils.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @ClassName JsonUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/20 17:43
 * @Version 1.0
 */
object JsonUtil {
    fun toJson(obj: Any): String = Gson().toJson(obj)

    fun <T> fromJson(json: String, token: TypeToken<T>): T = Gson().fromJson(json, token.type)

    fun <T> fromJson(json: String, cls: Class<T>): T = Gson().fromJson(json, cls)
}