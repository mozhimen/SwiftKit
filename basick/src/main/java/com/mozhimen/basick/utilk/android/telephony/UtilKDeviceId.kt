package com.mozhimen.basick.utilk.android.telephony

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.e
import com.mozhimen.basick.utilk.android.util.i
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz
import java.lang.reflect.Method

/**
 * @ClassName UtilKDeviceId
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/1/30 10:09
 * @Version 1.0
 */
object UtilKDeviceId : IUtilK {
    /**
     * 获取 IMEI/MEID
     * @param context 上下文
     * @return 获取到的值 或者 空串""
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun get(context: Context): String {
        var imei = ""
        if (!UtilKPermission.hasPermission(CPermission.READ_PHONE_STATE))//Android 6.0 以后需要获取动态权限  检查权限
            return imei
        imei = UtilKDeviceId.get_ofSys(context)// 1. 尝试通过系统api获取imei
        if (TextUtils.isEmpty(imei))
            imei = UtilKDeviceId.get_ofReflect(context)
        return imei
    }

    /**
     * 获取 IMEI/MEID
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     * @return 获取到的值 或者 空串""
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun get(context: Context, slotId: Int): String {
        var imei: String
        // 1. 尝试通过系统api获取imei
        imei = get_ofSys(context, slotId)
        if (TextUtils.isEmpty(imei))
            imei = get_ofReflect(context, slotId)
        return imei
    }

    /**
     * 调用系统接口获取 IMEI/MEID
     * Android 6.0之后如果用户不允许通过 [CPermission.READ_PHONE_STATE] 权限的话，
     * 那么是没办法通过系统api进行获取 IMEI/MEID 的，但是可以通过[.getDeviceIdByReflect] 反射}绕过权限进行获取
     * @return 获取到的值 或者 空串""
     */
    @JvmStatic
    @SuppressLint("HardwareIds")
    @RequiresApi(CVersCode.V_23_6_M)
    fun get_ofSys(context: Context, slotId: Int): String =
        try {
            UtilKTelephonyManager.getDeviceId(context, slotId)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.i(TAG)
            ""
        }

    @JvmStatic
    @SuppressLint("HardwareIds")
    @RequiresApi(CVersCode.V_23_6_M)
    fun get_ofSys(context: Context): String =
        try {
            UtilKTelephonyManager.getDeviceId(context)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
            ""
        }

    /**
     * 反射获取 IMEI/MEID
     * Android 6.0之后如果用户不允许通过 [CPermission.READ_PHONE_STATE] 权限的话，
     * 那么是没办法通过系统api进行获取 IMEI/MEID 的，但是可以通过这个反射来尝试绕过权限进行获取
     * @return 获取到的值 或者 空串""
     */
    @SuppressLint("DiscouragedPrivateApi")
    @JvmStatic
    fun get_ofReflect(context: Context): String {
        try {
            val telephonyManager = UtilKTelephonyManager.get(context)
            return if (UtilKBuildVersion.isAfterV_21_5_L()) {
                val methodGetDefaultSim: Method = TelephonyManager::class.java.getDeclaredMethod("getDefaultSim")
                methodGetDefaultSim.isAccessible =true
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
            e.message?.e(TAG)
        }
        return ""
    }

    /**
     * 反射获取 deviceId
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     */
    @JvmStatic
    fun get_ofReflect(context: Context, slotId: Int): String {
        try {
            val telephonyManager = UtilKTelephonyManager.get(context)
            val methodGetDeviceId: Method = telephonyManager.javaClass.getMethod("getDeviceId", Int::class.javaPrimitiveType)
            return methodGetDeviceId.invoke(telephonyManager, slotId)?.toString() ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.e(TAG)
        }
        return ""
    }
}