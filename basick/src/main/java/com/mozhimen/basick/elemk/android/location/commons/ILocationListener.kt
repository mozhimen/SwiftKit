package com.mozhimen.basick.elemk.android.location.commons

import android.location.Location
import android.location.LocationListener
import android.os.Bundle


/**
 * @ClassName LocationCallback
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Version 1.0
 */
interface ILocationListener : LocationListener {
    // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
    override fun onLocationChanged(location: Location) {}

    // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
    @Deprecated("Deprecated in Java")
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    // Provider被enable时触发此函数，比如GPS被打开
    override fun onProviderEnabled(provider: String) {}

    // Provider被disable时触发此函数，比如GPS被关闭
    override fun onProviderDisabled(provider: String) {}
}