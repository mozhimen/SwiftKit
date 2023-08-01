package com.mozhimen.basick.utilk.android.telephony

import android.content.Context
import android.telephony.TelephonyManager
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

    //////////////////////////////////////////////////////////////

    @JvmStatic
    fun hasTelephone(context: Context): Boolean =
        get(context).phoneType != TelephonyManager.PHONE_TYPE_NONE
}