package com.mozhimen.basick.utilk.net

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.util.Log
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.content.UtilKApplication
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*
import kotlin.math.abs

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
    private val _connectivityManager by lazy { _context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    private val _wifiManager by lazy { _context.getSystemService(Context.WIFI_SERVICE) as WifiManager }

    /**
     * 获取Wifi强度
     * @return Int
     */
    @JvmStatic
    fun getWifiStrength(): Int {
        return abs(_wifiManager.connectionInfo.rssi)
    }

    /**
     * 获取NetworkInfo
     * @return NetworkInfo?
     */
    @JvmStatic
    fun getActiveNetworkInfo(): NetworkInfo? {
        return _connectivityManager.activeNetworkInfo
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
            Log.e(TAG, "getDeviceIP SocketException ${e.message}")
        }
        return null
    }

    /**
     * 网络是否连接
     * @param context Context
     * @return Boolean
     */
    fun isNetConnected(context: Context): Boolean {
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
    fun isNetAvailable(): Boolean {
        val netWorkInfo = _connectivityManager.activeNetworkInfo
        return netWorkInfo != null && netWorkInfo.isAvailable
    }

    /**
     * 是否连接无线网
     * @return Boolean
     */
    @JvmStatic
    fun isWifiConnected(): Boolean {
        if (_context.checkCallingOrSelfPermission(CPermission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) return false
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