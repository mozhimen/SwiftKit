package com.mozhimen.basick.utilk.android.net

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiInfo
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_FINE_LOCATION
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_WIFI_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.kotlin.intIp2strIp

/**
 * @ClassName UtilKWifiInfo
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/9/27 14:24
 * @Version 1.0
 */
object UtilKWifiInfo {
    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun get_ofMgr(context: Context): WifiInfo? =
        UtilKWifiManager.getConnectionInfo(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun get_ofActive(context: Context): WifiInfo? =
        UtilKTransportInfo.getWifiInfo_ofActive(context)

    @JvmStatic
    @RequiresApi(CVersCode.V_29_10_Q)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getIpAddress_ofActive(context: Context):Int? =
        get_ofActive(context)?.ipAddress

    //////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresApi(CVersCode.V_21_5_L)
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getFrequency_ofMgr(context: Context): Int? =
        get_ofMgr(context)?.frequency

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getSsid_ofMgr(context: Context): String? =
        get_ofMgr(context)?.ssid

    /**
     * 获取当前连接Wi-Fi名
     * # 如果没有定位权限，获取到的名字将为  unknown ssid
     */
    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getRealSsid_ofMgr(context: Context): String? =
        getSsid_ofMgr(context)?.substring(1)

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getRssi_ofMgr(context: Context): Int? =
        get_ofMgr(context)?.rssi

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getRssiAbs_ofMgr(context: Context): Int =
        getRssi_ofMgr(context)?.let { kotlin.math.abs(it) } ?: 0

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getIpAddress_ofMgr(context: Context): Int? =
        get_ofMgr(context)?.ipAddress

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getStrIp_ofMgr(context: Context): String? =
        getIpAddress_ofMgr(context)?.intIp2strIp()

    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    @SuppressLint("HardwareIds")
    fun getMacAddress_ofMgr(context: Context): String? =
        get_ofMgr(context)?.macAddress

    /////////////////////////////////////////////////////////////////////////////

    /**
     * 检查当前连接的网络是否为5G WI-FI
     */
    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun is5GConnected_ofMgr(context: Context): Boolean {
        var frequency = 0// 频段
        if (UtilKBuildVersion.isAfterV_21_5_L()) {
            getFrequency_ofMgr(context)?.let { frequency = it }
        } else {
            val ssid = getSsid_ofMgr(context) ?: return false
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