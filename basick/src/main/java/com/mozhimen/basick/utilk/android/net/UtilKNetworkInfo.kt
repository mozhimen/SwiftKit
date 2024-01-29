package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.NetworkInfo

/**
 * @ClassName UtilKNetworkInterface
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/7 1:48
 * @Version 1.0
 */
object UtilKNetworkInfo {

    @JvmStatic
    fun getOfActive(context: Context): NetworkInfo? =
        UtilKConnectivityManager.getActiveNetworkInfo(context)

    @JvmStatic
    fun getType(networkInfo: NetworkInfo): Int =
        networkInfo.type

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAvailable(networkInfo: NetworkInfo): Boolean =
        networkInfo.isAvailable
}