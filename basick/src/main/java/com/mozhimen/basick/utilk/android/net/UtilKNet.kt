package com.mozhimen.basick.utilk.android.net

import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CNetType
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_FINE_LOCATION
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_WIFI_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
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
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    fun getWifiStrength(): Int =
        UtilKWifiInfo.getRssiAbs(_context)
    //endregion

    //region # net
    //获取网路IP
    @JvmStatic
    fun getStrIp(): String? =
        UtilKNetworkInterface.getStrIp()

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActiveNetTypes(): Set<ENetType> =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.activeNetworkCapabilities2netTypes(_context)
        else
            UtilKActiveNetworkInfo.activeNetworkInfo2netTypes(_context)

    //获取连接类型
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getActiveType(): Int =
        UtilKActiveNetworkInfo.getActiveType(_context)

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
    fun isActiveAvailable(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKActiveNetwork.isActiveAvailable(_context) || UtilKNetworkCapabilities.isActiveAvailable(_context)
        else
            UtilKActiveNetworkInfo.isActiveAvailable(_context)//UtilKConnectivityManager.getActiveNetworkInfo(_context)?.isAvailable ?: false

    //是否连接网络,需要权限:ACCESS_NETWORK_STATE
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isActiveConnected(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveConnected(_context)
        else
            UtilKActiveNetworkInfo.isActiveConnected(_context)//UtilKConnectivityManager.getActiveNetworkInfo(_context)?.isAvailable ?: false

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveAny(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveAny(_context)
        else
            UtilKActiveNetworkInfo.isActiveAny(_context)

    //移动网络是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveMobile(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveMobile(_context)
        else
            UtilKActiveNetworkInfo.isActiveMobile(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveEthernet(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveEthernet(_context)
        else
            UtilKActiveNetworkInfo.isActiveEthernet(_context)

    //无线网是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveWifi(): Boolean =
        if (UtilKBuildVersion.isAfterV_26_8_O())
            UtilKNetworkCapabilities.isActiveWifi(_context)
        else
            UtilKActiveNetworkInfo.isActiveWifi(_context)

    //VPN是否活跃
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveVpn(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveVpn(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isActiveVpn(_context)
        else false

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isMobileAvailable(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveMobileAvailable(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isActiveMobileAvailable(_context)
        else
            UtilKNetworkInfo.isMobileAvailable(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isEthernetAvailable(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveEthernetAvailable(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isActiveEthernetAvailable(_context)
        else
            UtilKNetworkInfo.isEthernetAvailable(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isWifiAvailable(): Boolean =
        if (UtilKBuildVersion.isAfterV_26_8_O())
            UtilKNetworkCapabilities.isActiveWifiAvailable(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isActiveWifiAvailable(_context)
        else
            UtilKNetworkInfo.isWifiAvailable(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isVpnAvailable(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveVpnAvailable(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isActiveVpnAvailable(_context) || UtilKNetworkInfo.isVpnAvailable(_context)
        else false

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isMobileConnected(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveMobileConnected(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isActiveMobileConnected(_context)
        else
            UtilKNetworkInfo.isMobileConnected(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isEthernetConnected(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveEthernetConnected(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isActiveEtherNetConnected(_context)
        else
            UtilKNetworkInfo.isEthernetConnected(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isWifiConnected(): Boolean =
        if (UtilKBuildVersion.isAfterV_26_8_O())
            UtilKNetworkCapabilities.isActiveWifiConnected(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isActiveWifiConnected(_context)
        else
            UtilKNetworkInfo.isWifiConnected(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isVpnConnected(): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveVpnConnected(_context)
        else if (UtilKBuildVersion.isAfterV_21_5_L())
            UtilKActiveNetworkInfo.isActiveVpnConnected(_context) || UtilKNetworkInfo.isVpnConnected(_context)
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