package com.mozhimen.componentk.camerak.camerax.temps

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.CameraInfo
import androidx.camera.core.impl.CameraInfoInternal
import androidx.core.util.Preconditions
import com.mozhimen.basick.utilk.android.util.UtilKLog
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXFilter


/**
 * @ClassName OtherCameraSelectorFilter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/15 14:50
 * @Version 1.0
 */
class OtherCameraFilter(private val _id: String) : ICameraKXFilter {

    @SuppressLint("RestrictedApi")
    override fun filter(cameraInfos: MutableList<CameraInfo>): MutableList<CameraInfo> {
        "filter: _id $_id cameraInfos $cameraInfos".dt(TAG)
        val tempCameraInfos = ArrayList<CameraInfo>()
        cameraInfos.forEach {
            Preconditions.checkArgument(it is CameraInfoInternal, "The camera info doesn't contain internal implementation.")
            it as CameraInfoInternal
            val id = it.cameraId
            if (id.contains(_id) || id == _id) tempCameraInfos.add(it)
        }
        return tempCameraInfos.also { "filter: cameraInfos ${it.joinToString { list-> (list as CameraInfoInternal).cameraId }}".dt(TAG) }
    }
}
