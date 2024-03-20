package com.mozhimen.basick.utilk.android.telephony

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.telephony.CTelephonyManager
import com.mozhimen.basick.lintk.optins.permission.OPermission_READ_PRIVILEGED_PHONE_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz
import java.lang.reflect.Method


/**
 * @ClassName UtilKTelephony
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 16:03
 * @Version 1.0
 */
object UtilKTelephonyManager {
    @JvmStatic
    fun get(context: Context): TelephonyManager =
        UtilKContext.getTelephonyManager(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_READ_PRIVILEGED_PHONE_STATE
    @RequiresPermission(CPermission.READ_PRIVILEGED_PHONE_STATE)
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context, slotIndex: Int): String =
        get(context).getDeviceId(slotIndex)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_READ_PRIVILEGED_PHONE_STATE
    @RequiresPermission(CPermission.READ_PRIVILEGED_PHONE_STATE)
    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String =
        get(context).deviceId

    /**
     * 反射获取 IMEI/MEID
     * Android 6.0之后如果用户不允许通过 [CPermission.READ_PHONE_STATE] 权限的话，
     * 那么是没办法通过系统api进行获取 IMEI/MEID 的，但是可以通过这个反射来尝试绕过权限进行获取
     * @return 获取到的值 或者 空串""
     */
    @JvmStatic
    @SuppressLint("DiscouragedPrivateApi")
    fun getDeviceId_ofReflect(context: Context): String {
        try {
            val telephonyManager = get(context)
            return if (UtilKBuildVersion.isAfterV_21_5_L()) {
                val methodGetDefaultSim: Method = TelephonyManager::class.java.getDeclaredMethod("getDefaultSim")
                methodGetDefaultSim.isAccessible = true
                val defaultSim: Any? = methodGetDefaultSim.invoke(telephonyManager)

                val methodGetDeviceId: Method = TelephonyManager::class.java.getDeclaredMethod("getDeviceId", Int::class.javaPrimitiveType)
                methodGetDeviceId.invoke(telephonyManager, defaultSim)?.toString() ?: ""
            } else {
                val methodGetSubscriberInfo: Method = TelephonyManager::class.java.getDeclaredMethod("getSubscriberInfo")
                methodGetSubscriberInfo.isAccessible = true
                val subInfo: Any? = methodGetSubscriberInfo.invoke(telephonyManager)

                val methodGetDeviceId: Method = "com.android.internal.telephony.IPhoneSubInfo".strPackage2clazz().getDeclaredMethod("getDeviceId")
                methodGetDeviceId.invoke(subInfo)?.toString() ?: ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(UtilKDeviceId.TAG)
        }
        return ""
    }

    /**
     * 反射获取 deviceId
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     */
    @JvmStatic
    fun getDeviceId_ofReflect(context: Context, slotIndex: Int): String {
        try {
            val telephonyManager = get(context)
            val methodGetDeviceId: Method = telephonyManager.javaClass.getMethod("getDeviceId", Int::class.javaPrimitiveType)
            return methodGetDeviceId.invoke(telephonyManager, slotIndex)?.toString() ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(UtilKDeviceId.TAG)
        }
        return ""
    }

    @JvmStatic
    fun getPhoneType(context: Context): Int =
        get(context).phoneType

    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    fun getImei(context: Context, slotIndex: Int): String {
        val telephonyManager = get(context)
        return telephonyManager.javaClass.getMethod("getImei", Int::class.javaPrimitiveType).invoke(telephonyManager, slotIndex) as String// android 8 即以后建议用getImei 方法获取 不会获取到MEID
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    fun getMeid(context: Context, slotIndex: Int): String {
        val telephonyManager = get(context)
        return telephonyManager.javaClass.getMethod("getMeid", Int::class.javaPrimitiveType).invoke(telephonyManager, slotIndex) as String// android 8 即以后建议用getImei 方法获取 不会获取到MEID
    }

    //////////////////////////////////////////////////////////////

    @JvmStatic
    fun hasTelephone(context: Context): Boolean =
        getPhoneType(context) != CTelephonyManager.PHONE_TYPE_NONE
}