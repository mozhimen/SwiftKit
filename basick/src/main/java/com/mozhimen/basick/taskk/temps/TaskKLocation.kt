package com.mozhimen.basick.taskk.temps

import android.util.Log
import androidx.annotation.RequiresPermission
import com.mozhimen.basick.lintk.optin.OptInApiCall_BindLifecycle
import com.mozhimen.basick.lintk.optin.OptInApiInit_ByLazy
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.location.UtilKLocation


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

    @RequiresPermission(allOf = [CPermission.ACCESS_COARSE_LOCATION, CPermission.ACCESS_FINE_LOCATION])
    fun startLocationTask(intervalMillis: Long) {
        start(intervalMillis) {
            val location = UtilKLocation.get()
            _longitude = location.first.takeIf { it != 0.0 } ?: _longitude
            _latitude = location.second.takeIf { it != 0.0 } ?: _latitude
            Log.d(TAG, "startLocationTask: _longitude $_longitude _latitude $_latitude")
        }
    }
}