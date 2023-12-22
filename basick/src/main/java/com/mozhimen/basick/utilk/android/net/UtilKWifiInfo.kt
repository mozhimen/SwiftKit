package com.mozhimen.basick.utilk.android.net

import android.content.Context
import android.net.wifi.WifiInfo
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKWifiInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 14:24
 * @Version 1.0
 */
object UtilKWifiInfo {
    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun get(context: Context): WifiInfo? =
        UtilKWifiManager.getConnectionInfo(context)

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getFrequency(context: Context): Int? =
        get(context)?.frequency

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getSsid(context: Context): String? =
        get(context)?.ssid

    /**
     * 获取当前连接Wi-Fi名
     * # 如果没有定位权限，获取到的名字将为  unknown ssid
     */
    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getRealSsid(context: Context): String? =
        getSsid(context)?.substring(1)

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getRssi(context: Context): Int? =
        get(context)?.rssi

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getRssiAbs(context: Context): Int? =
        getRssi(context)?.let { kotlin.math.abs(it) }

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getIpAddress(context: Context): Int? =
        get(context)?.ipAddress

    /////////////////////////////////////////////////////////////////////////////

    /**
     * 检查当前连接的网络是否为5G WI-FI
     */
    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun isConnect5G(context: Context): Boolean {
        var frequency = 0// 频段
        if (UtilKBuildVersion.isAfterV_21_5_L()) {
            getFrequency(context)?.let { frequency = it }
        } else {
            val ssid = getSsid(context) ?: return false
            if (ssid.length < 2) return false
            val sid = ssid.substring(1)
            for (scan in UtilKWifiManager.getScanResults(context)) {
                if (scan.SSID == sid) {
                    frequency = scan.frequency
                    break
                }
            }
        }
        return frequency in 4900..5900
    }
}