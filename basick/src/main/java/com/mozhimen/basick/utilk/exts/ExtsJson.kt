package com.mozhimen.basick.utilk.exts

import com.google.gson.reflect.TypeToken
import com.mozhimen.basick.utilk.json.UtilKJsonGson
import com.mozhimen.basick.utilk.json.UtilKJsonMoshi

/**
 * @ClassName JsonUtil
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/20 17:43
 * @Version 1.0
 */
/**
 * 转Json
 * @receiver T
 * @param indent String
 * @return String
 * @throws Exception
 */
@Throws(Exception::class)
inline fun <reified T : Any> T.toJsonMoshi(indent: String = ""): String = UtilKJsonMoshi.t2Json(this, indent)

/**
 * 转实体
 * @receiver String
 * @return T?
 * @throws Exception
 */
@Throws(Exception::class)
inline fun <reified T> String.fromJsonMoshi(): T? = UtilKJsonMoshi.json2T(this)

/**
 * 转Json
 * @receiver Any
 * @return String
 */
fun Any.toJsonGson(): String = UtilKJsonGson.t2Json(this)

/**
 * 转实体
 * @receiver String
 * @param token TypeToken<T>
 * @return T
 */
fun <T> String.fromJsonGson(token: TypeToken<T>): T = UtilKJsonGson.json2T(this, token)

/**
 * 转实体
 * @receiver String
 * @param cls Class<T>
 * @return T
 */
fun <T> String.fromJsonGson(cls: Class<T>): T = UtilKJsonGson.json2T(this, cls)