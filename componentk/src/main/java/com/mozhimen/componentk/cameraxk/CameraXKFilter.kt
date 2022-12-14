package com.mozhimen.componentk.cameraxk

import androidx.camera.core.CameraFilter
import androidx.camera.core.CameraInfo
import androidx.camera.core.impl.CameraInfoInternal
import androidx.core.util.Preconditions


/**
 * @ClassName CameraXKFilter
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2022/12/14 20:19
 * @Version 1.0
 */
class CameraXKFilter(private val mId: String) : CameraFilter {

    private val TAG = "CameraXKFilter>>>>>"

    override fun filter(cameraInfos: MutableList<CameraInfo>): MutableList<CameraInfo> {
        val result = ArrayList()
        cameraInfos.forEach {
            Preconditions.checkArgument(it is CameraInfoInternal, "The camera info doesn't contain internal implementation.")
            it as CameraInfoInternal
            val id = it.cameraId//测试发现有的ID为/dev/video0，有的为0；故mId传0，1即可
            if (id.contains(mId)) {
                result.add(it)
            }
        }
        return result
    }

}
