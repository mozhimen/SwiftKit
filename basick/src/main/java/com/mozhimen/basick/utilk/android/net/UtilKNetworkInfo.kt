package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.Network
import android.net.NetworkInfo
import android.telephony.AvailableNetworkInfo
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import java.net.NetworkInterface

/**
 * @ClassName UtilKNetworkInterface
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/7 1:48
 * @Version 1.0
 */
object UtilKNetworkInfo {
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun get(context: Context, networkType: Int): NetworkInfo? =
        UtilKConnectivityManager.getNetworkInfo(context, networkType)

    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun getActive(context: Context): NetworkInfo? =
        UtilKConnectivityManager.getActiveNetworkInfo(context)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getMobile(context: Context): NetworkInfo? =
        get(context, CConnectivityManager.TYPE_MOBILE)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getEthernet(context: Context): NetworkInfo? =
        get(context, CConnectivityManager.TYPE_ETHERNET)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getWifi(context: Context): NetworkInfo? =
        get(context, CConnectivityManager.TYPE_WIFI)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getVpn(context: Context): NetworkInfo? =
        get(context, CConnectivityManager.TYPE_VPN)

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun getType(networkInfo: NetworkInfo): Int =
        networkInfo.type

    @JvmStatic
    fun getState(networkInfo: NetworkInfo): NetworkInfo.State =
        networkInfo.state

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAvailable(networkInfo: NetworkInfo): Boolean =
        networkInfo.isAvailable

    @JvmStatic
    fun isConnected(networkInfo: NetworkInfo):Boolean =
        networkInfo.isConnected

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isMobileAvailable(context: Context): Boolean =
        getMobile(context)?.let { isAvailable(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isEthernetAvailable(context: Context): Boolean =
        getEthernet(context)?.let { isAvailable(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isWifiAvailable(context: Context): Boolean =
        getWifi(context)?.let { isAvailable(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isVpnAvailable(context: Context): Boolean =
        getVpn(context)?.let { isAvailable(it) } ?: false

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isMobileConnected(context: Context): Boolean =
        getMobile(context)?.let { isConnected(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isEthernetConnected(context: Context): Boolean =
        getEthernet(context)?.let { isConnected(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isWifiConnected(context: Context): Boolean =
        getWifi(context)?.let { isConnected(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isVpnConnected(context: Context): Boolean =
        getVpn(context)?.let { isConnected(it) } ?: false

    //////////////////////////////////////////////////////////////////////////////

    //网络是否连接
    @ADescription("isNetAvailable","isConnectionUseful")
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun hasNetworkConnected(context: Context): Boolean {
        UtilKConnectivityManager.getAllNetworkInfo(context).forEach {
            if (isConnected(it)) return true
        }
        return false
    }
}