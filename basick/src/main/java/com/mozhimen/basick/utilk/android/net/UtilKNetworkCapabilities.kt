package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.NetworkCapabilities
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CNetworkCapabilities
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission

/**
 * @ClassName UtilKNetworkCapabilities
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 11:56
 * @Version 1.0
 */
@RequiresApi(CVersCode.V_21_5_L)
fun NetworkCapabilities.networkCapabilities2netType(): ENetType =
    UtilKNetworkCapabilities.networkCapabilities2netType(this)

object UtilKNetworkCapabilities {
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActive(context: Context): NetworkCapabilities? =
        UtilKNetwork.getActiveNetworkCapabilities(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun activeNetworkCapabilities2netType(context: Context): ENetType? =
        getActive(context)?.let { networkCapabilities2netType(it) }

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveConnected(context: Context): Boolean {
        val networkCapabilities = getActive(context) ?: return false
        return when {
            networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveVpn(context: Context): Boolean {
        val networkCapabilities = getActive(context) ?: return false
        return networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_VPN)
                && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveWifi(context: Context): Boolean {
        val networkCapabilities = getActive(context) ?: return false
        return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI_AWARE))
                && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveMobile(context: Context): Boolean {
        val networkCapabilities = getActive(context) ?: return false
        return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun networkCapabilities2netType(capabilities: NetworkCapabilities): ENetType =
        if (!capabilities.hasCapability(CNetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            ENetType.NONE
        } else {
            if (capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI_AWARE)) {
                ENetType.WIFI// 使用WI-FI
            } else if (capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_ETHERNET)) {
                ENetType.MOBILE// 使用蜂窝网络
            } else {
                ENetType.UNKNOWN// 未知网络，包括蓝牙、VPN、LoWPAN
            }
        }
}