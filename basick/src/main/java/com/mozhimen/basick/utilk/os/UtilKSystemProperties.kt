package com.mozhimen.basick.utilk.os

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import com.mozhimen.basick.elemk.cons.CVersionCode

import java.lang.reflect.Method

/**
 * @ClassName UtilKCmd
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/22 12:31
 * @Version 1.0
 */
object UtilKSystemProperties {
    private val TAG = "UtilKCmd>>>>>"
    private const val KEY_SYSTEM_PROPERTIES = "android.os.SystemProperties"
    private const val RO_ROM_VERSION = "ro.product.rom.version"
    private const val RO_HW_VERSION = "ro.product.hw.version"
    private const val RO_SERIAL_NO = "ro.serialno"

    private const val NO_DEFINED = "unknown"

    /**
     * 设备Rom版本
     * @return String
     */
    @JvmStatic
    fun getRomVersion(): String =
        getSystemProperties(RO_ROM_VERSION, NO_DEFINED)

    /**
     * 设备硬件版本
     * @return String
     */
    @JvmStatic
    fun getHardwareVersion(): String =
        getSystemProperties(RO_HW_VERSION, NO_DEFINED)

    /**
     * 序列号
     * @return String
     */
    @JvmStatic
    fun getSerialNumber(): String = if (Build.VERSION.SDK_INT >= CVersionCode.V_29_10_Q) {
        NO_DEFINED
    } else if (Build.VERSION.SDK_INT >= CVersionCode.V_26_8_O) {
        Build.SERIAL
    } else {
        getSystemProperties(RO_SERIAL_NO, NO_DEFINED)
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
            val clazz = Class.forName(KEY_SYSTEM_PROPERTIES)
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
            val clazz = Class.forName(KEY_SYSTEM_PROPERTIES)
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
            val clazz = Class.forName(KEY_SYSTEM_PROPERTIES)
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