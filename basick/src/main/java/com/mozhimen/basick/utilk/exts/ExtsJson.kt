package com.mozhimen.basick.utilk.exts

import com.google.gson.reflect.TypeToken
import com.mozhimen.basick.utilk.java.datatype.json.UtilKJsonGson
import com.mozhimen.basick.utilk.java.datatype.json.UtilKJsonMoshi

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
inline fun <reified T : Any> T.t2JsonMoshi(indent: String = ""): String = UtilKJsonMoshi.t2Json(this, indent)

/**
 * 转实体
 * @receiver String
 * @return T?
 * @throws Exception
 */
@Throws(Exception::class)
inline fun <reified T> String.json2TMoshi(): T? = UtilKJsonMoshi.json2T(this)

/**
 * 转Json
 * @receiver Any
 * @return String
 */
fun Any.obj2JsonGson(): String = UtilKJsonGson.obj2Json(this)

/**
 * 转实体
 * @receiver String
 * @param token TypeToken<T>
 * @return T
 */
fun <T> String.json2TGson(token: TypeToken<T>): T = UtilKJsonGson.json2T(this, token)

/**
 * 转实体
 * @receiver String
 * @param clazz Class<T>
 * @return T
 */
fun <T> String.json2TGson(clazz: Class<T>): T? = UtilKJsonGson.json2T(this, clazz)

/**
 * 转Json
 * @receiver Any
 * @return String
 */
fun Any.obj2JsonGsonWithExpose(): String = UtilKJsonGson.obj2JsonWithExpose(this)

/**
 * 转实体
 * @receiver String
 * @param clazz Class<T>
 * @return T
 */
fun <T> String.json2TGsonWithExpose(clazz: Class<T>): T? = UtilKJsonGson.json2TWithExpose(this, clazz)