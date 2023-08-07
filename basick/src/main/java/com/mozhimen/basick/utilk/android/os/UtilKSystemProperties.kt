package com.mozhimen.basick.utilk.android.os

import android.annotation.SuppressLint
import android.util.Log
import com.mozhimen.basick.elemk.android.os.cons.CBuild
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.utilk.android.util.et
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

    @JvmStatic
    fun get2(strPackage: String, defaultValue: String = ""): String {
        try {
            @SuppressLint("PrivateApi") val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodGet: Method = clazz.getMethod("get", String::class.java, String::class.java)
            return methodGet.invoke(clazz, strPackage, defaultValue) as String
        } catch (e: Exception) { /**/
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return ""
    }

    /**
     * 获取首选项
     * @param strPackage String
     * @param defaultValue String
     * @return String
     */
    @JvmStatic
    fun get(strPackage: String, defaultValue: String): String =
        try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
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
    fun getBool(strPackage: String, defaultValue: Boolean): Boolean =
        try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodGet: Method = clazz.getMethod("get", String::class.java)
            val str = methodGet.invoke(clazz, strPackage) as String
            if (str.isNotEmpty()) java.lang.Boolean.parseBoolean(str) else defaultValue
        } catch (e: Exception) {
            Log.e(TAG, "getSystemProperties Exception ${e.message}")
            e.printStackTrace()
            defaultValue
        }

    /**
     * 设备Rom版本
     * @return String
     */
    @JvmStatic
    fun getRomVersion(): String =
        get(CStrPackage.RO_PRODUCT_ROM_VERSION, CBuild.UNKNOWN)

    /**
     * 设备硬件版本
     * @return String
     */
    @JvmStatic
    fun getHardwareVersion(): String =
        get(CStrPackage.RO_PRODUCT_HW_VERSION, CBuild.UNKNOWN)

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
        get(CStrPackage.RO_SERIAL_NO, CBuild.UNKNOWN)
    }

    ///////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @SuppressLint("PrivateApi")
    fun isMIUIAfter6(): Boolean {
        return try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodGet: Method = clazz.getMethod("get", String::class.java)
            var value = methodGet.invoke(null, CStrPackage.RO_MIUI_UI_VERSION_NAME) as String
            value = value.replace("[vV]".toRegex(), "")
            val version = value.toInt()
            version >= 6
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            false
        }
    }

    /**
     * colorOS是否大于3
     * @return Boolean
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun isColorOSAfter3(): Boolean {
        return try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodGet = clazz.getMethod("get", String::class.java)
            var value = methodGet.invoke(null, CStrPackage.RO_BUILD_VERSION_OPPOROM) as String
            value = value.replace("[vV]".toRegex(), "")
            value = value.substring(0, 1)
            val version = value.toInt()
            version >= 3
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
            false
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 是否自启动
     * @return Boolean
     */
    @JvmStatic
    fun isAutoRun(): Boolean =
        getBool(CStrPackage.PERSIST_SENSEPASS_AUTORUN, false)

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 设置首选项
     * @param key String?
     * @param value String?
     */
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun apply(key: String, value: String) {
        try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodSet: Method = clazz.getMethod("set", String::class.java, String::class.java)
            methodSet.invoke(clazz, key, value)
        } catch (e: Exception) {
            Log.e(TAG, "setSystemProperties Exception ${e.message}")
            e.printStackTrace()
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * 重启
     */
    @JvmStatic
    fun applyReboot() {
        apply(CStrPackage.SYS_POWERED, "reboot")
    }
}