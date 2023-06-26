package com.mozhimen.basick.utilk.dalvik.system

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.bases.BaseUtilK

import java.lang.reflect.Method

/**
 * @ClassName UtilKCmd
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 12:31
 * @Version 1.0
 */
object UtilKSystemProperties : BaseUtilK() {

    /**
     * 设备Rom版本
     * @return String
     */
    @JvmStatic
    fun getRomVersion(): String =
        getSystemProperties(CPackage.RO_PRODUCT_ROM_VERSION, CPackage.UNKNOWN)

    /**
     * 设备硬件版本
     * @return String
     */
    @JvmStatic
    fun getHardwareVersion(): String =
        getSystemProperties(CPackage.RO_PRODUCT_HW_VERSION, CPackage.UNKNOWN)

    /**
     * 序列号
     * @return String
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    fun getSerialNumber(): String = if (Build.VERSION.SDK_INT >= CVersionCode.V_29_10_Q) {
        CPackage.UNKNOWN
    } else if (Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O) {
        Build.SERIAL
    } else {
        getSystemProperties(CPackage.RO_SERIAL_NO, CPackage.UNKNOWN)
    }

    /**
     * 设置首选项
     * @param key String?
     * @param value String?
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun setSystemProperties(key: String, value: String) {
        try {
            val clazz = Class.forName(CPackage.ANDROID_OS_SYSTEM_PROPERTIES)
            val setMethod: Method = clazz.getMethod("set", String::class.java, String::class.java)
            setMethod.invoke(clazz, key, value)
        } catch (e: Exception) {
            Log.e(TAG, "setSystemProperties Exception ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * 获取首选项
     * @param key String?
     * @param defaultValue String
     * @return String
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun getSystemProperties(key: String, defaultValue: String): String =
        try {
            val clazz = Class.forName(CPackage.ANDROID_OS_SYSTEM_PROPERTIES)
            val getMethod: Method = clazz.getMethod("get", String::class.java)
            (getMethod.invoke(clazz, key) as String).ifEmpty { defaultValue }
        } catch (e: Exception) {
            Log.e(TAG, "getSystemProperties Exception ${e.message}")
            e.printStackTrace()
            defaultValue
        }

    /**
     * 获取首选项
     * @param key String?
     * @param defaultValue String
     * @return String
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun getSystemPropertiesBool(key: String, defaultValue: Boolean): Boolean =
        try {
            val clazz = Class.forName(CPackage.ANDROID_OS_SYSTEM_PROPERTIES)
            val getMethod: Method = clazz.getMethod("get", String::class.java)
            val resStr = getMethod.invoke(clazz, key) as String
            if (resStr.isNotEmpty()) {
                java.lang.Boolean.parseBoolean(resStr)
            } else defaultValue
        } catch (e: Exception) {
            Log.e(TAG, "getSystemProperties Exception ${e.message}")
            e.printStackTrace()
            defaultValue
        }
}