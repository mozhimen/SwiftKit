package com.mozhimen.basick.utilk.android.location


import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.location.commons.LocationCallback
import com.mozhimen.basick.elemk.android.location.cons.CLocationManager
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.bases.BaseUtilK

/**
 * @ClassName UtilKLocation
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/15 16:04
 * @Version 1.0
 */
object UtilKLocation : BaseUtilK() {

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION])
    fun get(): Pair<Double, Double> {
        if (UtilKLocationManager.isGpsProviderEnabled(_context)) {
            val location = UtilKLocationManager.getGpsLastKnownLocation(_context)
            if (location != null)
                return location.longitude to location.latitude
        } else {
            val locationManager = UtilKLocationManager.get(_context)
            locationManager.requestLocationUpdates(CLocationManager.NETWORK_PROVIDER, 1000, 0f, object : LocationCallback() {})
            val location = UtilKLocationManager.getNetworkLastKnownLocation(_context)
            if (location != null)
                return location.longitude to location.latitude
        }
        return 0.0 to 0.0
    }
}
