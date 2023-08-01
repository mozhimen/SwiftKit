package com.mozhimen.basick.utilk.android.os

import android.annotation.SuppressLint
import java.lang.reflect.Method

/**
 * @ClassName UtilKSystemProperties
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/2 1:24
 * @Version 1.0
 */
object UtilKSystemProperties {
    @JvmStatic
    fun getByReflect(key: String): String {
        try {
            @SuppressLint("PrivateApi") val clazz = Class.forName("android.os.SystemProperties")
            val getMethod: Method = clazz.getMethod("get", String::class.java, String::class.java)
            return getMethod.invoke(clazz, key, "") as String
        } catch (e: Exception) { /**/
        }
        return ""
    }
}