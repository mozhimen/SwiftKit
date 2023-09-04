package com.mozhimen.basick.elemk.android.net.cons

import android.net.wifi.WifiManager
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

object CWifiManager {
    @RequiresApi(CVersCode.V_30_11_R)
    const val UNKNOWN_SSID = WifiManager.UNKNOWN_SSID
    const val UNKNOWN = "<unknown>"
    const val DISABLED = "<disabled>"
    const val NO_CONNECT = "<not connect>"
    const val PERMISSION_DENIED = "<permission denied>"
}