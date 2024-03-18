package com.mozhimen.basick.utilk.android.os

import android.annotation.SuppressLint
import com.mozhimen.basick.utilk.android.util.UtilKLogWrapper
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz

import java.lang.reflect.Method

/**
 * @ClassName UtilKCmd
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 12:31
 * @Version 1.0
 */
object UtilKSystemProperties : BaseUtilK() {

    @SuppressLint("PrivateApi")
    @JvmStatic
    fun getStrStr(strPackage: String, defaultValue: String = ""): String {
        try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodGet: Method = clazz.getMethod("get", String::class.java, String::class.java)
            return methodGet.invoke(clazz, strPackage, defaultValue) as String
        } catch (e: Exception) { /**/
            e.printStackTrace()
            e.message?.e(TAG)
        }
        return ""
    }

    /**
     * 获取首选项
     */
    @JvmStatic
    fun getStr(strPackage: String, defaultValue: String): String =
        try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodGet: Method = clazz.getMethod("get", String::class.java)
            (methodGet.invoke(clazz, strPackage) as String).ifEmpty { defaultValue }
        } catch (e: Exception) {
            UtilKLogWrapper.e(TAG, "get Exception ${e.message}")
            e.printStackTrace()
            defaultValue
        }

    /**
     * 获取首选项
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun getStrOfBool(strPackage: String, defaultValue: Boolean): Boolean =
        try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodGet: Method = clazz.getMethod("get", String::class.java)
            val str = methodGet.invoke(clazz, strPackage) as String
            if (str.isNotEmpty()) java.lang.Boolean.parseBoolean(str) else defaultValue
        } catch (e: Exception) {
            UtilKLogWrapper.e(TAG, "getSystemProperties Exception ${e.message}")
            e.printStackTrace()
            defaultValue
        }

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置首选项
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun setStrStr(key: String, value: String) {
        try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodSet: Method = clazz.getMethod("set", String::class.java, String::class.java)
            methodSet.invoke(clazz, key, value)
        } catch (e: Exception) {
            UtilKLogWrapper.e(TAG, "apply Exception ${e.message}")
            e.printStackTrace()
        }
    }
}