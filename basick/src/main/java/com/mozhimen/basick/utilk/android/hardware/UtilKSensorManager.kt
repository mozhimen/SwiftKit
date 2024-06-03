package com.mozhimen.basick.utilk.android.hardware

import android.content.Context
import android.hardware.SensorManager
import com.mozhimen.basick.utilk.android.content.UtilKContext

/**
 * @ClassName UtilKSensorManager
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/28
 * @Version 1.0
 */
object UtilKSensorManager {
    @JvmStatic
    fun get(context: Context): SensorManager =
        UtilKContext.getSensorManager(context)
}