package com.swiftmk.library.helper.activity

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * @ClassName StatusBarIniter1
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/9 17:15
 * @Version 1.0
 */

/***
 * 作用: 用于跳转
 * 用法:
 * 1.静态继承(目标Activity):
 * companion object : ActivityBridgerImpl {
 *  override fun actionStart(context: Context, pairs: Pair<String, Any?>) {
 *      ActivityBridger.actionStart(context, SecondActivity::class.java, pairs)}}
 *
 * 2.当前Activity:
 * val map = mapOf("param1" to "1", "param2" to "2")
 * SecondActivity.actionStart(this, map as HashMap<String, String>)
 */
interface ActivityBridgerImpl {
    fun actionStart(context: Context, values: ContentValues)
}

object ActivityBridger {
    /**
     * 作用: 数据封装
     * 用法:
     * val values = cvOf("name" to "...",...)
     * db.insert("Book", null, values)
     */
    fun paramsOf(vararg pairs: Pair<String, Any?>) = ExtraValues(pairs.size).apply {
        for ((key, value) in pairs) {
            when (value) {
                is Bundle -> put(key, value)
                is Boolean -> put(key, value)
                is Int -> put(key, value)
                is String -> put(key, value)
                null -> putNull(key)
                else -> {
                    val valueType = value.javaClass.canonicalName
                    throw IllegalArgumentException("Not support value type $valueType  in this function")
                }
            }
        }
    }

    fun paramsOfPlus(vararg pairs: Pair<String, Any?>) = ExtraValues(pairs.size).apply {
        for ((key, value) in pairs) {
            when (value) {
                is Bundle -> put(key, value)
                is Parcelable -> put(key, value)
                is Serializable -> put(key, value)
                is Boolean -> put(key, value)
                is BooleanArray -> put(key, value)
                is Byte -> put(key, value)
                is ByteArray -> put(key, value)
                is Char -> put(key, value)
                is CharArray -> put(key, value)
                is CharSequence -> put(key, value)
                is Double -> put(key, value)
                is DoubleArray -> put(key, value)
                is Float -> put(key, value)
                is FloatArray -> put(key, value)
                is Int -> put(key, value)
                is IntArray -> put(key, value)
                is Long -> put(key, value)
                is LongArray -> put(key, value)
                is Short -> put(key, value)
                is ShortArray -> put(key, value)
                is String -> put(key, value)
                null -> {
                    putNull(key)
                }
                else -> {
                    val valueType = value.javaClass.canonicalName
                    throw IllegalArgumentException("Illegal value type $valueType for key \"$key\"")
                }
            }
        }
    }

    fun actionStart(context: Context, cls: Class<*>, values: ExtraValues) {
        val map = values.getValues()
        val intent = Intent(context, cls).apply {
            if (map != null) {
                for ((key, value) in map){
                    putExtra(key,value)
                }
            }
        }
        context.startActivity(intent)
    }
}