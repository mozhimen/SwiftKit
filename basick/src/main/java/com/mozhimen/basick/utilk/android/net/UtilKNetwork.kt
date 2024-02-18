package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.Network
import android.net.NetworkCapabilities
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission

/**
 * @ClassName UtilKNetwork
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2024/2/18 23:22
 * @Version 1.0
 */
object UtilKNetwork {
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActive(context: Context): Network? =
        UtilKConnectivityManager.getActiveNetwork(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getActiveNetworkCapabilities(context: Context): NetworkCapabilities? =
        getActive(context)?.let { UtilKConnectivityManager.getNetworkCapabilities(context, it) }
}