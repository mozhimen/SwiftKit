package com.mozhimen.basick.utilk.android.net

import android.net.wifi.WifiInfo
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CWifiManager
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.kotlin.text.replaceRegexDoubleQuote

object UtilKWifi : BaseUtilK() {

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getIpAddress(): String {
        var ipAddress = 0
        if (UtilKBuildVersion.isAfterV_29_10_Q()) {
            val activeNetwork = UtilKConnectivityManager.getActiveNetwork(_context)
            activeNetwork?.let {
                (UtilKConnectivityManager.getNetworkCapabilities(_context, it)?.transportInfo as? WifiInfo?)?.let { wifiInfo ->
                    ipAddress = wifiInfo.ipAddress
                }
            }
        } else ipAddress = UtilKWifiInfo.getIpAddress(_context) ?: 0
        if (ipAddress == 0) return ""
        return (ipAddress and 0xFF).toString() + "." + (ipAddress shr 8 and 0xFF) + "." + (ipAddress shr 16 and 0xFF) + "." + (ipAddress shr 24 and 0xFF)
    }

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getName(): String {
        if (!UtilKWifiManager.isWifiEnabled(_context))
            return CWifiManager.DISABLED
        val wifiInfo = UtilKWifiManager.getConnectionInfo(_context) ?: return CWifiManager.NO_CONNECT
        return if (wifiInfo.ssid == CWifiManager.UNKNOWN_SSID) {
            if (UtilKBuildVersion.isAfterV_26_8_O()) {
                if (UtilKPermission.checkPermission(CPermission.ACCESS_FINE_LOCATION)) {
                    val configuredNetworks = UtilKWifiManager.getConfiguredNetworks(_context)
                    if (!configuredNetworks.isNullOrEmpty()) {
                        for (wifiConfiguration in configuredNetworks) {
                            if (wifiConfiguration.networkId == wifiInfo.networkId)
                                return wifiConfiguration.SSID.replaceRegexDoubleQuote()
                        }
                    }
                } else CWifiManager.PERMISSION_DENIED
            } else CWifiManager.NO_CONNECT
            CWifiManager.UNKNOWN
        } else wifiInfo.ssid.replaceRegexDoubleQuote()
    }
}