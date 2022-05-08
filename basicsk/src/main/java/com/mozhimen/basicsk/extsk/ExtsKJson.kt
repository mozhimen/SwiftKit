package com.mozhimen.basicsk.extsk

import com.google.gson.reflect.TypeToken
import com.mozhimen.basicsk.utilk.UtilKJson

/**
 * @ClassName JsonUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/20 17:43
 * @Version 1.0
 */
/**
 * 转Json
 * @receiver Any
 * @return String
 */
fun Any.toJson(): String = UtilKJson.toJson(this)

/**
 * 转实体
 * @receiver String
 * @param token TypeToken<T>
 * @return T
 */
fun <T> String.fromJson(token: TypeToken<T>): T = UtilKJson.fromJson(this, token)

/**
 * 转实体
 * @receiver String
 * @param cls Class<T>
 * @return T
 */
fun <T> String.fromJson(cls: Class<T>): T = UtilKJson.fromJson(this, cls)