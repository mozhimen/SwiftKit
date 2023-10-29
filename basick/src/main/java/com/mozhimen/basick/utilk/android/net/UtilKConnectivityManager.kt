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
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.android.net.cons.CNetworkCapabilities
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.telephony.CTelephonyManager
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContext
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKConnManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 22:11
 * @Version 1.0
 */
@AManifestKRequire(CPermission.ACCESS_NETWORK_STATE)
object UtilKConnectivityManager {
    @JvmStatic
    fun get(context: Context): ConnectivityManager =
        UtilKContext.getConnectivityManager(context)

    /**
     * 获取可获得的网络信息
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActiveNetworkInfo(context: Context): NetworkInfo? =
        get(context).activeNetworkInfo

    /**
     * 获取所有网络信息
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getAllNetworkInfo(context: Context): Array<NetworkInfo> =
        get(context).allNetworkInfo

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActiveNetwork(context: Context): Network? =
        get(context).activeNetwork

    @JvmStatic
    fun getNetworkCapabilities(context: Context, network: Network): NetworkCapabilities? =
        get(context).getNetworkCapabilities(network)

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isNetworkConnected(context: Context): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            isNetworkConnectedAfter23(context)
        else isNetworkConnectedBefore23(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isNetworkConnectedAfter23(context: Context): Boolean {
        val networkCapabilities = getNetworkCapabilities(context, getActiveNetwork(context) ?: return false) ?: return false
        return when {
            networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(CNetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isNetworkConnectedBefore23(context: Context): Boolean {
        return getActiveNetworkInfo(context)?.let {
            when (it.type) {
                CConnectivityManager.TYPE_WIFI -> true
                CConnectivityManager.TYPE_MOBILE -> true
                CConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        } ?: false
    }

    ////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(value = CPermission.ACCESS_NETWORK_STATE)
    fun registerNetworkCallback(context: Context, request: NetworkRequest, networkCallback: NetworkCallback) {
        get(context).registerNetworkCallback(request, networkCallback)
    }

    @JvmStatic
    fun unregisterNetworkCallback(context: Context, networkCallback: NetworkCallback) {
        get(context).unregisterNetworkCallback(networkCallback)
    }
}