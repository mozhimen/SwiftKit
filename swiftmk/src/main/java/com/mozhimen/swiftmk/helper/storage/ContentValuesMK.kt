package com.mozhimen.swiftmk.helper.storage

import android.content.ContentValues

/**
 * @ClassName ContentValues
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/14 17:10
 * @Version 1.0
 */

/**
 * 作用: ContentValues(数据库)数据封装
 * 用法:
 * val values = cvOf("name" to "...",...)
 * db.insert("Book", null, values)
 */
fun cvOf(vararg pairs: Pair<String, Any?>) = ContentValues(pairs.size).apply {
    for ((key, value) in pairs) {
        when (value) {
            is Int -> put(key, value)
            is Long -> put(key, value)
            is Float -> put(key, value)
            is Double -> put(key, value)
            is Boolean -> put(key, value)
            is String -> put(key, value)
            is Byte -> put(key, value)
            is ByteArray -> put(key, value)
            null -> putNull(key)
            else -> {
                val valueType = value.javaClass.canonicalName
                throw IllegalArgumentException("Illegal value type $valueType for key \"$key\"")
            }
        }
    }
}