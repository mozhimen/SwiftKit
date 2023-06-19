package com.mozhimen.basick.utilk.device

import android.content.Context
import android.telephony.TelephonyManager
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext


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
    fun isHasTelephone(context: Context): Boolean =
        get(context).phoneType != TelephonyManager.PHONE_TYPE_NONE
}