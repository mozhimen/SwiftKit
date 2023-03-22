package com.mozhimen.basick.utilk.net

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.content.UtilKApplication
import com.mozhimen.basick.utilk.content.UtilKContext
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*

/**
 * @ClassName UtilKNet
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 19:57
 * @Version 1.0
 */
@AManifestKRequire(
    CPermission.ACCESS_NETWORK_STATE,
    CPermission.ACCESS_WIFI_STATE,
    CPermission.INTERNET
)
object UtilKNetConn {
    private val TAG = "UtilKNet>>>>>"
    private val _context = UtilKApplication.instance.get()

    /**
     * 获取Wifi强度
     * @return Int
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_WIFI_STATE)
    fun getWifiStrength(): Int {
        return UtilKWifiManager.getRssiAbs(_context)
    }

    /**
     * 获取NetworkInfo
     * @return NetworkInfo?
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActiveNetworkInfo(): NetworkInfo? {
        return UtilKConnManager.getActiveNetworkInfo(_context)
    }

    /**
     * 获取网路IP
     * @return String
     */
    @JvmStatic
    fun getIP(): String? {
        try {
            val networkInterfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
            var inetAddress: InetAddress
            while (networkInterfaces.hasMoreElements()) {
                val inetAddresses: Enumeration<InetAddress> = (networkInterfaces.nextElement() as NetworkInterface).inetAddresses
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement() as InetAddress
                    if (inetAddress !is Inet6Address) {
                        if (inetAddress.hostAddress != "127.0.0.1") {
                            return inetAddress.hostAddress ?: continue
                        }
                    }
                }
            }
        } catch (e: SocketException) {
            e.printStackTrace()
            Log.e(TAG, "getIP SocketException ${e.message}")
        }
        return null
    }

    /**
     * 网络是否连接
     * @return Boolean
     */
    @JvmStatic
    fun isNetConnected(): Boolean {
        UtilKConnManager.getAllNetworkInfo(_context).forEach {
            if (it.isConnected) return true
        }
        return false
    }

    /**
     * 是否连接网络,需要权限:ACCESS_NETWORK_STATE
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isNetAvailable(): Boolean =
        UtilKConnManager.getActiveNetworkInfo(_context)?.isAvailable ?: false

    /**
     * 是否连接无线网
     * @return Boolean
     */
    @JvmStatic
    fun isWifiConnected(): Boolean {
        if (UtilKContext.checkCallingOrSelfPermission(_context,CPermission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) return false
        val activeNetworkInfo = UtilKConnManager.getActiveNetworkInfo(_context)
        return activeNetworkInfo != null && activeNetworkInfo.state == NetworkInfo.State.CONNECTED && activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 是否连接移动网络
     * @return Boolean
     */
    @JvmStatic
    fun isMobileConnected(): Boolean {
        val activeNetworkInfo = UtilKConnManager.getActiveNetworkInfo(_context)
        return activeNetworkInfo != null && activeNetworkInfo.state == NetworkInfo.State.CONNECTED && activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * 获取连接类型
     * @return Int
     */
    @JvmStatic
    fun getConnectionType(): Int =
        UtilKConnManager.getActiveNetworkInfo(_context)?.type ?: -1

    /**
     * 打印连接信息
     */
    @JvmStatic
    fun printActiveNetworkInfo() {
        val activeNetworkInfo = UtilKConnManager.getActiveNetworkInfo(_context)
        if (activeNetworkInfo != null) {
            Log.i(TAG, "isAvailable " + activeNetworkInfo.isAvailable + " isConnected " + activeNetworkInfo.isConnected + " networkInfo " + activeNetworkInfo.toString())
            Log.i(TAG, "subtypeName " + activeNetworkInfo.subtypeName + " typeName " + activeNetworkInfo.typeName + " extraInfo " + activeNetworkInfo.extraInfo)
        }
    }
}