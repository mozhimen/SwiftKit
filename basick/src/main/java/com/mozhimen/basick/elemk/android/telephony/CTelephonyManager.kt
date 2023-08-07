package com.mozhimen.basick.elemk.android.telephony

import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CTelephonyManager
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/7 1:10
 * @Version 1.0
 */
object CTelephonyManager {
    @RequiresApi(CVersCode.V_25_71_N1)
    const val NETWORK_TYPE_TD_SCDMA = TelephonyManager.NETWORK_TYPE_TD_SCDMA
    const val NETWORK_TYPE_EVDO_A = TelephonyManager.NETWORK_TYPE_EVDO_A
    const val NETWORK_TYPE_UMTS = TelephonyManager.NETWORK_TYPE_UMTS
    const val NETWORK_TYPE_EVDO_0 = TelephonyManager.NETWORK_TYPE_EVDO_0
    const val NETWORK_TYPE_HSDPA = TelephonyManager.NETWORK_TYPE_HSDPA
    const val NETWORK_TYPE_HSUPA = TelephonyManager.NETWORK_TYPE_HSUPA
    const val NETWORK_TYPE_HSPA = TelephonyManager.NETWORK_TYPE_HSPA
    const val NETWORK_TYPE_EVDO_B = TelephonyManager.NETWORK_TYPE_EVDO_B
    const val NETWORK_TYPE_EHRPD = TelephonyManager.NETWORK_TYPE_EHRPD
    const val NETWORK_TYPE_HSPAP = TelephonyManager.NETWORK_TYPE_HSPAP
    const val NETWORK_TYPE_LTE = TelephonyManager.NETWORK_TYPE_LTE

    @RequiresApi(CVersCode.V_25_71_N1)
    const val NETWORK_TYPE_IWLAN = TelephonyManager.NETWORK_TYPE_IWLAN

    @RequiresApi(CVersCode.V_25_71_N1)
    const val NETWORK_TYPE_GSM = TelephonyManager.NETWORK_TYPE_GSM
    const val NETWORK_TYPE_GPRS = TelephonyManager.NETWORK_TYPE_GPRS
    const val NETWORK_TYPE_CDMA = TelephonyManager.NETWORK_TYPE_CDMA
    const val NETWORK_TYPE_EDGE = TelephonyManager.NETWORK_TYPE_EDGE
    const val NETWORK_TYPE_1xRTT = TelephonyManager.NETWORK_TYPE_1xRTT
    const val NETWORK_TYPE_IDEN = TelephonyManager.NETWORK_TYPE_IDEN

    const val PHONE_TYPE_NONE = TelephonyManager.PHONE_TYPE_NONE
}