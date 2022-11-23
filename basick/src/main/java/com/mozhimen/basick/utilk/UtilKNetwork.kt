package com.mozhimen.basick.utilk

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Process
import android.util.Log
import com.mozhimen.basick.cachek.CacheKSP
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.context.UtilKApplication

/**
 * @ClassName UtilKNet
 * @Description <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 19:57
 * @Version 1.0
 */
@APermissionK([Manifest.permission.ACCESS_NETWORK_STATE])
object UtilKNetwork {
    private val TAG = "UtilKNet>>>>>"
    private val _context = UtilKApplication.instance.get()
    private val _connectivityManager = _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private const val utilknet_sp_name = "utilknet_sp_name"
    private const val utilknet_sp_degrade_http = "utilknet_sp_degrade_http"

    /**
     * 网络是否连接
     * @param context Context
     * @return Boolean
     */
    fun isNetworkConnected(context: Context): Boolean {
        val netWorkInfos = _connectivityManager.allNetworkInfo
        netWorkInfos.forEach {
            if (it.isConnected) return true
        }
        return false
    }

    /**
     * 是否连接网络,需要权限:ACCESS_NETWORK_STATE
     * @return Boolean
     */
    @JvmStatic
    fun isNetworkAvailable(): Boolean {
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
     * 获取连接类型
     * @return Int
     */
    @JvmStatic
    fun getConnectionType(): Int {
        val networkInfo = _connectivityManager.activeNetworkInfo
        return networkInfo?.type ?: -1
    }

    /**
     * 打印连接信息
     * @param context Context
     */
    @JvmStatic
    fun printNetworkInfo(context: Context) {
        val networkInfo = _connectivityManager.activeNetworkInfo
        if (networkInfo != null) {
            Log.i(TAG, "isAvailable " + networkInfo.isAvailable + " isConnected " + networkInfo.isConnected + " networkInfo " + networkInfo.toString())
            Log.i(TAG, "subtypeName " + networkInfo.subtypeName + " typeName " + networkInfo.typeName + " extraInfo " + networkInfo.extraInfo)
        }
    }

    @JvmStatic
    fun degrade2Http() {
        if (CacheKSP.instance.with(utilknet_sp_name).getBoolean(utilknet_sp_degrade_http, false)) return
        CacheKSP.instance.with(utilknet_sp_name).putBoolean(utilknet_sp_degrade_http, true)
        _context.startActivity(_context.packageManager.getLaunchIntentForPackage(_context.packageName))
        //杀掉当前进程,并主动启动新的启动页,以完成重启的动作
        Process.killProcess(Process.myPid())
    }
}