package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.NetworkInfo
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission

/**
 * @ClassName UtilKActiveNetworkInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 11:28
 * @Version 1.0
 */
object UtilKActiveNetworkInfo {
    @JvmStatic
    fun get(context: Context): NetworkInfo? =
        UtilKNetworkInfo.getOfActive(context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @AManifestKRequire(CPermission.ACCESS_NETWORK_STATE)
    fun getType(context: Context): Int? =
        get(context)?.let { UtilKNetworkInfo.getType(it) }

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun isAvailable(context: Context): Boolean? =
        get(context)?.let { UtilKNetworkInfo.isAvailable(it) }

    /**
     * 是否连接无线网
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @AManifestKRequire(CPermission.ACCESS_NETWORK_STATE)
    fun isWifiConnected(context: Context): Boolean {
        val activeNetworkInfo = get(context)
        return activeNetworkInfo != null
                && activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_WIFI
    }

    /**
     * 是否连接移动网络
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @AManifestKRequire(CPermission.ACCESS_NETWORK_STATE)
    fun isMobileConnected(context: Context): Boolean {
        val activeNetworkInfo = get(context)
        return activeNetworkInfo != null
                && activeNetworkInfo.state == NetworkInfo.State.CONNECTED
                && activeNetworkInfo.type == CConnectivityManager.TYPE_MOBILE
    }
}