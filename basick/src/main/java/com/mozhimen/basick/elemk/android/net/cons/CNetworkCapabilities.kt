package com.mozhimen.basick.elemk.android.net.cons

import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CNetworkCapabilities
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/7 2:07
 * @Version 1.0
 */
object CNetworkCapabilities {
    const val TRANSPORT_WIFI = NetworkCapabilities.TRANSPORT_WIFI
    const val TRANSPORT_CELLULAR = NetworkCapabilities.TRANSPORT_CELLULAR
    const val TRANSPORT_ETHERNET = NetworkCapabilities.TRANSPORT_ETHERNET
    @RequiresApi(CVersCode.V_23_6_M)
    const val NET_CAPABILITY_VALIDATED = NetworkCapabilities.NET_CAPABILITY_VALIDATED
    @RequiresApi(CVersCode.V_26_8_O)
    const val TRANSPORT_WIFI_AWARE = NetworkCapabilities.TRANSPORT_WIFI_AWARE
}