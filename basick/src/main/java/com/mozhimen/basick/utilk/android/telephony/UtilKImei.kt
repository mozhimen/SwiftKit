package com.mozhimen.basick.utilk.android.telephony

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.cons.CPackage
import com.mozhimen.basick.elemk.cons.CStrPackage
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.os.UtilKSystemProperties
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.util.it
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.kotlin.strPackage2clazz
import java.lang.reflect.Method


/**
 * @ClassName IEMIUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/7/16 10:53
 * @Version 1.0
 */
object UtilKImei : IUtilK {
    /**
     * 获取默认的imei  一般都是IMEI 1
     * //优先获取IMEI(即使是电信卡)  不行的话就获取MEID
     * @param context
     * @return
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.READ_PHONE_STATE)
    fun getImei(context: Context): String =
        getImeiOrMeid(context, 0)

    /**
     * 获取imei2
     *
     * @param context
     * @return
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.READ_PHONE_STATE)
    fun getImei2(context: Context): String {
        //imei2必须与 imei1不一样
        val imeiDefault = getImei(context)
        if (imeiDefault.isEmpty()) {
            //默认的 imei 竟然为空，说明权限还没拿到，或者是平板
            //这种情况下，返回 imei2也应该是空串
            return ""
        }

        //注意，拿第一个 IMEI 是传0，第2个 IMEI 是传1，别搞错了
        val imei1 = getImeiOrMeid(context, 0)
        val imei2 = getImeiOrMeid(context, 1)
        //sim 卡换卡位时，imei1与 imei2有可能互换，而 imeidefault 有可能不变
        if (!TextUtils.equals(imei2, imeiDefault)) {
            //返回与 imeiDefault 不一样的
            return imei2
        }
        return if (!TextUtils.equals(imei1, imeiDefault))
            imei1
        else ""
    }

    /**
     * 获取 Imei/Meid    优先获取IMEI(即使是电信卡)  不行的话就获取MEID
     * 如果装有CDMA制式的SIM卡(电信卡) ，在Android 8 以下 只能获取MEID ,无法获取到该卡槽的IMEI
     * 8及以上可以通过 #imei 方法获取IMEI  通过 #deviceId 方法获取的是MEID
     * @param context
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.READ_PHONE_STATE)
    fun getImeiOrMeid(context: Context, slotId: Int): String {
        var imei = ""
        //Android 6.0 以后需要获取动态权限  检查权限
        if (!UtilKPermission.checkPermission(CPermission.READ_PHONE_STATE))
            return imei
        try {
            val telephonyManager = UtilKTelephonyManager.get(context)
            imei = if (UtilKBuildVersion.isAfterV_26_8_O())
                telephonyManager.javaClass.getMethod("getImei", Int::class.javaPrimitiveType).invoke(telephonyManager, slotId) as String// android 8 即以后建议用getImei 方法获取 不会获取到MEID
            else if (UtilKBuildVersion.isAfterV_21_5_L())
                UtilKSystemProperties.get2(CStrPackage.RIL_GSM_IMEI)//5.0的系统如果想获取MEID/IMEI1/IMEI2  ----framework层提供了两个属性值“ril.cdma.meid"和“ril.gsm.imei"获取
            else
                getDeviceId(context, slotId)//如果获取不到 就调用 getDeviceId 方法获取//5.0以下获取imei/meid只能通过 getDeviceId  方法去取
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.it(TAG)
        }
        if (imei.isEmpty())
            imei = getDeviceId(context, slotId)
        return imei
    }

    /**
     * 仅获取 Imei  如果获取到的是meid 或空  均返回空字符串
     *
     * @param slotId slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun getImeiOnly(context: Context, slotId: Int): String {
        var imei = ""

        //Android 6.0 以后需要获取动态权限  检查权限
        if (!UtilKPermission.checkPermission(CPermission.READ_PHONE_STATE))
            return imei
        try {
            val telephonyManager = UtilKTelephonyManager.get(context)
            if (UtilKBuildVersion.isAfterV_26_8_O()) { // android 8 即以后建议用getImei 方法获取 不会获取到MEID
                val methodGetImei: Method = telephonyManager.javaClass.getMethod("getImei", Int::class.javaPrimitiveType)
                imei = methodGetImei.invoke(telephonyManager, slotId) as String
            } else if (UtilKBuildVersion.isAfterV_21_5_L()) {
                //5.0的系统如果想获取MEID/IMEI1/IMEI2  ----framework层提供了两个属性值“ril.cdma.meid"和“ril.gsm.imei"获取
                imei = UtilKSystemProperties.get2(CStrPackage.RIL_GSM_IMEI)
                //如果获取不到 就调用 getDeviceId 方法获取
            } else { //5.0以下获取imei/meid只能通过 getDeviceId  方法去取
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.it(TAG)
        }
        if (TextUtils.isEmpty(imei)) {
            val imeiOrMeid = getDeviceId(context, slotId)
            //长度15 的是imei  14的是meid
            if (!TextUtils.isEmpty(imeiOrMeid) && imeiOrMeid.length >= 15)
                imei = imeiOrMeid
        }
        return imei
    }

    /**
     * 仅获取 Meid  如果获取到的是imei 或空  均返回空字符串
     * 一般只有一个 meid  即获取到的二个是相同的
     *
     * @param context
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun getMeidOnly(context: Context, slotId: Int): String {
        var meid = ""
        //Android 6.0 以后需要获取动态权限  检查权限
        if (!UtilKPermission.checkPermission(CPermission.READ_PHONE_STATE))
            return meid
        try {
            val telephonyManager = UtilKTelephonyManager.get(context)
            if (UtilKBuildVersion.isAfterV_26_8_O()) { // android 8 即以后建议用getMeid 方法获取 不会获取到Imei
                val methodGetMeid: Method = telephonyManager.javaClass.getMethod("getMeid", Int::class.javaPrimitiveType)
                meid = methodGetMeid.invoke(telephonyManager, slotId) as String
            } else if (UtilKBuildVersion.isAfterV_21_5_L()) {
                //5.0的系统如果想获取MEID/IMEI1/IMEI2  ----framework层提供了两个属性值“ril.cdma.meid"和“ril.gsm.imei"获取
                meid = UtilKSystemProperties.get2(CStrPackage.RIL_CDMA_MEID/*"ril.cdma.meid"*/)
                //如果获取不到 就调用 getDeviceId 方法获取
            } else { //5.0以下获取imei/meid只能通过 getDeviceId  方法去取
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.it(TAG)
        }
        if (TextUtils.isEmpty(meid)) {
            val imeiOrMeid = getDeviceId(context, slotId)
            //长度15 的是imei  14的是meid
            if (imeiOrMeid.length == 14)
                meid = imeiOrMeid
        }
        return meid
    }

    /**
     * 获取 IMEI/MEID
     *
     * @param context 上下文
     * @return 获取到的值 或者 空串""
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun getDeviceId(context: Context): String {
        var imei = ""
        //Android 6.0 以后需要获取动态权限  检查权限
        if (!UtilKPermission.checkPermission(CPermission.READ_PHONE_STATE))
            return imei
        // 1. 尝试通过系统api获取imei
        imei = getDeviceIdForSystemApi(context)
        if (TextUtils.isEmpty(imei))
            imei = getDeviceIdByReflect(context)
        return imei
    }

    /**
     * 获取 IMEI/MEID
     *
     * @param context 上下文
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     * @return 获取到的值 或者 空串""
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun getDeviceId(context: Context, slotId: Int): String {
        var imei: String
        // 1. 尝试通过系统api获取imei
        imei = getDeviceIdForSystemApi(context, slotId)
        if (TextUtils.isEmpty(imei))
            imei = getDeviceIdByReflect(context, slotId)
        return imei
    }

    /**
     * 调用系统接口获取 IMEI/MEID
     *
     *
     * Android 6.0之后如果用户不允许通过 [CPermission.READ_PHONE_STATE] 权限的话，
     * 那么是没办法通过系统api进行获取 IMEI/MEID 的，但是可以通过[.getDeviceIdByReflect] 反射}绕过权限进行获取
     *
     * @param context 上下文
     * @return 获取到的值 或者 空串""
     */
    @JvmStatic
    @SuppressLint("HardwareIds")
    @RequiresApi(CVersCode.V_23_6_M)
    fun getDeviceIdForSystemApi(context: Context, slotId: Int): String {
        var imei = ""
        try {
            imei = UtilKTelephonyManager.getDeviceId(context, slotId)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.it(TAG)
        }
        return imei
    }

    @JvmStatic
    @SuppressLint("HardwareIds")
    @RequiresApi(CVersCode.V_23_6_M)
    fun getDeviceIdForSystemApi(context: Context): String {
        var imei = ""
        try {
            imei = UtilKTelephonyManager.getDeviceId(context)
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return imei
    }

    /**
     * 反射获取 IMEI/MEID
     *
     *
     * Android 6.0之后如果用户不允许通过 [CPermission.READ_PHONE_STATE] 权限的话，
     * 那么是没办法通过系统api进行获取 IMEI/MEID 的，但是可以通过这个反射来尝试绕过权限进行获取
     *
     * @param context 上下文
     * @return 获取到的值 或者 空串""
     */
    @SuppressLint("DiscouragedPrivateApi")
    @JvmStatic
    fun getDeviceIdByReflect(context: Context): String {
        try {
            val telephonyManager = UtilKTelephonyManager.get(context)
            return if (UtilKBuildVersion.isAfterV_21_5_L()) {
                val methodGetDefaultSim: Method = TelephonyManager::class.java.getDeclaredMethod("getDefaultSim")
                val sim: Any? = methodGetDefaultSim.invoke(telephonyManager)
                val methodGetDeviceId: Method = TelephonyManager::class.java.getDeclaredMethod("getDeviceId", Int::class.javaPrimitiveType)
                methodGetDeviceId.invoke(telephonyManager, sim)?.toString() ?: ""
            } else {
                val clazz = "com.android.internal.telephony.IPhoneSubInfo".strPackage2clazz()
                val methodGetSubscriberInfo: Method = TelephonyManager::class.java.getDeclaredMethod("getSubscriberInfo")
                methodGetSubscriberInfo.isAccessible = true
                val subInfo: Any? = methodGetSubscriberInfo.invoke(telephonyManager)
                val methodGetDeviceId: Method = clazz.getDeclaredMethod("getDeviceId")
                methodGetDeviceId.invoke(subInfo)?.toString() ?: ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return ""
    }

    /**
     * 反射获取 deviceId
     *
     * @param context
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     * @return
     */
    @JvmStatic
    fun getDeviceIdByReflect(context: Context, slotId: Int): String {
        try {
            val telephonyManager = UtilKTelephonyManager.get(context)
            val methodGetDeviceId: Method = telephonyManager.javaClass.getMethod("getDeviceId", Int::class.javaPrimitiveType)
            return methodGetDeviceId.invoke(telephonyManager, slotId)?.toString() ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
        return ""
    }
}

