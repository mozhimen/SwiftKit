package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.elemk.android.telephony.CTelephonyManager
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.util.et
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.Enumeration

/**
 * @ClassName UtilKNetworkInterface
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/7 1:48
 * @Version 1.0
 */
object UtilKNetworkInfo {

    @JvmStatic
    fun getActive(context: Context): NetworkInfo? =
        UtilKConnectivityManager.getActiveNetworkInfo(context)

    @JvmStatic
    fun getActiveType(context: Context): Int? =
        getActive(context)?.let { getType(it) }

    @JvmStatic
    fun getType(networkInfo: NetworkInfo): Int =
        networkInfo.type

    /**
     * 获取网路IP
     * @return String
     */
    @JvmStatic
    fun getIp(): String? {
        try {
            val networkInterfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            var inetAddress: InetAddress
            while (networkInterfaces.hasMoreElements()) {
                val inetAddresses: Enumeration<InetAddress> = (networkInterfaces.nextElement() as NetworkInterface).inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement() as InetAddress
                    if (inetAddress !is Inet6Address) {
                        if (inetAddress.hostAddress != "127.0.0.1") {
                            return inetAddress.hostAddress ?: continue
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
            "getIP SocketException ${e.message}".et(UtilKNetConn.TAG)
        }
        return null
    }

    @JvmStatic
    fun getNetType(context: Context): ENetType {
        var netKType = ENetType.NONE
        val activeNetworkInfo: NetworkInfo? = getActive(context)
        if (activeNetworkInfo != null && isAvailable(activeNetworkInfo)) {
            netKType = when (activeNetworkInfo.type) {
                CConnectivityManager.TYPE_WIFI -> ENetType.WIFI
                CConnectivityManager.TYPE_MOBILE -> {
                    when (activeNetworkInfo.subtype) {
                        CTelephonyManager.NETWORK_TYPE_TD_SCDMA, CTelephonyManager.NETWORK_TYPE_EVDO_A, CTelephonyManager.NETWORK_TYPE_UMTS, CTelephonyManager.NETWORK_TYPE_EVDO_0, CTelephonyManager.NETWORK_TYPE_HSDPA, CTelephonyManager.NETWORK_TYPE_HSUPA, CTelephonyManager.NETWORK_TYPE_HSPA, CTelephonyManager.NETWORK_TYPE_EVDO_B, CTelephonyManager.NETWORK_TYPE_EHRPD, CTelephonyManager.NETWORK_TYPE_HSPAP -> ENetType.M3G
                        CTelephonyManager.NETWORK_TYPE_LTE, CTelephonyManager.NETWORK_TYPE_IWLAN -> ENetType.M4G
                        CTelephonyManager.NETWORK_TYPE_GSM, CTelephonyManager.NETWORK_TYPE_GPRS, CTelephonyManager.NETWORK_TYPE_CDMA, CTelephonyManager.NETWORK_TYPE_EDGE, CTelephonyManager.NETWORK_TYPE_1xRTT, CTelephonyManager.NETWORK_TYPE_IDEN -> ENetType.M2G
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

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isActiveAvailable(context: Context): Boolean? =
        getActive(context)?.let { isAvailable(it) }

    @JvmStatic
    fun isAvailable(networkInfo: NetworkInfo): Boolean =
        networkInfo.isAvailable

    /**
     * 是否连接无线网
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isWifiConnected(context: Context): Boolean {
        val activeNetworkInfo = getActive(context)
        return activeNetworkInfo != null && activeNetworkInfo.state == NetworkInfo.State.CONNECTED && activeNetworkInfo.type == CConnectivityManager.TYPE_WIFI
    }

    /**
     * 是否连接移动网络
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isMobileConnected(context: Context): Boolean {
        val activeNetworkInfo = getActive(context)
        return activeNetworkInfo != null && activeNetworkInfo.state == NetworkInfo.State.CONNECTED && activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
    }
}