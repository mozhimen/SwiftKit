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
    //表示此网络使用蓝牙传输
    @RequiresApi(CVersCode.V_21_5_L)
    const val TRANSPORT_BLUETOOTH = NetworkCapabilities.TRANSPORT_BLUETOOTH

    //表示此网络使用移动蜂窝传输
    @RequiresApi(CVersCode.V_21_5_L)
    const val TRANSPORT_CELLULAR = NetworkCapabilities.TRANSPORT_CELLULAR

    //表示此网络使用以太网传输
    @RequiresApi(CVersCode.V_21_5_L)
    const val TRANSPORT_ETHERNET = NetworkCapabilities.TRANSPORT_ETHERNET

    //表示此网络使用低功耗传输
    @RequiresApi(CVersCode.V_27_81_O1)
    const val TRANSPORT_LOWPAN = NetworkCapabilities.TRANSPORT_LOWPAN

    @RequiresApi(CVersCode.V_31_12_S)
    const val TRANSPORT_USB = NetworkCapabilities.TRANSPORT_USB

    //指示此网络使用VPN传输
    @RequiresApi(CVersCode.V_21_5_L)
    const val TRANSPORT_VPN = NetworkCapabilities.TRANSPORT_VPN

    //表示此网络使用Wi-Fi传输
    @RequiresApi(CVersCode.V_21_5_L)
    const val TRANSPORT_WIFI = NetworkCapabilities.TRANSPORT_WIFI//表示此网络使用Wi-Fi传输

    //表示此网络使用支持 Wi-Fi Aware 的传输
    @RequiresApi(CVersCode.V_26_8_O)
    const val TRANSPORT_WIFI_AWARE = NetworkCapabilities.TRANSPORT_WIFI_AWARE

    //////////////////////////////////////////////////////////////////

    //表示是否连接上了互联网（不关心是否可以上网）
    @RequiresApi(CVersCode.V_21_5_L)
    const val NET_CAPABILITY_INTERNET = NetworkCapabilities.NET_CAPABILITY_INTERNET

    //表示能够和互联网通信（这个为true表示能够上网）
    @RequiresApi(CVersCode.V_23_6_M)
    const val NET_CAPABILITY_VALIDATED = NetworkCapabilities.NET_CAPABILITY_VALIDATED
}