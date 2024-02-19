package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKConnManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 22:11
 * @Version 1.0
 */
@OPermission_ACCESS_NETWORK_STATE
object UtilKConnectivityManager {
    @JvmStatic
    fun get(context: Context): ConnectivityManager =
        UtilKContext.getConnectivityManager(context)

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getNetworkInfo(context: Context, networkType: Int): NetworkInfo? =
        get(context).getNetworkInfo(networkType)

    //获取可获得的网络信息
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getActiveNetworkInfo(context: Context): NetworkInfo? =
        get(context).activeNetworkInfo

    //获取所有网络信息
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getAllNetworkInfo(context: Context): Array<NetworkInfo> =
        get(context).allNetworkInfo

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getActiveNetwork(context: Context): Network? =
        get(context).activeNetwork

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getNetworkCapabilities(context: Context, network: Network): NetworkCapabilities? =
        get(context).getNetworkCapabilities(network)

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_24_7_N)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun registerDefaultNetworkCallback(context: Context, networkCallback: NetworkCallback) {
        get(context).registerDefaultNetworkCallback(networkCallback)
    }

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun registerNetworkCallback(context: Context, request: NetworkRequest, networkCallback: NetworkCallback) {
        get(context).registerNetworkCallback(request, networkCallback)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun unregisterNetworkCallback(context: Context, networkCallback: NetworkCallback) {
        get(context).unregisterNetworkCallback(networkCallback)
    }
}