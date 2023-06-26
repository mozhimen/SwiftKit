package com.mozhimen.basick.utilk.android.hardware

import android.content.Context
import android.hardware.Camera
import android.os.Build
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.utilk.android.content.UtilKPackageManager

/**
 * @ClassName UtilKCamera
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/7 19:09
 * @Version 1.0
 */
object UtilKCamera {
    /**
     * 设备是否有前置摄像
     * @return Boolean
     */
    @JvmStatic
    fun isHasFrontCamera(context: Context): Boolean =
        isHasCamera(context, true)

    /**
     * 设备是否有后置摄像头
     * @return Boolean
     */
    @JvmStatic
    fun isHasBackCamera(context: Context): Boolean =
        isHasCamera(context, false)

    /**
     * 是否含有相机
     * @param isFront Boolean
     * @return Boolean
     */
    @JvmStatic
    fun isHasCamera(context: Context, isFront: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_28_9_P) {
            return if (isFront) UtilKPackageManager.hasFrontCamera(context) else UtilKPackageManager.hasBackCamera(context)
        } else {
            val cameraInfo = Camera.CameraInfo()
            for (cameraIndex in 0 until Camera.getNumberOfCameras()) {
                Camera.getCameraInfo(cameraIndex, cameraInfo)
                if (cameraInfo.facing == if (isFront) Camera.CameraInfo.CAMERA_FACING_FRONT else Camera.CameraInfo.CAMERA_FACING_BACK) return true
            }
            return false
        }
    }
}