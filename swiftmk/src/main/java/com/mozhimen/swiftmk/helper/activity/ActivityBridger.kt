package com.mozhimen.swiftmk.helper.activity

import android.content.Context
import android.content.Intent
<<<<<<< HEAD
import android.os.Bundle
=======
>>>>>>> b9e6f72a089183e4e672c0e1fda33ff2a94e8327
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
<<<<<<< HEAD
        vararg data: Pair<String, Any> = emptyArray(),
=======
        vararg data: Pair<String, Any> = emptyArray()
>>>>>>> b9e6f72a089183e4e672c0e1fda33ff2a94e8327
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
<<<<<<< HEAD
                        is Bundle -> putExtra(key, value)
=======
>>>>>>> b9e6f72a089183e4e672c0e1fda33ff2a94e8327
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