package com.mozhimen.basicsmk.utilmk

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

/**
 * @ClassName UtilMKNet
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 19:57
 * @Version 1.0
 */
object UtilMKNet {
    private const val TAG = "UtilMKNet"

    /**
     * need permission
     */
    fun isConnectionUseful(): Boolean {
        val connectivityManager = UtilMKGlobal.instance.getApp()!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return if (info != null && info.isAvailable) {
            val name = info.typeName
            Log.d(TAG, "isConnectIsNormal: $name")
            true
        } else {
            Log.d(TAG, "isConnectionUseful: false")
            false
        }
    }
}