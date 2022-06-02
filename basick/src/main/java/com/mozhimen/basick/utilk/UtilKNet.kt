package com.mozhimen.basick.utilk

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Process
import com.mozhimen.basick.cachek.CacheKSP

/**
 * @ClassName UtilKNet
 * @Description <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 19:57
 * @Version 1.0
 */
object UtilKNet {
    private val TAG = "UtilKNet>>>>>"
    const val UTILKNET_SP_NAME = "utilknet_sp_name"
    const val UTILKNET_SP_DEGRADE_HTTP = "utilknet_sp_degrade_http"

    /**
     * 是否连接网络,需要权限:ACCESS_NETWORK_STATE
     * @return Boolean
     */
    fun isConnectionUseful(): Boolean {
        val connectivityManager = UtilKGlobal.instance.getApp()!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkInfo = connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.isAvailable
    }

    /**
     * 是否连接无线网
     * @return Boolean
     */
    fun isWifiConnected(): Boolean {
        val connectivityManager = UtilKGlobal.instance.getApp()!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkInfo = connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.state == NetworkInfo.State.CONNECTED && netWorkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 是否连接移动网络
     * @return Boolean
     */
    fun isMobileConnected(): Boolean {
        val connectivityManager = UtilKGlobal.instance.getApp()!!
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkInfo = connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.state == NetworkInfo.State.CONNECTED && netWorkInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    fun degrade2Http() {
        if (CacheKSP.instance.with(UTILKNET_SP_NAME).getBoolean(UTILKNET_SP_DEGRADE_HTTP, false)) return
        CacheKSP.instance.with(UTILKNET_SP_NAME).putBoolean(UTILKNET_SP_DEGRADE_HTTP, true)
        val context = UtilKGlobal.instance.getApp() ?: return
        context.startActivity(context.packageManager.getLaunchIntentForPackage(context.packageName))

        //杀掉当前进程,并主动启动新的启动页,以完成重启的动作
        Process.killProcess(Process.myPid())
    }
}