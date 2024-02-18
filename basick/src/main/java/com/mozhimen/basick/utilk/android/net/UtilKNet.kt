package com.mozhimen.basick.utilk.android.net

import android.content.Context
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CNetType
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_FINE_LOCATION
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_WIFI_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.dt
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

    //获取Wifi强度
    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    fun getWifiStrength(): Int =
        UtilKWifiInfo.getRssiAbs(_context) ?: 0

    //获取网路IP
    @JvmStatic
    fun getStrIP(): String? =
        UtilKNetworkInterface.getStrIP()

    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun getActiveNetType(): ENetType =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.activeNetworkCapabilities2netType(_context) ?: ENetType.NONE
        else
            UtilKActiveNetworkInfo.activeNetworkInfo2netType(_context)

    //获取连接类型
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getActiveType(): Int =
        UtilKActiveNetworkInfo.getActiveType(_context) ?: -1

    /////////////////////////////////////////////////////////////////////////

    //网络是否连接
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun hasConnected(): Boolean =
        UtilKNetworkInfo.hasNetworkConnected(_context)

    /**
     * 是否连接网络,需要权限:ACCESS_NETWORK_STATE
     */
    @JvmStatic
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    fun isActiveAvailable(): Boolean =
        UtilKActiveNetworkInfo.isActiveAvailable(_context) ?: false//UtilKConnectivityManager.getActiveNetworkInfo(_context)?.isAvailable ?: false

    /**
     * 是否连接无线网
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveWifi(): Boolean =
        UtilKActiveNetworkInfo.isActiveWifi(_context)

    /**
     * 是否连接移动网络
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveMobile(): Boolean =
        UtilKActiveNetworkInfo.isActiveMobile(_context)

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isActiveConnected(context: Context): Boolean =
        if (UtilKBuildVersion.isAfterV_23_6_M())
            UtilKNetworkCapabilities.isActiveConnected(context)
        else UtilKActiveNetworkInfo.isActiveConnected(context)

    /////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun eNetType2strNetType(type: ENetType): String =
        when (type) {
            ENetType.WIFI -> CNetType.WIFI
            ENetType.MOBILE_4G -> CNetType.MOBILE_4G
            ENetType.MOBILE_3G -> CNetType.MOBILE_3G
            ENetType.MOBILE_2G -> CNetType.MOBILE_2G
            ENetType.MOBILE -> CNetType.MOBILE
            ENetType.UNKNOWN -> CNetType.UNKNOWN
            else -> CNetType.NONE
        }

    /////////////////////////////////////////////////////////////////////////

    /**
     * 打印连接信息
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun printActiveNetworkInfo() {
        val activeNetworkInfo = UtilKConnectivityManager.getActiveNetworkInfo(_context)
        if (activeNetworkInfo != null) {
            ("isAvailable " + activeNetworkInfo.isAvailable + " isConnected " + activeNetworkInfo.isConnected + " networkInfo " + activeNetworkInfo.toString()).dt(TAG)
            ("subtypeName " + activeNetworkInfo.subtypeName + " typeName " + activeNetworkInfo.typeName + " extraInfo " + activeNetworkInfo.extraInfo).dt(TAG)
        }
    }

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
}