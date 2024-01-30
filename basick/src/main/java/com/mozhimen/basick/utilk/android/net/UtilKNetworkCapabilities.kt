package com.mozhimen.basick.utilk.android.net

import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.net.cons.CNetworkCapabilities
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName UtilKNetworkCapabilities
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 11:56
 * @Version 1.0
 */
@RequiresApi(CVersCode.V_21_5_L)
fun NetworkCapabilities.networkCapabilities2netType(): ENetType =
    UtilKNetworkCapabilities.networkCapabilities2netType(this)

object UtilKNetworkCapabilities {
    @RequiresApi(CVersCode.V_21_5_L)
    @JvmStatic
    fun networkCapabilities2netType(capabilities: NetworkCapabilities): ENetType =
        if (!capabilities.hasCapability(CNetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            ENetType.NONE
        } else {
            if (capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_WIFI_AWARE)) {
                ENetType.WIFI// 使用WI-FI
            } else if (capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(CNetworkCapabilities.TRANSPORT_ETHERNET)) {
                ENetType.MOBILE// 使用蜂窝网络
            } else {
                ENetType.UNKNOWN// 未知网络，包括蓝牙、VPN、LoWPAN
            }
        }
}