package com.mozhimen.basick.taskk.temps

import android.util.Log
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.elemk.commons.ISuspendAA_Listener
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.location.UtilKLocation
import kotlinx.coroutines.Dispatchers


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
    @AManifestKRequire(CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION)
    fun startLocationTask(intervalMillis: Long, minTimeMs: Long = 2000, minDistanceM: Float = 5f, task: ISuspendAA_Listener<Double>? = null) {
        if (!UtilKLocation.hasPermission()) {
            Log.w(TAG, "startLocationTask: dont hasPermission")
            return
        }
        start(intervalMillis, Dispatchers.Main) {
            val location = UtilKLocation.get_Longitude_Latitude(minTimeMs, minDistanceM)
            Log.d(TAG, "startLocationTask: location ${location?.first} ${location?.second}")
            location?.let {
                _longitude = location.first
                _latitude = location.second
            }
            task?.invoke(_longitude, _latitude)
        }
    }
}