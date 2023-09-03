package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKWifiManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 22:12
 * @Version 1.0
 */
@AManifestKRequire(CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION)
object UtilKWifiManager {
    @JvmStatic
    fun get(context: Context): WifiManager =
        UtilKContext.getWifiManager(context)

    /////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getConnectionInfo(context: Context): WifiInfo? =
        get(context).connectionInfo

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getRssi(context: Context): Int? =
        getConnectionInfo(context)?.rssi

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getSsid(context: Context): String? =
        getConnectionInfo(context)?.ssid

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getRssiAbs(context: Context): Int? =
        getRssi(context)?.let { kotlin.math.abs(it) }

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getConfiguredNetworks(context: Context): List<WifiConfiguration>? =
        get(context).configuredNetworks

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getConnectionInfoIpAddress(context: Context): Int? =
        getConnectionInfo(context)?.ipAddress

    /////////////////////////////////////////////////////////////////////////////

    fun isWifiEnabled(context: Context): Boolean =
        get(context).isWifiEnabled
}