package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.Network
import android.net.NetworkCapabilities
import android.net.TransportInfo
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission

/**
 * @ClassName UtilKActiveNetwork
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/2/19
 * @Version 1.0
 */
object UtilKActiveNetwork {
    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun get(context: Context): Network? =
        UtilKNetwork.get_ofActive(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getNetworkCapabilities(context: Context): NetworkCapabilities? =
        get(context)?.let { UtilKConnectivityManager.getNetworkCapabilities(context, it) }

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getTransportInfo(context: Context): TransportInfo? =
        getNetworkCapabilities(context)?.transportInfo

    ///////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresApi(CVersCode.V_23_6_M)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isAvailable(context: Context): Boolean =
        get(context) != null
}