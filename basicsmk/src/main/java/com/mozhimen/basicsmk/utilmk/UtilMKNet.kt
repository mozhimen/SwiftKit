package com.mozhimen.basicsmk.utilmk

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @ClassName UtilMKNet
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 19:57
 * @Version 1.0
 */
object UtilMKNet {
    private const val TAG = "UtilMKNet>>>>>"

    /**
     * need permission
     */
    fun isConnectionUseful(): Boolean {
        val connectivityManager = UtilMKGlobal.instance.getApp()!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkInfo = connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.isAvailable
    }

    fun isWifiConnected(): Boolean {
        val connectivityManager = UtilMKGlobal.instance.getApp()!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkInfo = connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.state == NetworkInfo.State.CONNECTED && netWorkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    fun isMobileConnected(): Boolean {
        val connectivityManager = UtilMKGlobal.instance.getApp()!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkInfo = connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.state == NetworkInfo.State.CONNECTED && netWorkInfo.type == ConnectivityManager.TYPE_MOBILE
    }
}