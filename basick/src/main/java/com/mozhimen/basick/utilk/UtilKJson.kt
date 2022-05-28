package com.mozhimen.basick.utilk

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @ClassName UtilKJson
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/4/14 20:23
 * @Version 1.0
 */
object UtilKJson {
    /**
     * 转Json
     * @param obj Any
     * @return String
     */
    fun <T> toJson(obj: T): String = Gson().toJson(obj)

    /**
     * 从Json转
     * @param json String
     * @param token TypeToken<T>
     * @return T
     */
    fun <T> fromJson(json: String, token: TypeToken<T>): T = Gson().fromJson(json, token.type)

    /**
     * 从Json转
     * @param json String
     * @param cls Class<T>
     * @return T
     */
    fun <T> fromJson(json: String, cls: Class<T>): T = Gson().fromJson(json, cls)
}