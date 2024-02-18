package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.NetworkInfo
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.elemk.android.telephony.CTelephonyManager
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.kotlin.equalsIgnoreCase

/**
 * @ClassName UtilKActiveNetworkInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 11:28
 * @Version 1.0
 */
object UtilKActiveNetworkInfo {
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun getActive(context: Context): NetworkInfo? =
        UtilKNetworkInfo.getActive(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getActiveType(context: Context): Int? =
        getActive(context)?.let { UtilKNetworkInfo.getType(it) }

    //////////////////////////////////////////////////////////////////////////////

    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun activeNetworkInfo2netType(context: Context): ENetType {
        val activeNetworkInfo: NetworkInfo? = getActive(context)
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable) return ENetType.NONE
        return when (activeNetworkInfo.type) {
            CConnectivityManager.TYPE_WIFI -> ENetType.WIFI
            CConnectivityManager.TYPE_MOBILE -> when (activeNetworkInfo.subtype) {
                CTelephonyManager.NETWORK_TYPE_LTE, CTelephonyManager.NETWORK_TYPE_IWLAN -> ENetType.MOBILE_4G
                CTelephonyManager.NETWORK_TYPE_TD_SCDMA, CTelephonyManager.NETWORK_TYPE_EVDO_A, CTelephonyManager.NETWORK_TYPE_UMTS, CTelephonyManager.NETWORK_TYPE_EVDO_0, CTelephonyManager.NETWORK_TYPE_HSDPA, CTelephonyManager.NETWORK_TYPE_HSUPA, CTelephonyManager.NETWORK_TYPE_HSPA, CTelephonyManager.NETWORK_TYPE_EVDO_B, CTelephonyManager.NETWORK_TYPE_EHRPD, CTelephonyManager.NETWORK_TYPE_HSPAP -> ENetType.MOBILE_3G
                CTelephonyManager.NETWORK_TYPE_GSM, CTelephonyManager.NETWORK_TYPE_GPRS, CTelephonyManager.NETWORK_TYPE_CDMA, CTelephonyManager.NETWORK_TYPE_EDGE, CTelephonyManager.NETWORK_TYPE_1xRTT, CTelephonyManager.NETWORK_TYPE_IDEN -> ENetType.MOBILE_2G
                else -> {
                    val subtypeName = activeNetworkInfo.subtypeName
                    if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) ENetType.MOBILE_3G
                    else ENetType.MOBILE
                }
            }

            else -> ENetType.UNKNOWN
        }
    }

    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun isActiveAvailable(context: Context): Boolean? =
        getActive(context)?.let { UtilKNetworkInfo.isAvailable(it) }

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveConnected(context: Context): Boolean {
        return getActive(context)?.let {
            when (it.type) {
                CConnectivityManager.TYPE_WIFI -> true
                CConnectivityManager.TYPE_MOBILE -> true
                CConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        } ?: false
    }

    /**
     * 是否连接无线网
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveWifi(context: Context): Boolean {
        val activeNetworkInfo = getActive(context)
        return activeNetworkInfo != null
                && activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_WIFI
    }

    /**
     * 是否连接移动网络
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveMobile(context: Context): Boolean {
        val activeNetworkInfo = getActive(context)
        return activeNetworkInfo != null
                && activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_MOBILE
    }
}