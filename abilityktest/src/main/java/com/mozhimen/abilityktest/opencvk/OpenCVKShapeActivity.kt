package com.mozhimen.abilityktest.opencvk

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityk.opencvk.OpenCVKShape
import com.mozhimen.abilityk.opencvk.OpenCVKTrans
import com.mozhimen.abilityktest.databinding.ActivityOpencvkShapeBinding
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionRequire
import com.mozhimen.basick.permissionk.cons.CUseFeature
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapDeal
import com.mozhimen.basick.utilk.exts.cropBitmap
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.componentk.cameraxk.mos.CameraXKConfig
import com.mozhimen.opencvk.OpenCVK
import java.util.concurrent.locks.ReentrantLock

@APermissionRequire(CPermission.CAMERA, CUseFeature.CAMERA, CUseFeature.CAMERA_AUTOFOCUS)
class OpenCVKShapeActivity : BaseActivityVB<ActivityOpencvkShapeBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKPermission.openSettingSelf(this)
            }
        }
    }

    @Throws(Exception::class)
    override fun initView(savedInstanceState: Bundle?) {
        require(OpenCVK.initSDK()) { "$TAG opencv init fail" }
        initCamera()
    }

    private fun initCamera() {
        vb.opencvkShapePreview.initCamera(this, CameraXKConfig(facing = ACameraXKFacing.BACK))
        vb.opencvkShapePreview.setCameraXKFrameListener(_frameAnalyzer)
        vb.opencvkShapePreview.startCamera()
    }

    private val _frameAnalyzer: ICameraXKFrameListener by lazy {
        object : ICameraXKFrameListener {
            private val _reentrantLock = ReentrantLock()

            @SuppressLint("UnsafeOptInUsageError")
            override fun onFrame(image: ImageProxy) {
                try {
                    _reentrantLock.lock()
                    val bitmap: Bitmap = if (image.format == ImageFormat.YUV_420_888) {
                        ImageConverter.yuv420888Image2JpegBitmap(image)!!
                    } else {
                        ImageConverter.jpegImage2JpegBitmap(image)
                    }
                    val rotateBitmap = UtilKBitmapDeal.rotateBitmap(bitmap, 90)
                    val ratio: Double =
                        vb.opencvkShapeQrscan.getRectSize().toDouble() / UtilKScreen.getRealScreenWidth().toDouble()
                    val cropBitmap = rotateBitmap.cropBitmap(
                        (ratio * rotateBitmap.width).toInt(),
                        (ratio * rotateBitmap.width).toInt(),
                        ((1 - ratio) * rotateBitmap.width / 2).toInt(),
                        ((rotateBitmap.height - ratio * rotateBitmap.width) / 2).toInt()
                    )
                    //detect
                    val result = OpenCVKShape.getCircleNum(OpenCVKTrans.bitmap2Mat(cropBitmap))
                    runOnUiThread {
                        vb.opencvkShapeRes.text = result.toString()
                    }
                } finally {
                    _reentrantLock.unlock()
                }

                image.close()
            }
        }
    }
}