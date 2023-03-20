package com.mozhimen.basick.utilk.device

import android.content.Context
import android.telephony.TelephonyManager
import com.mozhimen.basick.utilk.content.UtilKApplication


/**
 * @ClassName UtilKTelephony
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/3/20 16:03
 * @Version 1.0
 */
object UtilKTelephony {
    private val _context = UtilKApplication.instance.get()

    @JvmStatic
    fun getTelephonyManager(): TelephonyManager =
        _context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    @JvmStatic
    fun isHasTelephone(): Boolean =
        getTelephonyManager().phoneType != TelephonyManager.PHONE_TYPE_NONE
}