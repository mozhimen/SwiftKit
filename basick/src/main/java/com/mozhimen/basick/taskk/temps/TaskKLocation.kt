package com.mozhimen.basick.taskk.temps

import android.util.Log
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.commons.ISuspendAA_Listener
import com.mozhimen.basick.elemk.commons.ISuspendAB_Listener
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.utilk.android.app.UtilKPermission
import com.mozhimen.basick.utilk.android.location.UtilKLocation
import com.mozhimen.basick.utilk.android.provider.UtilKSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * @ClassName TaskKLocation
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/15 16:29
 * @Version 1.0
 */
@OptInApiInit_ByLazy
@OptInApiCall_BindLifecycle
class TaskKLocation : TaskKPollInfinite() {

    @Volatile
    private var _longitude: Double = 0.0

    @Volatile
    private var _latitude: Double = 0.0

    val longitude get() = _longitude

    val latitude get() = _latitude

    /**
     * Start location task
     *
     * @param intervalMillis
     * @param minTimeMs 时间刷新 毫秒
     * @param minDistanceM 距离 米
     * @param task
     */
    @RequiresPermission(allOf = [CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION])
    fun startLocationTask(intervalMillis: Long, minTimeMs: Long = 2000, minDistanceM: Float = 5f, task: ISuspendAA_Listener<Double>? = null) {
        if (!UtilKPermission.checkPermissions(arrayOf(CPermission.ACCESS_FINE_LOCATION, CPermission.ACCESS_COARSE_LOCATION))) {
            Log.w(TAG, "startLocationTask: dont have location permission")
            return
        }
        if (!UtilKSettings.isLocationEnabled(_context)){
            Log.w(TAG, "startLocationTask:  dont open system location setting")
            return
        }
        start(intervalMillis, Dispatchers.Main) {
            val location = UtilKLocation.getLonAndLat(minTimeMs, minDistanceM)
            _longitude = location.first.takeIf { it != 0.0 } ?: _longitude
            _latitude = location.second.takeIf { it != 0.0 } ?: _latitude
            Log.d(TAG, "startLocationTask: _longitude $_longitude _latitude $_latitude")
            task?.invoke(_longitude, _latitude)
        }
    }
}