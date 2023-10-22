package com.mozhimen.basick.utilk.android.location


import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.android.location.commons.ILocationListener
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.provider.UtilKSettings
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.android.util.it
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
    @SuppressLint("MissingPermission")
    fun get(minTimeMs: Long, minDistanceM: Float): Location? {
        if (!hasPermission()) return null
        return getForGps() ?: getForNetwork(minTimeMs, minDistanceM) ?: getLastLocation()
    }

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_FINE_LOCATION, CPermission.ACCESS_COARSE_LOCATION])
    fun getForGps(): Location? =
        (if (UtilKLocationManager.isProviderEnabledGps(_context))
            UtilKLocationManager.getLastKnownLocationGps(_context)
        else null).also { "getForGps is null ${it == null}".it(TAG) }

    /**
     * Get for network
     *
     * @param minTimeMs 2000
     * @param minDistanceM 5f
     * @param listener
     * @return
     */
    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_FINE_LOCATION, CPermission.ACCESS_COARSE_LOCATION])
    fun getForNetwork(minTimeMs: Long, minDistanceM: Float, listener: LocationListener = object : ILocationListener {}): Location? {
        if (!UtilKLocationManager.isProviderEnabledNetwork(_context)) return null
        UtilKLocationManager.requestLocationUpdatesNetwork(_context, minTimeMs, minDistanceM, listener)
        return UtilKLocationManager.getLastKnownLocationNetwork(_context).also { "getForNetwork is null ${it == null}".it(TAG) }
    }

    @JvmStatic
    @RequiresPermission(allOf = [CPermission.ACCESS_FINE_LOCATION, CPermission.ACCESS_COARSE_LOCATION])
    fun getLastLocation(): Location? {
        var lastLocation: Location? = null
        val providers = UtilKLocationManager.getProviders(_context, true)
        for (provider in providers) {
            val location = UtilKLocationManager.getLastKnownLocation(_context, provider) ?: continue
            if (lastLocation == null)
                lastLocation = location
            else if (lastLocation.accuracy < location.accuracy)
                lastLocation = location// Found best last known location: %s", l);
        }
        return lastLocation.also { "getLastLocation is null ${it == null}".it(TAG) }
    }

    @JvmStatic
    fun get_Longitude_Latitude(minTimeMs: Long = 2000, minDistanceM: Float = 5f): Pair<Double, Double>? =
        get(minTimeMs, minDistanceM)?.let { it.longitude to it.latitude }

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @JvmStatic
    fun hasPermission(): Boolean =
        if (!UtilKPermission.checkPermissions(arrayOf(CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION))) {
            false.also { "get: permission denied".et(TAG) }
        } else if (!UtilKSettings.isLocationEnabled(_context)) {
            false.also { "get: system setting location off".et(TAG) }
        } else true
}