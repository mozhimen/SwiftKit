package com.mozhimen.basick.utilk.java.lang

import android.annotation.SuppressLint
import android.util.Log
import com.mozhimen.basick.elemk.android.os.cons.CBuild
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.utilk.android.os.UtilKBuild
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
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

    /**
     * 设备Rom版本
     * @return String
     */
    @JvmStatic
    fun getRomVersion(): String =
            getSystemProperties(CPackage.RO_PRODUCT_ROM_VERSION, CBuild.UNKNOWN)

    /**
     * 设备硬件版本
     * @return String
     */
    @JvmStatic
    fun getHardwareVersion(): String =
            getSystemProperties(CPackage.RO_PRODUCT_HW_VERSION, CBuild.UNKNOWN)

    /**
     * 序列号
     * @return String
     */
    @SuppressLint("HardwareIds")
    @JvmStatic
    fun getSerialNumber(): String = if (UtilKBuildVersion.isAfterV_29_10_Q()) {
        CBuild.UNKNOWN
    } else if (UtilKBuildVersion.isAfterV_26_8_O()) {
        UtilKBuild.getSerial()
    } else {
        getSystemProperties(CPackage.RO_SERIAL_NO, CBuild.UNKNOWN)
    }

    /**
     * 获取首选项
     * @param strPackage String
     * @param defaultValue String
     * @return String
     */
    @JvmStatic
    fun getSystemProperties(strPackage: String, defaultValue: String): String =
            try {
                val clazz = CPackage.ANDROID_OS_SYSTEM_PROPERTIES.strPackage2clazz()
                val methodGet: Method = clazz.getMethod("get", String::class.java)
                (methodGet.invoke(clazz, strPackage) as String).ifEmpty { defaultValue }
            } catch (e: Exception) {
                Log.e(TAG, "getSystemProperties Exception ${e.message}")
                e.printStackTrace()
                defaultValue
            }

    /**
     * 获取首选项
     * @param strPackage String
     * @param defaultValue Boolean
     * @return String
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun getSystemPropertiesBool(strPackage: String, defaultValue: Boolean): Boolean =
            try {
                val clazz = CPackage.ANDROID_OS_SYSTEM_PROPERTIES.strPackage2clazz()
                val methodGet: Method = clazz.getMethod("get", String::class.java)
                val str = methodGet.invoke(clazz, strPackage) as String
                if (str.isNotEmpty()) java.lang.Boolean.parseBoolean(str) else defaultValue
            } catch (e: Exception) {
                Log.e(TAG, "getSystemProperties Exception ${e.message}")
                e.printStackTrace()
                defaultValue
            }

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置首选项
     * @param key String?
     * @param value String?
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun applySystemProperties(key: String, value: String) {
        try {
            val clazz = CPackage.ANDROID_OS_SYSTEM_PROPERTIES.strPackage2clazz()
            val methodSet: Method = clazz.getMethod("set", String::class.java, String::class.java)
            methodSet.invoke(clazz, key, value)
        } catch (e: Exception) {
            Log.e(TAG, "setSystemProperties Exception ${e.message}")
            e.printStackTrace()
        }
    }

    /**
     * 重启
     */
    @JvmStatic
    fun applyReboot() {
        UtilKSystemProperties.applySystemProperties(CPackage.SYS_POWERED, "reboot")
    }

    /**
     * 是否自启动
     * @return Boolean
     */
    @JvmStatic
    fun isAutoRun(): Boolean =
            UtilKSystemProperties.getSystemPropertiesBool(CPackage.PERSIST_SENSEPASS_AUTORUN, false)
}