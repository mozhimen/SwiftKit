package com.mozhimen.basicsmk.utilmk

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * @ClassName UtilMKSkip
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/2/27 16:35
 * @Version 1.0
 */
object UtilMKSkip {
    fun actionStart(
        context: Context,
        cls: Class<*>,
        vararg data: Pair<String, Any> = emptyArray(),
    ) {
        val intent = Intent(context, cls).apply {
            if (!data.isNullOrEmpty()) {
                data.forEach { (key, value) ->
                    when (value) {
                        is String -> putExtra(key, value)
                        is Int -> putExtra(key, value)
                        is Byte -> putExtra(key, value)
                        is Boolean -> putExtra(key, value)
                        is Char -> putExtra(key, value)
                        is Short -> putExtra(key, value)
                        is Long -> putExtra(key, value)
                        is Float -> putExtra(key, value)
                        is Double -> putExtra(key, value)
                        is Bundle -> putExtra(key, value)
                        else -> {
                            val valueType = value.javaClass.simpleName
                            throw IllegalArgumentException("Illegal value type $valueType for key $key")
                        }
                    }
                }
            }
        }
        context.startActivity(intent)
    }
}

fun Class<*>.start(context: Context) {
    Intent(context, this)
}