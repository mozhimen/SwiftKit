package com.mozhimen.swiftmk.helper.activity

import android.content.Context
import android.content.Intent
import java.security.InvalidParameterException

/**
 * @ClassName ActivityBridger
 * @Description TODO
 * @Author mozhimen
 * @Date 2021/4/22 9:13
 * @Version 1.0
 */
object ActivityBridger {
    fun actionStart(
        context: Context,
        cls: Class<*>,
        vararg data: Pair<String, Any> = emptyArray()
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
                        else -> {
                            val valueType = value.javaClass.canonicalName
                            throw IllegalArgumentException("Illegal value type $valueType for key $key")
                        }
                    }
                }
            }
        }
        context.startActivity(intent)
    }
}