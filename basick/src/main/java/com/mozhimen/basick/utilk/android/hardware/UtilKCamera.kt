package com.mozhimen.basick.utilk.android.hardware

import android.content.Context
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import com.mozhimen.basick.elemk.android.hardware.cons.CCamera
import com.mozhimen.basick.utilk.android.content.UtilKPackage
import com.mozhimen.basick.utilk.android.os.UtilKBuildVersion

/**
 * @ClassName UtilKCamera
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/7 19:09
 * @Version 1.0
 */
object UtilKCamera {

    @JvmStatic
    fun getCameraInfo(cameraId: Int, cameraInfo: CameraInfo) {
        Camera.getCameraInfo(cameraId, cameraInfo)
    }

    @JvmStatic
    fun getNumberOfCameras(): Int =
        Camera.getNumberOfCameras()

    ///////////////////////////////////////////////////////////////////////

    /**
     * 设备是否有前置摄像
     */
    @JvmStatic
    fun hasFrontCamera(context: Context): Boolean =
        hasCamera(context, true)

    /**
     * 设备是否有后置摄像头
     */
    @JvmStatic
    fun hasBackCamera(context: Context): Boolean =
        hasCamera(context, false)

    /**
     * 是否含有相机
     * @param isFront Boolean
     * @return Boolean
     */
    @JvmStatic
    fun hasCamera(context: Context, isFront: Boolean): Boolean {
        if (UtilKBuildVersion.isAfterV_28_9_P()) {
            return if (isFront) UtilKPackage.hasFrontCamera(context) else UtilKPackage.hasBackCamera(context)
        } else {
            val cameraInfo = CameraInfo()
            for (cameraId in 0 until getNumberOfCameras()) {
                getCameraInfo(cameraId, cameraInfo)
                if (cameraInfo.facing == if (isFront) CCamera.CameraInfo.CAMERA_FACING_FRONT else CCamera.CameraInfo.CAMERA_FACING_BACK)
                    return true
            }
            return false
        }
    }
}