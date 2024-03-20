package com.mozhimen.basick.utilk.android.os

import android.annotation.SuppressLint
import com.mozhimen.basick.elemk.android.os.cons.CBuild
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz
import java.lang.reflect.Method

/**
 * @ClassName UtilKSystemPropertiesWrapper
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 9:55
 * @Version 1.0
 */
object UtilKSystemPropertiesWrapper {
    @JvmStatic
    fun getImei():String =
        UtilKSystemProperties.getStrStr(CStrPackage.RIL_GSM_IMEI)//5.0的系统如果想获取MEID/IMEI1/IMEI2  ----framework层提供了两个属性值“ril.cdma.meid"和“ril.gsm.imei"获取

    @JvmStatic
    fun getMeid():String =
        UtilKSystemProperties.getStrStr(CStrPackage.RIL_CDMA_MEID)//5.0的系统如果想获取MEID/IMEI1/IMEI2  ----framework层提供了两个属性值“ril.cdma.meid"和“ril.gsm.imei"获取
    //设备Rom版本
    @JvmStatic
    fun getRomVersion(): String =
        UtilKSystemProperties.getStr(CStrPackage.RO_PRODUCT_ROM_VERSION, CBuild.UNKNOWN)

    //设备硬件版本
    @JvmStatic
    fun getHwVersion(): String =
        UtilKSystemProperties.getStr(CStrPackage.RO_PRODUCT_HW_VERSION, CBuild.UNKNOWN)

    //是否自启动
    @JvmStatic
    fun isAutoRun(): Boolean =
        UtilKSystemProperties.getStrOfBool(CStrPackage.PERSIST_SENSEPASS_AUTORUN, false)

    @JvmStatic
    @SuppressLint("PrivateApi")
    fun isMiui_after6(): Boolean {
        return try {
            val clazz = CStrPackage.ANDROID_OS_SYSTEMPROPERTIES.strPackage2clazz()
            val methodGet: Method = clazz.getMethod("get", String::class.java)
            var value = methodGet.invoke(null, CStrPackage.RO_MIUI_UI_VERSION_NAME) as String
            value = value.replace("[vV]".toRegex(), "")
            val version = value.toInt()
            version >= 6
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(UtilKSystemProperties.TAG)
            false
        }
    }

    //colorOS是否大于3
    @JvmStatic
    @SuppressLint("PrivateApi")
    fun isColorOS_after3(): Boolean {
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
            e.message?.e(UtilKSystemProperties.TAG)
            false
        }
    }

    //重启
    @JvmStatic
    fun applyReboot() {
        UtilKSystemProperties.setStrStr(CStrPackage.SYS_POWERED, "reboot")
    }
}