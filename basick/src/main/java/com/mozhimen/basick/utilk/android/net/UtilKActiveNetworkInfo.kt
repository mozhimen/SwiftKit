package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.NetworkInfo
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.telephony.CTelephonyManager
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.equalsIgnoreCase

/**
 * @ClassName UtilKActiveNetworkInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 11:28
 * @Version 1.0
 */
object UtilKActiveNetworkInfo : IUtilK {
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun getActive(context: Context): NetworkInfo? =
        UtilKNetworkInfo.getActive(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getActiveType(context: Context): Int {
        val activeNetworkInfo = getActive(context)
        if (activeNetworkInfo != null && UtilKNetworkInfo.isAvailable(activeNetworkInfo)) {
            return UtilKNetworkInfo.getType(activeNetworkInfo)
        }
        return -1
    }

    //////////////////////////////////////////////////////////////////////////////

    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun isActiveAvailable(context: Context): Boolean =
        getActive(context)?.let { UtilKNetworkInfo.isAvailable(it) } ?: false

    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun isActiveConnected(context: Context): Boolean =
        getActive(context)?.let { UtilKNetworkInfo.isConnected(it) } ?: false

    //////////////////////////////////////////////////////////////////////////////

    //任何网络是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveAny(context: Context): Boolean {
        val activeNetworkInfo = getActive(context) ?: return false
        return when (activeNetworkInfo.type) {
            CConnectivityManager.TYPE_MOBILE -> true
            CConnectivityManager.TYPE_ETHERNET -> true
            CConnectivityManager.TYPE_WIFI -> true
            CConnectivityManager.TYPE_VPN -> true
            else -> false
        }
    }

    //移动网络是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveMobile(context: Context): Boolean {
        val activeNetworkInfo = getActive(context) ?: return false
        return activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_MOBILE
    }

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveEthernet(context: Context): Boolean {
        val activeNetworkInfo = getActive(context) ?: return false
        return activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_ETHERNET
    }

    //无线网是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveWifi(context: Context): Boolean {
        val activeNetworkInfo = getActive(context) ?: return false
        return activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_WIFI
    }

    //VPN是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun isActiveVpn(context: Context): Boolean {
        val activeNetworkInfo = getActive(context) ?: return false
        return activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_VPN
    }

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun isActiveMobileAvailable(context: Context):Boolean =
        isActiveMobile(context) && isActiveAvailable(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun isActiveEthernetAvailable(context: Context):Boolean =
        isActiveEthernet(context) && isActiveAvailable(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun isActiveWifiAvailable(context: Context):Boolean =
        isActiveWifi(context) && isActiveAvailable(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun isActiveVpnAvailable(context: Context):Boolean =
        isActiveVpn(context) && isActiveAvailable(context)

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun isActiveMobileConnected(context: Context):Boolean =
        isActiveMobile(context) && isActiveConnected(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun isActiveEtherNetConnected(context: Context):Boolean =
        isActiveEthernet(context) && isActiveConnected(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun isActiveWifiConnected(context: Context):Boolean =
        isActiveWifi(context) && isActiveConnected(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun isActiveVpnConnected(context: Context):Boolean =
        isActiveVpn(context) && isActiveConnected(context)

    //////////////////////////////////////////////////////////////////////////////

    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun activeNetworkInfo2netTypes(context: Context): Set<ENetType> =
        getActive(context)?.let { networkInfo2netTypes(it) } ?: setOf(ENetType.NONE)

    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun networkInfo2netTypes(networkInfo: NetworkInfo): Set<ENetType> =
        if (!UtilKNetworkInfo.isAvailable(networkInfo))
            setOf(ENetType.NONE)
        else when (networkInfo.type) {
            CConnectivityManager.TYPE_VPN -> setOf(ENetType.VPN)
            CConnectivityManager.TYPE_WIFI -> setOf(ENetType.WIFI)
            CConnectivityManager.TYPE_ETHERNET -> setOf(ENetType.ETHERNET)
            CConnectivityManager.TYPE_MOBILE -> when (networkInfo.subtype) {
                CTelephonyManager.NETWORK_TYPE_LTE, CTelephonyManager.NETWORK_TYPE_IWLAN -> setOf(ENetType.MOBILE_4G)
                CTelephonyManager.NETWORK_TYPE_TD_SCDMA, CTelephonyManager.NETWORK_TYPE_EVDO_A, CTelephonyManager.NETWORK_TYPE_UMTS, CTelephonyManager.NETWORK_TYPE_EVDO_0, CTelephonyManager.NETWORK_TYPE_HSDPA, CTelephonyManager.NETWORK_TYPE_HSUPA, CTelephonyManager.NETWORK_TYPE_HSPA, CTelephonyManager.NETWORK_TYPE_EVDO_B, CTelephonyManager.NETWORK_TYPE_EHRPD, CTelephonyManager.NETWORK_TYPE_HSPAP -> setOf(ENetType.MOBILE_3G)
                CTelephonyManager.NETWORK_TYPE_GSM, CTelephonyManager.NETWORK_TYPE_GPRS, CTelephonyManager.NETWORK_TYPE_CDMA, CTelephonyManager.NETWORK_TYPE_EDGE, CTelephonyManager.NETWORK_TYPE_1xRTT, CTelephonyManager.NETWORK_TYPE_IDEN -> setOf(ENetType.MOBILE_2G)
                else -> {
                    val subtypeName = networkInfo.subtypeName
                    if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) setOf(ENetType.MOBILE_3G)
                    else setOf(ENetType.MOBILE)
                }
            }

            else -> setOf(ENetType.UNKNOWN)
        }

    //打印连接信息
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun printActiveNetworkInfo(context: Context) {
        val activeNetworkInfo = getActive(context) ?: return
        ("printActiveNetworkInfo isAvailable " + activeNetworkInfo.isAvailable + " isConnected " + activeNetworkInfo.isConnected + " networkInfo " + activeNetworkInfo.toString()).dt(TAG)
        ("printActiveNetworkInfo subtypeName " + activeNetworkInfo.subtypeName + " typeName " + activeNetworkInfo.typeName + " extraInfo " + activeNetworkInfo.extraInfo).dt(TAG)
    }
}