package com.mozhimen.basick.utilk

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Process
import com.mozhimen.basick.cachek.CacheKSP
import java.net.URI
import java.net.URISyntaxException

/**
 * @ClassName UtilKNet
 * @Description <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 19:57
 * @Version 1.0
 */
object UtilKNet {
    private val TAG = "UtilKNet>>>>>"
    private val _context = UtilKGlobal.instance.getApp()!!
    private val _connectivityManager = _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private const val utilknet_sp_name = "utilknet_sp_name"
    private const val utilknet_sp_degrade_http = "utilknet_sp_degrade_http"

    /**
     * 是否连接网络,需要权限:ACCESS_NETWORK_STATE
     * @return Boolean
     */
    @JvmStatic
    fun isConnectionUseful(): Boolean {
        val netWorkInfo = _connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.isAvailable
    }

    /**
     * 是否连接无线网
     * @return Boolean
     */
    @JvmStatic
    fun isWifiConnected(): Boolean {
        val netWorkInfo = _connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.state == NetworkInfo.State.CONNECTED && netWorkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 是否连接移动网络
     * @return Boolean
     */
    @JvmStatic
    fun isMobileConnected(): Boolean {
        val netWorkInfo = _connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.state == NetworkInfo.State.CONNECTED && netWorkInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * 判断url是否合法
     * @param url String
     * @return Boolean
     */
    @JvmStatic
    fun isUrlAvailable(url: String): Boolean {
        val uri: URI?
        try {
            uri = URI(url)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return false
        }
        if (uri.host == null) {
            return false
        } else if (!uri.scheme.equals("http") && !uri.scheme.equals("https")) {
            return false
        }
        return true
    }

    @JvmStatic
    fun degrade2Http() {
        if (CacheKSP.instance.with(utilknet_sp_name).getBoolean(utilknet_sp_degrade_http, false)) return
        CacheKSP.instance.with(utilknet_sp_name).putBoolean(utilknet_sp_degrade_http, true)
        val context = UtilKGlobal.instance.getApp() ?: return
        context.startActivity(context.packageManager.getLaunchIntentForPackage(context.packageName))

        //杀掉当前进程,并主动启动新的启动页,以完成重启的动作
        Process.killProcess(Process.myPid())
    }
}