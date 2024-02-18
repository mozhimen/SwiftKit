package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.NetworkInfo
import androidx.annotation.RequiresPermission
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

    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @JvmStatic
    fun getActive(context: Context): NetworkInfo? =
        UtilKConnectivityManager.getActiveNetworkInfo(context)

    @JvmStatic
    fun getType(networkInfo: NetworkInfo): Int =
        networkInfo.type

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAvailable(networkInfo: NetworkInfo): Boolean =
        networkInfo.isAvailable

    //网络是否连接
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun hasNetworkConnected(context: Context): Boolean {
        UtilKConnectivityManager.getAllNetworkInfo(context).forEach {
            if (it.isConnected) return true
        }
        return false
    }
}