package com.mozhimen.componentk.cameraxk.temps

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.camera2.internal.Camera2CameraInfoImpl
import androidx.camera.core.CameraFilter
import androidx.camera.core.CameraInfo
import androidx.camera.core.impl.CameraInfoInternal
import androidx.core.util.Preconditions
import com.mozhimen.basick.utilk.android.util.UtilKLog
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.componentk.cameraxk.commons.ICameraXKAnalyzer
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFilter


/**
 * @ClassName OtherCameraSelectorFilter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/15 14:50
 * @Version 1.0
 */
class OtherCameraFilter(private val _id: String) : ICameraXKFilter {

    @SuppressLint("RestrictedApi")
    override fun filter(cameraInfos: MutableList<CameraInfo>): MutableList<CameraInfo> {
        UtilKLog.dt(TAG, "filter: _id $_id cameraInfos $cameraInfos")
        val tempCameraInfos = ArrayList<CameraInfo>()
        cameraInfos.forEach {
            Preconditions.checkArgument(it is CameraInfoInternal, "The camera info doesn't contain internal implementation.")
            it as CameraInfoInternal
            val id = it.cameraId
            Log.v(TAG, "filter: id $id")
            if (id.contains(_id) || id == _id) tempCameraInfos.add(it)
        }
        UtilKLog.dt(TAG, "filter: cameraInfos ${tempCameraInfos.joinToString { (it as CameraInfoInternal).cameraId }}")
        return tempCameraInfos
    }
}
