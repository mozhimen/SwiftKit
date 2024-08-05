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
import com.mozhimen.basick.utilk.android.util.d
import com.mozhimen.basick.utilk.commons.IUtilK
import com.mozhimen.basick.utilk.kotlin.equalsIgnoreCase
import kotlin.math.acos

/**
 * @ClassName UtilKActiveNetworkInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 11:28
 * @Version 1.0
 */
object UtilKActiveNetworkInfo : IUtilK {
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun get(context: Context): NetworkInfo? =
        UtilKNetworkInfo.get_ofActive(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getType(context: Context): Int {
        val activeNetworkInfo = get(context)
        if (activeNetworkInfo != null && UtilKNetworkInfo.isAvailable(activeNetworkInfo)) {
            return UtilKNetworkInfo.getType(activeNetworkInfo)
        }
        return -1
    }

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isAvailable(context: Context): Boolean =
        get(context)?.let { UtilKNetworkInfo.isAvailable(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isConnected(context: Context): Boolean =
        get(context)?.let { UtilKNetworkInfo.isConnected(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isConnectedOrConnecting(context: Context): Boolean =
        get(context)?.let { UtilKNetworkInfo.isConnectedOrConnecting(it) } ?: false

    //////////////////////////////////////////////////////////////////////////////

    //任何网络是否活跃
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isAny(context: Context): Boolean {
        val activeNetworkInfo = get(context) ?: return false
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
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isMobile(context: Context): Boolean {
        val activeNetworkInfo = get(context) ?: return false
        return activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_MOBILE
    }

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isEthernet(context: Context): Boolean {
        val activeNetworkInfo = get(context) ?: return false
        return activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_ETHERNET
    }

    //无线网是否活跃
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isWifi(context: Context): Boolean {
        val activeNetworkInfo = get(context) ?: return false
        return activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_WIFI
    }

    //VPN是否活跃
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isVpn(context: Context): Boolean {
        val activeNetworkInfo = get(context) ?: return false
        return activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_VPN
    }

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isMobileAvailable(context: Context): Boolean =
        isMobile(context) && isAvailable(context)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isEthernetAvailable(context: Context): Boolean =
        isEthernet(context) && isAvailable(context)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isWifiAvailable(context: Context): Boolean =
        isWifi(context) && isAvailable(context)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isVpnAvailable(context: Context): Boolean =
        isVpn(context) && isAvailable(context)

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isMobileConnected(context: Context): Boolean =
        isMobile(context) && isConnected(context)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isEtherNetConnected(context: Context): Boolean =
        isEthernet(context) && isConnected(context)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isWifiConnected(context: Context): Boolean =
        isWifi(context) && isConnected(context)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isVpnConnected(context: Context): Boolean =
        isVpn(context) && isConnected(context)

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun networkInfo2netTypes(context: Context): Set<ENetType> =
        get(context)?.let { networkInfo2netTypes(it) } ?: setOf(ENetType.NONE)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun networkInfo2netTypes(networkInfo: NetworkInfo): Set<ENetType> =
        if (!UtilKNetworkInfo.isAvailable(networkInfo))
            setOf(ENetType.NONE)
        else when (networkInfo.type) {
            CConnectivityManager.TYPE_VPN -> setOf(ENetType.VPN)
            CConnectivityManager.TYPE_WIFI -> setOf(ENetType.WIFI)
            CConnectivityManager.TYPE_ETHERNET -> setOf(ENetType.ETHERNET)
            CConnectivityManager.TYPE_MOBILE -> when (networkInfo.subtype) {
                CTelephonyManager.NETWORK_TYPE_LTE, CTelephonyManager.NETWORK_TYPE_IWLAN -> setOf(ENetType.MOBILE_4G)
                CTelephonyManager.NETWORK_TYPE_TD_SCDMA, CTelephonyManager.NETWORK_TYPE_EVDO_A, CTelephonyManager.NETWORK_TYPE_UMTS, CTelephonyManager.NETWORK_TYPE_EVDO_0, CTelephonyManager.NETWORK_TYPE_HSDPA, CTelephonyManager.NETWORK_TYPE_HSUPA, CTelephonyManager.NETWORK_TYPE_HSPA, CTelephonyManager.NETWORK_TYPE_EVDO_B, CTelephonyManager.NETWORK_TYPE_EHRPD, CTelephonyManager.NETWORK_TYPE_HSPAP -> setOf(
                    ENetType.MOBILE_3G
                )

                CTelephonyManager.NETWORK_TYPE_GSM, CTelephonyManager.NETWORK_TYPE_GPRS, CTelephonyManager.NETWORK_TYPE_CDMA, CTelephonyManager.NETWORK_TYPE_EDGE, CTelephonyManager.NETWORK_TYPE_1xRTT, CTelephonyManager.NETWORK_TYPE_IDEN -> setOf(
                    ENetType.MOBILE_2G
                )

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
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun printNetworkInfo(context: Context) {
        val activeNetworkInfo = get(context) ?: return
        ("printActiveNetworkInfo isAvailable " + activeNetworkInfo.isAvailable + " isConnected " + activeNetworkInfo.isConnected + " networkInfo " + activeNetworkInfo.toString()).d(TAG)
        ("printActiveNetworkInfo subtypeName " + activeNetworkInfo.subtypeName + " typeName " + activeNetworkInfo.typeName + " extraInfo " + activeNetworkInfo.extraInfo).d(TAG)
    }
}