package com.mozhimen.basick.elemk.android.net.cons

import android.net.ConnectivityManager
import androidx.annotation.RequiresApi
import com.mozhimen.basick.elemk.android.os.cons.CVersCode

/**
 * @ClassName CConnectivityManager
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/8/7 1:05
 * @Version 1.0
 */
object CConnectivityManager {
    const val TYPE_BLUETOOTH = ConnectivityManager.TYPE_BLUETOOTH
    const val TYPE_DUMMY = ConnectivityManager.TYPE_DUMMY
    const val TYPE_ETHERNET = ConnectivityManager.TYPE_ETHERNET
    const val TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE
    const val TYPE_MOBILE_DUN = ConnectivityManager.TYPE_MOBILE_DUN
    const val TYPE_MOBILE_HIPRI = ConnectivityManager.TYPE_MOBILE_HIPRI
    const val TYPE_MOBILE_MMS = ConnectivityManager.TYPE_MOBILE_MMS
    const val TYPE_MOBILE_SUPL = ConnectivityManager.TYPE_MOBILE_SUPL
    @RequiresApi(CVersCode.V_21_5_L)
    const val TYPE_VPN = ConnectivityManager.TYPE_VPN
    const val TYPE_WIFI = ConnectivityManager.TYPE_WIFI
    const val TYPE_WIMAX = ConnectivityManager.TYPE_WIMAX

    //////////////////////////////////////////////////////////////

    const val CONNECTIVITY_ACTION = ConnectivityManager.CONNECTIVITY_ACTION
}