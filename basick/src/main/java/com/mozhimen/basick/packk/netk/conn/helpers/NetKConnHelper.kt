package com.mozhimen.basick.packk.netk.conn.helpers

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.packk.netk.conn.cons.ENetKType
import com.mozhimen.basick.utilk.net.UtilKNetConn


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
object NetKConnHelper {
    @JvmStatic
    fun getENetKType(): ENetKType {
        var netKType = ENetKType.NONE
        val activeNetworkInfo: NetworkInfo? = UtilKNetConn.getActiveNetworkInfo()
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable) {
            netKType = when (activeNetworkInfo.type) {
                ConnectivityManager.TYPE_WIFI -> {
                    ENetKType.WIFI
                }
                ConnectivityManager.TYPE_MOBILE -> {
                    when (activeNetworkInfo.subtype) {
                        TelephonyManager.NETWORK_TYPE_TD_SCDMA, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_HSPAP -> ENetKType.M3G
                        TelephonyManager.NETWORK_TYPE_LTE, TelephonyManager.NETWORK_TYPE_IWLAN -> ENetKType.M4G
                        TelephonyManager.NETWORK_TYPE_GSM, TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN -> ENetKType.M2G
                        else -> {
                            val subtypeName = activeNetworkInfo.subtypeName
                            if (subtypeName.equals("TD-SCDMA", ignoreCase = true) || subtypeName.equals("WCDMA", ignoreCase = true) || subtypeName.equals("CDMA2000", ignoreCase = true)) {
                                ENetKType.M3G
                            } else {
                                ENetKType.UNKNOWN
                            }
                        }
                    }
                }
                else -> {
                    ENetKType.UNKNOWN
                }
            }
        }
        return netKType
    }
}