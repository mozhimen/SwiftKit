package com.mozhimen.basick.utilk.android.telephony

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.telephony.CTelephonyManager
import com.mozhimen.basick.utilk.android.content.UtilKContext


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

    @SuppressLint("HardwareIds")
    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getDeviceId(context: Context, slotIndex: Int): String =
        get(context).getDeviceId(slotIndex)

    @SuppressLint("HardwareIds")
    @RequiresApi(CVersCode.V_23_6_M)
    @JvmStatic
    fun getDeviceId(context: Context): String =
        get(context).deviceId

    @JvmStatic
    fun getPhoneType(context: Context): Int =
        get(context).phoneType

    //////////////////////////////////////////////////////////////

    @JvmStatic
    fun hasTelephone(context: Context): Boolean =
        getPhoneType(context) != CTelephonyManager.PHONE_TYPE_NONE
}