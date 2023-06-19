package com.mozhimen.componentk.cameraxk.helpers

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.camera2.internal.Camera2CameraInfoImpl
import androidx.camera.core.CameraFilter
import androidx.camera.core.CameraInfo
import androidx.camera.core.impl.CameraInfoInternal
import androidx.core.util.Preconditions


/**
 * @ClassName OtherCameraSelectorFilter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/15 14:50
 * @Version 1.0
 */
class OtherCameraFilter(private val _id: String) : CameraFilter {
    private val TAG = "OtherCameraFilter>>>>>"

    @SuppressLint("RestrictedApi")
    override fun filter(cameraInfos: MutableList<CameraInfo>): MutableList<CameraInfo> {
        Log.d(TAG, "filter: _id $_id cameraInfos $cameraInfos")
        val tempCameraInfos = ArrayList<CameraInfo>()
        cameraInfos.forEach {
            Preconditions.checkArgument(it is CameraInfoInternal, "The camera info doesn't contain internal implementation.")
            it as CameraInfoInternal
            val id = it.cameraId
            Log.v(TAG, "filter: id $id")
            if (id.contains(_id) || id == _id) {
                tempCameraInfos.add(it)
            }
        }
        Log.d(TAG, "filter: cameraInfos ${tempCameraInfos.joinToString { (it as CameraInfoInternal).cameraId }}")
        return tempCameraInfos
    }
}
