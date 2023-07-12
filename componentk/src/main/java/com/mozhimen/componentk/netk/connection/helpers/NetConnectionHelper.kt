package com.mozhimen.componentk.netk.connection.helpers

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.cons.ENetType
import com.mozhimen.basick.utilk.android.net.UtilKNetConn


/**
 * @ClassName NetKConnUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/2/13 15:11
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.INTERNET
)
object NetConnectionHelper {
    @JvmStatic
    fun getENetKType(): ENetType {
        var netKType = ENetType.NONE
        val activeNetworkInfo: NetworkInfo? = UtilKNetConn.getActiveNetworkInfo()
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable) {
            netKType = when (activeNetworkInfo.type) {
                ConnectivityManager.TYPE_WIFI -> ENetType.WIFI
                ConnectivityManager.TYPE_MOBILE -> {
                    when (activeNetworkInfo.subtype) {
                        TelephonyManager.NETWORK_TYPE_TD_SCDMA, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> ENetType.M3G
                        TelephonyManager.NETWORK_TYPE_LTE, TelephonyManager.NETWORK_TYPE_IWLAN -> ENetType.M4G
                        TelephonyManager.NETWORK_TYPE_GSM, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> ENetType.M2G
                        else -> {
                            val subtypeName = activeNetworkInfo.subtypeName
                            if (subtypeName.equals("TD-SCDMA", ignoreCase = true) || subtypeName.equals("WCDMA", ignoreCase = true) || subtypeName.equals("CDMA2000", ignoreCase = true)) ENetType.M3G
                            else ENetType.UNKNOWN
                        }
                    }
                }

                else -> ENetType.UNKNOWN
            }
        }
        return netKType
    }
}