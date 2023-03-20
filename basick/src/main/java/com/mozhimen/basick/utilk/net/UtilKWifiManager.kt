package com.mozhimen.basick.utilk.net

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import com.mozhimen.basick.utilk.content.UtilKContext
import kotlin.math.abs

/**
 * @ClassName UtilKWifiManager
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2023/3/20 22:12
 * @Version 1.0
 */
object UtilKWifiManager {
    @JvmStatic
    fun get(context: Context): WifiManager =
        UtilKContext.getWifiManager(context)

    @JvmStatic
    fun getConnectionInfo(context: Context): WifiInfo =
        get(context).connectionInfo

    @JvmStatic
    fun getRssi(context: Context): Int =
        getConnectionInfo(context).rssi

    @JvmStatic
    fun getRssiAbs(context: Context): Int =
        abs(getRssi(context))
}