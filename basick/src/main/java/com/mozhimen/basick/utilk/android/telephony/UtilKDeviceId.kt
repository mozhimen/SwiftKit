package com.mozhimen.basick.utilk.android.telephony

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import android.text.TextUtils
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_READ_PRIVILEGED_PHONE_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.UtilKPermission
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
    @OPermission_READ_PRIVILEGED_PHONE_STATE
    @RequiresPermission(CPermission.READ_PRIVILEGED_PHONE_STATE)
    fun get(context: Context): String {
        var imei = ""
        if (!UtilKPermission.isSelfGranted(CPermission.READ_PHONE_STATE))//Android 6.0 以后需要获取动态权限  检查权限
            return imei
        imei = get_ofTeleMgr(context)// 1. 尝试通过系统api获取imei
        if (TextUtils.isEmpty(imei))
            imei = get_ofReflect(context)
        return imei
    }

    /**
     * 获取 IMEI/MEID
     * @param slotId  slotId为卡槽Id，它的值为 0、1；
     * @return 获取到的值 或者 空串""
     */
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_READ_PRIVILEGED_PHONE_STATE
    @RequiresPermission(CPermission.READ_PRIVILEGED_PHONE_STATE)
    fun get(context: Context, slotId: Int): String {
        var imei: String
        imei = get_ofTeleMgr(context, slotId)// 1. 尝试通过系统api获取imei
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
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_READ_PRIVILEGED_PHONE_STATE
    @RequiresPermission(CPermission.READ_PRIVILEGED_PHONE_STATE)
    @SuppressLint("HardwareIds")
    fun get_ofTeleMgr(context: Context, slotId: Int): String =
        UtilKTelephonyManager.getDeviceId(context, slotId)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_READ_PRIVILEGED_PHONE_STATE
    @RequiresPermission(CPermission.READ_PRIVILEGED_PHONE_STATE)
    @SuppressLint("HardwareIds")
    fun get_ofTeleMgr(context: Context): String =
        UtilKTelephonyManager.getDeviceId(context)

    /**
     * 反射获取 IMEI/MEID
     * Android 6.0之后如果用户不允许通过 [CPermission.READ_PHONE_STATE] 权限的话，
     * 那么是没办法通过系统api进行获取 IMEI/MEID 的，但是可以通过这个反射来尝试绕过权限进行获取
     * @return 获取到的值 或者 空串""
     */
    @JvmStatic
    @SuppressLint("DiscouragedPrivateApi")
    fun get_ofReflect(context: Context): String =
        UtilKTelephonyManager.getDeviceId_ofReflect(context)

    /**
     * 反射获取 deviceId
     * @param slotIndex  slotId为卡槽Id，它的值为 0、1；
     */
    @JvmStatic
    fun get_ofReflect(context: Context, slotIndex: Int): String =
        UtilKTelephonyManager.getDeviceId_ofReflect(context, slotIndex)
}