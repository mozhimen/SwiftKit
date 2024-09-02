package com.mozhimen.taskk.temps

import com.mozhimen.kotlin.utilk.android.util.UtilKLogWrapper
import androidx.annotation.RequiresPermission
import com.mozhimen.kotlin.elemk.commons.ISuspendAA_Listener
import com.mozhimen.kotlin.lintk.optins.OApiCall_BindLifecycle
import com.mozhimen.kotlin.lintk.optins.OApiInit_ByLazy
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_ACCESS_COARSE_LOCATION
import com.mozhimen.kotlin.lintk.optins.permission.OPermission_ACCESS_FINE_LOCATION
import com.mozhimen.kotlin.elemk.android.cons.CPermission
import com.mozhimen.kotlin.utilk.wrapper.UtilKPermission
import com.mozhimen.kotlin.utilk.wrapper.UtilKLocation
import kotlinx.coroutines.Dispatchers


/**
 * @ClassName TaskKLocation
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/8/15 16:29
 * @Version 1.0
 */
@OApiInit_ByLazy
@OApiCall_BindLifecycle
class TaskKLocation : TaskKPollInfinite() {

    private var _longitude: Double = 0.0
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
    @OPermission_ACCESS_FINE_LOCATION
    @OPermission_ACCESS_COARSE_LOCATION
    fun startLocationTask(intervalMillis: Long, minTimeMs: Long = 2000, minDistanceM: Float = 5f, task: ISuspendAA_Listener<Double>? = null) {
        if (!UtilKPermission.hasAccessLocation()) {
            UtilKLogWrapper.w(TAG, "startLocationTask: dont hasPermission")
            return
        }
        start(intervalMillis, Dispatchers.Main) {
            val location = UtilKLocation.get_Longitude_Latitude(minTimeMs, minDistanceM)
            UtilKLogWrapper.d(TAG, "startLocationTask: location ${location?.first} ${location?.second}")
            location?.let {
                _longitude = location.first
                _latitude = location.second
            }
            task?.invoke(_longitude, _latitude)
        }
    }
}