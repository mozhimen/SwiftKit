package com.mozhimen.basick.utilk.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.utilk.content.UtilKContext

/**
 * @ClassName UtilKConnManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 22:11
 * @Version 1.0
 */
@AManifestKRequire(CPermission.ACCESS_NETWORK_STATE)
object UtilKConnManager {
    @JvmStatic
    fun get(context: Context): ConnectivityManager =
        UtilKContext.getConnectivityManager(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActiveNetworkInfo(context: Context): NetworkInfo? =
        get(context).activeNetworkInfo

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getAllNetworkInfo(context: Context): Array<NetworkInfo> =
        get(context).allNetworkInfo
}