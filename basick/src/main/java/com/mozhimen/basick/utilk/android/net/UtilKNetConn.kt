package com.mozhimen.basick.utilk.android.net

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.bases.BaseUtilK

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
object UtilKNetConn : BaseUtilK() {

    /**
     * 获取Wifi强度
     * @return Int
     */
    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getWifiStrength(): Int =
        UtilKWifiManager.getRssiAbs(_context)?:0

    /**
     * 获取NetworkInfo
     * @return NetworkInfo?
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActiveNetworkInfo(): NetworkInfo? =
        UtilKConnectivityManager.getActiveNetworkInfo(_context)

    /**
     * 获取网路IP
     * @return String
     */
    @JvmStatic
    fun getIp(): String? =
        UtilKNetworkInfo.getIp()

    @JvmStatic
    fun getNetType(): ENetType =
        UtilKNetworkInfo.getNetType(_context)

    /**
     * 获取连接类型
     * @return Int
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getConnectionType(): Int =
        UtilKNetworkInfo.getActiveType(_context) ?: -1

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(value = CPermission.ACCESS_NETWORK_STATE)
    fun registerNetworkCallback(request: NetworkRequest, networkCallback: ConnectivityManager.NetworkCallback) {
        UtilKConnectivityManager.registerNetworkCallback(_context, request, networkCallback)
    }

    @JvmStatic
    fun unregisterNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        UtilKConnectivityManager.unregisterNetworkCallback(_context, networkCallback)
    }

    /////////////////////////////////////////////////////////////////////////

    /**
     * 网络是否连接
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isNetConnected(): Boolean {
        UtilKConnectivityManager.getAllNetworkInfo(_context).forEach {
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
        UtilKNetworkInfo.isActiveAvailable(_context) ?: false//UtilKConnectivityManager.getActiveNetworkInfo(_context)?.isAvailable ?: false

    /**
     * 是否连接无线网
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isWifiConnected(): Boolean =
        UtilKNetworkInfo.isWifiConnected(_context)

    /**
     * 是否连接移动网络
     * @return Boolean
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isMobileConnected(): Boolean=
        UtilKNetworkInfo.isMobileConnected(_context)

    /////////////////////////////////////////////////////////////////////////

    /**
     * 打印连接信息
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun printActiveNetworkInfo() {
        val activeNetworkInfo = UtilKConnectivityManager.getActiveNetworkInfo(_context)
        if (activeNetworkInfo != null) {
            ("isAvailable " + activeNetworkInfo.isAvailable + " isConnected " + activeNetworkInfo.isConnected + " networkInfo " + activeNetworkInfo.toString()).dt(TAG)
            ("subtypeName " + activeNetworkInfo.subtypeName + " typeName " + activeNetworkInfo.typeName + " extraInfo " + activeNetworkInfo.extraInfo).dt(TAG)
        }
    }
}