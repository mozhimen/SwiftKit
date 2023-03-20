package com.mozhimen.basick.utilk.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.mozhimen.basick.utilk.content.UtilKContext

/**
 * @ClassName UtilKConnManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 22:11
 * @Version 1.0
 */
object UtilKConnManager {
    @JvmStatic
    fun get(context: Context): ConnectivityManager =
        UtilKContext.getConnectivityManager(context)

    @JvmStatic
    fun getActiveNetworkInfo(context: Context): NetworkInfo? =
        get(context).activeNetworkInfo

    @JvmStatic
    fun getAllNetworkInfo(context: Context): Array<NetworkInfo> =
        get(context).allNetworkInfo
}