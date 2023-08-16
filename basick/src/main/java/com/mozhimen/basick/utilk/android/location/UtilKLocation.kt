package com.mozhimen.basick.utilk.android.location


import android.location.Location
import android.location.LocationListener
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.location.commons.LocationCallback
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.provider.UtilKSettings
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
    fun get(minTimeMs: Long = 2000, minDistanceM: Float = 5f): Location? {
        if (!UtilKPermission.checkPermission(CPermission.ACCESS_FINE_LOCATION) || !UtilKPermission.checkPermission(CPermission.ACCESS_COARSE_LOCATION)) {
            Log.w(TAG, "get: permission denied")
            return null
        }

        if (!UtilKSettings.isLocationEnabled(_context)) {
            Log.w(TAG, "get: system setting location off")
            return null
        }

        return getForGps() ?: getForNetwork(minTimeMs, minDistanceM) ?: getLastLocation()
    }

    @JvmStatic
    fun getLonAndLat(minTimeMs: Long = 2000, minDistanceM: Float = 5f): Pair<Double, Double> =
        get(minTimeMs, minDistanceM)?.let { it.longitude to it.latitude } ?: (0.0 to 0.0)

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_FINE_LOCATION, CPermission.ACCESS_COARSE_LOCATION])
    fun getForGps(): Location? =
        if (UtilKLocationManager.isProviderEnabledGps(_context))
            UtilKLocationManager.getLastKnownLocationGps(_context)
        else null

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_FINE_LOCATION, CPermission.ACCESS_COARSE_LOCATION])
    fun getForNetwork(minTimeMs: Long = 2000, minDistanceM: Float = 5f, listener: LocationListener = object : LocationCallback() {}): Location? {
        UtilKLocationManager.requestLocationUpdatesNetwork(_context, minTimeMs, minDistanceM, listener)
        return UtilKLocationManager.getLastKnownLocationNetwork(_context)
    }

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_FINE_LOCATION, CPermission.ACCESS_COARSE_LOCATION])
    fun getLastLocation(): Location? {
        var lastLocation: Location? = null
        val providers = UtilKLocationManager.getProviders(_context, true)
        for (provider in providers) {
            val location = UtilKLocationManager.getLastKnownLocation(_context, provider) ?: continue
            if (location.accuracy < location.accuracy)
                lastLocation = location// Found best last known location: %s", l);
        }
        return lastLocation
    }
}
