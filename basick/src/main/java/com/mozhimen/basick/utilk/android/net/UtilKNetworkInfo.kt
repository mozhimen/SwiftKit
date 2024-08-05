package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.NetworkInfo
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.annors.ADescription
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission

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
    fun get_ofActive(context: Context): NetworkInfo? =
        UtilKConnectivityManager.getActiveNetworkInfo(context)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun get_ofMobile(context: Context): NetworkInfo? =
        get(context, CConnectivityManager.TYPE_MOBILE)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun get_ofEthernet(context: Context): NetworkInfo? =
        get(context, CConnectivityManager.TYPE_ETHERNET)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun get_ofWifi(context: Context): NetworkInfo? =
        get(context, CConnectivityManager.TYPE_WIFI)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun get_ofVpn(context: Context): NetworkInfo? =
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
    fun isConnected(networkInfo: NetworkInfo): Boolean =
        networkInfo.isConnected

    @JvmStatic
    fun isConnectedOrConnecting(networkInfo: NetworkInfo): Boolean =
        networkInfo.isConnectedOrConnecting

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isAvailable_ofMobile(context: Context): Boolean =
        get_ofMobile(context)?.let { isAvailable(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isAvailable_ofEthernet(context: Context): Boolean =
        get_ofEthernet(context)?.let { isAvailable(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isAvailable_ofWifi(context: Context): Boolean =
        get_ofWifi(context)?.let { isAvailable(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isAvailable_ofVpn(context: Context): Boolean =
        get_ofVpn(context)?.let { isAvailable(it) } ?: false

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isConnected_ofMobile(context: Context): Boolean =
        get_ofMobile(context)?.let { isConnected(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isConnected_ofEthernet(context: Context): Boolean =
        get_ofEthernet(context)?.let { isConnected(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isConnected_ofWifi(context: Context): Boolean =
        get_ofWifi(context)?.let { isConnected(it) } ?: false

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isConnected_ofVpn(context: Context): Boolean =
        get_ofVpn(context)?.let { isConnected(it) } ?: false

    //////////////////////////////////////////////////////////////////////////////

    //网络是否连接
    @ADescription("isNetAvailable", "isConnectionUseful")
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