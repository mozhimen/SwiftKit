package com.mozhimen.basick.utilk.wrapper

import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CNetType
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_FINE_LOCATION
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_WIFI_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.net.UtilKActiveNetwork
import com.mozhimen.basick.utilk.android.net.UtilKActiveNetworkInfo
import com.mozhimen.basick.utilk.android.net.UtilKNetworkCapabilities
import com.mozhimen.basick.utilk.android.net.UtilKNetworkInfo
import com.mozhimen.basick.utilk.android.net.UtilKWifiInfo
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.net.UtilKNetworkInterface

/**
 * @ClassName UtilKNet
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 19:57
 * @Version 1.0
 */
fun ENetType.eNetType2strNetType(): String =
    UtilKNet.eNetType2strNetType(this)

object UtilKNet : BaseUtilK() {

    //region # wifi
    //获取Wifi强度
    @JvmStatic
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    fun getWifiStrength(): Int =
        UtilKWifiInfo.getRssiAbs_ofMgr(_context)
    //endregion

    //region # net
    //获取网路IP
    @JvmStatic
    fun getStrIP(): String? =
        UtilKNetworkInterface.getStrIP()

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getNetTypes_ofActive(): Set<ENetType> =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.networkCapabilities2netTypes_ofActive(_context)
        else
            UtilKActiveNetworkInfo.networkInfo2netTypes(_context)

    //获取连接类型
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getType_ofActive(): Int =
        UtilKActiveNetworkInfo.getType(_context)

    /////////////////////////////////////////////////////////////////////////

    //网络是否连接
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun hasConnected(): Boolean =
        UtilKNetworkInfo.hasNetworkConnected(_context)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isAvailable_ofActive(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKActiveNetwork.isAvailable(_context) || UtilKNetworkCapabilities.isAvailable_ofActive(_context)
        else
            UtilKActiveNetworkInfo.isAvailable(_context)//UtilKConnectivityManager.getActiveNetworkInfo(_context)?.isAvailable ?: false

    //是否连接网络,需要权限:ACCESS_NETWORK_STATE
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isConnected_ofActive(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isConnected_ofActive(_context)
        else
            UtilKActiveNetworkInfo.isConnected(_context)//UtilKConnectivityManager.getActiveNetworkInfo(_context)?.isAvailable ?: false

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isAny_ofActive(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isAny_ofActive(_context)
        else
            UtilKActiveNetworkInfo.isAny(_context)

    //移动网络是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isMobile_ofActive(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isMobile_ofActive(_context)
        else
            UtilKActiveNetworkInfo.isMobile(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isEthernet_ofActive(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isEthernet_ofActive(_context)
        else
            UtilKActiveNetworkInfo.isEthernet(_context)

    //无线网是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isWifi_ofActive(): Boolean =
        if (UtilKBuildVersion.isAfterV_26_8_O())
            UtilKNetworkCapabilities.isWifi_ofActive(_context)
        else
            UtilKActiveNetworkInfo.isWifi(_context)

    //VPN是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isVpn_ofActive(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isVpn_ofActive(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isVpn(_context)
        else false

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isMobileAvailable(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isMobileAvailable_ofActive(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isMobileAvailable(_context)
        else
            UtilKNetworkInfo.isMobileAvailable(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isEthernetAvailable(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isEthernetAvailable_ofActive(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isEthernetAvailable(_context)
        else
            UtilKNetworkInfo.isEthernetAvailable(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isWifiAvailable(): Boolean =
        if (UtilKBuildVersion.isAfterV_26_8_O())
            UtilKNetworkCapabilities.isWifiAvailable_ofActive(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isWifiAvailable(_context)
        else
            UtilKNetworkInfo.isWifiAvailable(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isVpnAvailable(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isVpnAvailable_ofActive(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isVpnAvailable(_context) || UtilKNetworkInfo.isVpnAvailable(_context)
        else false

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isMobileConnected(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isMobileConnected_ofActive(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isMobileConnected(_context)
        else
            UtilKNetworkInfo.isMobileConnected(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isEthernetConnected(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isEthernetConnected_ofActive(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isEtherNetConnected(_context)
        else
            UtilKNetworkInfo.isEthernetConnected(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isWifiConnected(): Boolean =
        if (UtilKBuildVersion.isAfterV_26_8_O())
            UtilKNetworkCapabilities.isWifiConnected_ofActive(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isWifiConnected(_context)
        else
            UtilKNetworkInfo.isWifiConnected(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isVpnConnected(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isVpnConnected_ofActive(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isVpnConnected(_context) || UtilKNetworkInfo.isVpnConnected(_context)
        else false

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun eNetType2strNetType(type: ENetType): String =
        when (type) {
            ENetType.MOBILE -> CNetType.MOBILE
            ENetType.MOBILE_2G -> CNetType.MOBILE_2G
            ENetType.MOBILE_3G -> CNetType.MOBILE_3G
            ENetType.MOBILE_4G -> CNetType.MOBILE_4G
            ENetType.WIFI -> CNetType.WIFI
            ENetType.VPN -> CNetType.VPN
            ENetType.UNKNOWN -> CNetType.UNKNOWN
            else -> CNetType.NONE
        }

    /////////////////////////////////////////////////////////////////////////

//    private val UTILKNET_SP_NAME = "utilknet_sp_name"
//    private val UTILKNET_SP_DEGRADE_HTTP = "utilknet_sp_degrade_http"
//    /**
//     * 协议降级
//     */
//    @JvmStatic
//    fun degrade2Http() {
//        if (CacheKSP.instance.with(UTILKNET_SP_NAME).getBoolean(UTILKNET_SP_DEGRADE_HTTP, false)) return
//        CacheKSP.instance.with(UTILKNET_SP_NAME).putBoolean(UTILKNET_SP_DEGRADE_HTTP, true)
//        Thread.sleep(100)
//        UtilKApp.restartApp(isKillProcess = true)
//    }
    //endregion
}