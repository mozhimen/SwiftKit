package com.mozhimen.swiftmk.helper.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * @ClassName NetworkObserver
 * @Description TODO
 * @Author Kolin Zhao
 * @Date 2021/7/17 14:48
 * @Version 1.0
 */
object NetworkObserver {
    fun isNetConnected(context: Context?): Boolean {
        context?.let {
            val connectivityManager =
                it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netWorkInfo = connectivityManager.activeNetworkInfo
            return netWorkInfo != null && netWorkInfo.isAvailable
        }
        return false
    }

    fun isWifiConnected(context: Context?): Boolean {
        context?.let {
            val connectivityManager =
                it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netWorkInfo = connectivityManager.activeNetworkInfo
            return netWorkInfo != null && netWorkInfo.state == NetworkInfo.State.CONNECTED && netWorkInfo.type == ConnectivityManager.TYPE_WIFI
        }
        return false
    }

    fun isMobileConnected(context: Context?): Boolean {
        context?.let {
            val connectivityManager =
                it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netWorkInfo = connectivityManager.activeNetworkInfo
            return netWorkInfo != null && netWorkInfo.state == NetworkInfo.State.CONNECTED && netWorkInfo.type == ConnectivityManager.TYPE_MOBILE
        }
        return false
    }
}