package com.mozhimen.basick.utilk.android.net

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkRequest
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.net.cons.CConnectivityManager
import com.mozhimen.basick.elemk.android.net.cons.CNetType
import com.mozhimen.basick.elemk.android.net.cons.ENetType
import com.mozhimen.basick.elemk.android.os.cons.CVersCode
import com.mozhimen.basick.elemk.android.telephony.CTelephonyManager
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_FINE_LOCATION
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_NETWORK_STATE
import com.mozhimen.basick.lintk.optins.permission.OPermission_ACCESS_WIFI_STATE
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.java.net.UtilKNetworkInterface
import com.mozhimen.basick.utilk.kotlin.equalsIgnoreCase

/**
 * @ClassName UtilKNet
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/2/16 19:57
 * @Version 1.0
 */
fun ENetType.eNetType2strNetType(): String =
    UtilKNetConn.eNetType2strNetType(this)

object UtilKNetConn : BaseUtilK() {

    /**
     * 获取Wifi强度
     */
    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_WIFI_STATE, CPermission.ACCESS_FINE_LOCATION])
    @OPermission_ACCESS_WIFI_STATE
    @OPermission_ACCESS_FINE_LOCATION
    fun getWifiStrength(): Int =
        UtilKWifiInfo.getRssiAbs(_context) ?: 0

    /**
     * 获取网路IP
     */
    @JvmStatic
    fun getStrIP(): String? =
        UtilKNetworkInterface.getStrIP()

    @JvmStatic
    fun getNetType(): ENetType {
        if (UtilKBuildVersion.isAfterV_23_6_M()) {
            return UtilKActiveNetwork.getNetworkCapabilities(_context)?.networkCapabilities2netType() ?: ENetType.NONE
        } else {
            val activeNetworkInfo: NetworkInfo? = UtilKActiveNetworkInfo.get(_context)
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable) return ENetType.NONE
            return when (activeNetworkInfo.type) {
                CConnectivityManager.TYPE_WIFI -> ENetType.WIFI
                CConnectivityManager.TYPE_MOBILE -> when (activeNetworkInfo.subtype) {
                    CTelephonyManager.NETWORK_TYPE_LTE, CTelephonyManager.NETWORK_TYPE_IWLAN -> ENetType.MOBILE_4G
                    CTelephonyManager.NETWORK_TYPE_TD_SCDMA, CTelephonyManager.NETWORK_TYPE_EVDO_A, CTelephonyManager.NETWORK_TYPE_UMTS, CTelephonyManager.NETWORK_TYPE_EVDO_0, CTelephonyManager.NETWORK_TYPE_HSDPA, CTelephonyManager.NETWORK_TYPE_HSUPA, CTelephonyManager.NETWORK_TYPE_HSPA, CTelephonyManager.NETWORK_TYPE_EVDO_B, CTelephonyManager.NETWORK_TYPE_EHRPD, CTelephonyManager.NETWORK_TYPE_HSPAP -> ENetType.MOBILE_3G
                    CTelephonyManager.NETWORK_TYPE_GSM, CTelephonyManager.NETWORK_TYPE_GPRS, CTelephonyManager.NETWORK_TYPE_CDMA, CTelephonyManager.NETWORK_TYPE_EDGE, CTelephonyManager.NETWORK_TYPE_1xRTT, CTelephonyManager.NETWORK_TYPE_IDEN -> ENetType.MOBILE_2G
                    else -> {
                        val subtypeName = activeNetworkInfo.subtypeName
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA") || subtypeName.equalsIgnoreCase("WCDMA") || subtypeName.equalsIgnoreCase("CDMA2000")) ENetType.MOBILE_3G
                        else ENetType.MOBILE
                    }
                }

                else -> ENetType.UNKNOWN
            }
        }
    }

    /**
     * 获取连接类型
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun getConnectionType(): Int =
        UtilKActiveNetworkInfo.getType(_context) ?: -1

    /////////////////////////////////////////////////////////////////////////

    /**
     * 网络是否连接
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isNetConnected(): Boolean {
        UtilKConnectivityManager.getAllNetworkInfo(_context).forEach {
            if (it.isConnected) return true
        }
        return false
    }

    /**
     * 是否连接网络,需要权限:ACCESS_NETWORK_STATE
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isNetAvailable(): Boolean =
        UtilKActiveNetworkInfo.isAvailable(_context) ?: false//UtilKConnectivityManager.getActiveNetworkInfo(_context)?.isAvailable ?: false

    /**
     * 是否连接无线网
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isWifiConnected(): Boolean =
        UtilKActiveNetworkInfo.isWifiConnected(_context)

    /**
     * 是否连接移动网络
     */
    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    fun isMobileConnected(): Boolean =
        UtilKActiveNetworkInfo.isMobileConnected(_context)

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

    @JvmStatic
    @RequiresPermission(CPermission.ACCESS_NETWORK_STATE)
    @OPermission_ACCESS_NETWORK_STATE
    @RequiresApi(CVersCode.V_21_5_L)
    fun registerNetworkCallback(request: NetworkRequest, networkCallback: ConnectivityManager.NetworkCallback) {
        UtilKConnectivityManager.registerNetworkCallback(_context, request, networkCallback)
    }

    @JvmStatic
    @RequiresApi(CVersCode.V_21_5_L)
    fun unregisterNetworkCallback(networkCallback: ConnectivityManager.NetworkCallback) {
        UtilKConnectivityManager.unregisterNetworkCallback(_context, networkCallback)
    }

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
}