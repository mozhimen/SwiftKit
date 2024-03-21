package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.TransportInfo
import android.net.wifi.WifiInfo
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.manifestk.cons.CPermission

/**
 * @ClassName UtilKTransportInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/3/21
 * @Version 1.0
 */
object UtilKTransportInfo {
    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun get_ofActive(context: Context): TransportInfo? =
        UtilKActiveNetwork.getTransportInfo(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getWifiInfo_ofActive(context: Context): WifiInfo? =
        get_ofActive(context) as? WifiInfo?
}