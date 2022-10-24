package com.mozhimen.abilityktest.opencvk

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityk.opencvk.OpenCVKShape
import com.mozhimen.abilityk.opencvk.OpenCVKTrans
import com.mozhimen.abilityktest.databinding.ActivityOpencvkShapeBinding
import com.mozhimen.basick.basek.BaseKActivityVB
import com.mozhimen.basick.basek.BaseKActivityVBVM
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.cropBitmap
import com.mozhimen.basick.utilk.UtilKBitmap
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.componentk.cameraxk.annors.CameraXKFacing
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor
import com.mozhimen.opencvk.OpenCVK
import java.util.concurrent.locks.ReentrantLock

@PermissionKAnnor(permissions = [Manifest.permission.CAMERA])
class OpenCVKShapeActivity : BaseKActivityVB<ActivityOpencvkShapeBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
            } else {
                PermissionK.applySetting(this)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        require(OpenCVK.initSDK()) { "opencv init fail" }
        initCamera()
    }

    private fun initCamera() {
        vb.opencvkShapePreview.initCamera(this, CameraXKFacing.BACK)
        vb.opencvkShapePreview.setImageAnalyzer(_frameAnalyzer)
        vb.opencvkShapePreview.startCamera()
    }

    private val _frameAnalyzer: ImageAnalysis.Analyzer by lazy {
        object : ImageAnalysis.Analyzer {
            private val _reentrantLock = ReentrantLock()

            @SuppressLint("UnsafeOptInUsageError")
            override fun analyze(image: ImageProxy) {
                try {
                    _reentrantLock.lock()
                    val bitmap: Bitmap = if (image.format == ImageFormat.YUV_420_888) {
                        ImageConverter.yuv2Bitmap(image)!!
                    } else {
                        ImageConverter.jpeg2Bitmap(image)
                    }
                    val rotateBitmap = UtilKBitmap.rotateBitmap(bitmap, 90)
                    val ratio: Double =
                        vb.opencvkShapeQrscan.getRectSize().toDouble() / UtilKScreen.getScreenWidth().toDouble()
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