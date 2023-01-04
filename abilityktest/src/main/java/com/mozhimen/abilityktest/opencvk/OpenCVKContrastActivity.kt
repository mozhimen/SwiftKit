package com.mozhimen.abilityktest.opencvk

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityk.opencvk.OpenCVKContrast
import com.mozhimen.abilityktest.R
import com.mozhimen.abilityktest.databinding.ActivityOpencvkContrastBinding
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.exts.cropBitmap
import com.mozhimen.basick.utilk.exts.drawable2Bitmap
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapDeal
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.mos.CameraXKConfig
import com.mozhimen.opencvk.OpenCVK
import java.util.concurrent.locks.ReentrantLock

@APermissionK(
    Manifest.permission.CAMERA
)
class OpenCVKContrastActivity : BaseActivityVB<ActivityOpencvkContrastBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKPermission.openSettingSelf()
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        _orgBitmap = UtilKRes.getDrawable(R.mipmap.opencvk_contrast_test)!!.drawable2Bitmap()
        require(OpenCVK.initSDK()) { "opencv init fail" }
        initCamera()
    }

    private fun initCamera() {
        vb.opencvkContrastPreview.initCamera(this, CameraXKConfig(facing = ACameraXKFacing.BACK))
        vb.opencvkContrastPreview.setCameraXKFrameListener(_frameAnalyzer)
        vb.opencvkContrastPreview.startCamera()
    }

    private lateinit var _orgBitmap: Bitmap

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
                        vb.opencvkContrastQrscan.getRectSize().toDouble() / UtilKScreen.getRealScreenWidth().toDouble()

                    val cropBitmap = rotateBitmap.cropBitmap(
                        (ratio * rotateBitmap.width).toInt(),
                        (ratio * rotateBitmap.width).toInt(),
                        ((1 - ratio) * rotateBitmap.width / 2).toInt(),
                        ((rotateBitmap.height - ratio * rotateBitmap.width) / 2).toInt()
                    )

                    val cropSameBitmap = UtilKBitmapDeal.scaleSameSize(cropBitmap, _orgBitmap)
                    runOnUiThread {
                        vb.opencvkContrastImg.setImageBitmap(rotateBitmap)
                        vb.opencvkContrastImg1.setImageBitmap(cropSameBitmap.first)
                        vb.opencvkContrastImg2.setImageBitmap(cropSameBitmap.second)
                    }
                    //detect
                    val result = OpenCVKContrast.similarity(cropSameBitmap.first, cropSameBitmap.second) * 100
                    runOnUiThread {
                        vb.opencvkContrastRes.text = result.toString()
                    }
                } finally {
                    _reentrantLock.unlock()
                }
                image.close()
            }
        }
    }
}