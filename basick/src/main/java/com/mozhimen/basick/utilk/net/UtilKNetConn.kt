package com.mozhimen.basick.utilk.net

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Process
import android.util.Log
import com.mozhimen.basick.cachek.CacheKSP
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.app.UtilKApp
import com.mozhimen.basick.utilk.context.UtilKActivitySkip
import com.mozhimen.basick.utilk.context.UtilKApplication

/**
 * @ClassName UtilKNet
 * @Description <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 19:57
 * @Version 1.0
 */
@APermissionK(Manifest.permission.ACCESS_NETWORK_STATE)
object UtilKNetConn {
    private val TAG = "UtilKNet>>>>>"
    private val _context = UtilKApplication.instance.get()
    private val _connectivityManager = _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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
     */
    @JvmStatic
    fun printNetworkInfo() {
        val networkInfo = _connectivityManager.activeNetworkInfo
        if (networkInfo != null) {
            Log.i(TAG, "isAvailable " + networkInfo.isAvailable + " isConnected " + networkInfo.isConnected + " networkInfo " + networkInfo.toString())
            Log.i(TAG, "subtypeName " + networkInfo.subtypeName + " typeName " + networkInfo.typeName + " extraInfo " + networkInfo.extraInfo)
        }
    }
}