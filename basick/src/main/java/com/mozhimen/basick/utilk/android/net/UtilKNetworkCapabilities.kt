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
fun NetworkCapabilities.networkCapabilities2netTypes(): Set<ENetType> =
    UtilKNetworkCapabilities.networkCapabilities2netTypes(this)

object UtilKNetworkCapabilities {
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActive(context: Context): NetworkCapabilities? =
        UtilKActiveNetwork.getActiveNetworkCapabilities(context)

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun isAvailable(networkCapabilities: NetworkCapabilities): Boolean =
        networkCapabilities.hasCapability(CNetworkCapabilities.NET_CAPABILITY_INTERNET)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    fun isConnected(networkCapabilities: NetworkCapabilities): Boolean =
        networkCapabilities.hasCapability(CNetworkCapabilities.NET_CAPABILITY_VALIDATED)

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveAvailable(context: Context): Boolean =
        getActive(context)?.let { isAvailable(it) } ?: false

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveConnected(context: Context): Boolean =
        getActive(context)?.let { isConnected(it) } ?: false

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveAny(context: Context): Boolean {
        val activeNetworkCapabilities = getActive(context) ?: return false
        return when {
            activeNetworkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetworkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetworkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetworkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI_AWARE) -> true
            activeNetworkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_VPN) -> true
            else -> false
        }
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveMobile(context: Context): Boolean {
        val networkCapabilities = getActive(context) ?: return false
        return networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_CELLULAR)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveEthernet(context: Context): Boolean {
        val networkCapabilities = getActive(context) ?: return false
        return networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_ETHERNET)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveWifi(context: Context): Boolean {
        val networkCapabilities = getActive(context) ?: return false
        return (networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI_AWARE))
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveVpn(context: Context): Boolean {
        val networkCapabilities = getActive(context) ?: return false
        return networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_VPN)
    }

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveMobileAvailable(context: Context) =
        isActiveMobile(context) && isActiveAvailable(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveEthernetAvailable(context: Context) =
        isActiveEthernet(context) && isActiveAvailable(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveWifiAvailable(context: Context) =
        isActiveWifi(context) && isActiveAvailable(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveVpnAvailable(context: Context) =
        isActiveVpn(context) && isActiveAvailable(context)

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveMobileConnected(context: Context) =
        isActiveMobile(context) && isActiveConnected(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveEthernetConnected(context: Context) =
        isActiveEthernet(context) && isActiveConnected(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_26_8_O)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveWifiConnected(context: Context) =
        isActiveWifi(context) && isActiveConnected(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveVpnConnected(context: Context) =
        isActiveVpn(context) && isActiveConnected(context)

    ///////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun activeNetworkCapabilities2netTypes(context: Context): Set<ENetType> =
        getActive(context)?.let { networkCapabilities2netTypes(it) } ?: setOf(ENetType.NONE)

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun networkCapabilities2netTypes(capabilities: NetworkCapabilities): Set<ENetType> {
        if (!isAvailable(capabilities)) {
            return setOf(ENetType.NONE)
        } else {
            val netTypes = mutableSetOf<ENetType>()
            if (capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_VPN)) {
                netTypes.add(ENetType.VPN)
            }
            if (capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI_AWARE)) {
                netTypes.add(ENetType.WIFI)// 使用WI-FI
            }
            if (capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_ETHERNET)) {
                netTypes.add(ENetType.ETHERNET)
            }
            if (capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_CELLULAR)) {
                netTypes.add(ENetType.MOBILE)// 使用蜂窝网络
            }
            if (capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_BLUETOOTH)
                || capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_LOWPAN)
                || capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_USB)
            ) {
                netTypes.add(ENetType.UNKNOWN) // 未知网络，包括蓝牙、VPN、LoWPAN
            }
            return netTypes
        }
    }
}