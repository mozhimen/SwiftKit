package com.mozhimen.basick.utilk.device

import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.elemk.cons.CVersionCode
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.utilk.content.UtilKApplication

/**
 * @ClassName UtilKCamera
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/7 19:09
 * @Version 1.0
 */
@AManifestKRequire(CPermission.CAMERA)
object UtilKCamera {
    private val _context = UtilKApplication.instance.get()
    /**
     * 设备是否有前置摄像
     * @return Boolean
     */
    @JvmStatic
    fun isHasFrontCamera(): Boolean =
        isHasCamera(true)

    /**
     * 设备是否有后置摄像头
     * @return Boolean
     */
    @JvmStatic
    fun isHasBackCamera(): Boolean =
        isHasCamera(false)

    /**
     * 是否含有相机
     * @param isFront Boolean
     * @return Boolean
     */
    @JvmStatic
    private fun isHasCamera(isFront: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= CVersionCode.V_28_9_P) {
            return _context.packageManager.hasSystemFeature(if (isFront) PackageManager.FEATURE_CAMERA_FRONT else PackageManager.FEATURE_CAMERA)
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