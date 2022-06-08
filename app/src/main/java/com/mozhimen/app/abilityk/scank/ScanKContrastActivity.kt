package com.mozhimen.app.abilityk.scank

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityk.cameraxk.helpers.ImageConverter
import com.mozhimen.abilityk.scank.ScanKOpenCV
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityScankContrastBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.cropBitmap
import com.mozhimen.basick.extsk.drawable2Bitmap
import com.mozhimen.basick.utilk.UtilKBitmap
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor
import java.util.concurrent.locks.ReentrantLock

@PermissionKAnnor(permissions = [Manifest.permission.CAMERA])
class ScanKContrastActivity : BaseKActivity<ActivityScankContrastBinding, BaseKViewModel>(R.layout.activity_scank_contrast) {
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
        _orgBitmap = UtilKRes.getDrawable(R.mipmap.scank_contrast_test)!!.drawable2Bitmap()
        initCamera()
    }

    private fun initCamera() {
        vb.scankContrastPreview.initCamera(this, CameraSelector.DEFAULT_BACK_CAMERA)
        vb.scankContrastPreview.setImageAnalyzer(_frameAnalyzer)
        vb.scankContrastPreview.startCamera()
    }

    private lateinit var _orgBitmap: Bitmap

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
                        vb.scankContrastQrscan.getRectSize().toDouble() / UtilKScreen.getScreenWidth().toDouble()
                    val cropBitmap = rotateBitmap.cropBitmap(
                        (ratio * rotateBitmap.width).toInt(),
                        (ratio * rotateBitmap.width).toInt(),
                        ((1 - ratio) * rotateBitmap.width / 2).toInt(),
                        ((rotateBitmap.height - ratio * rotateBitmap.width) / 2).toInt()
                    )
                    val cropSameBitmap = UtilKBitmap.scaleSameSize(cropBitmap, _orgBitmap)
                    runOnUiThread {
                        vb.scankContrastImg.setImageBitmap(rotateBitmap)
                        vb.scankContrastImg1.setImageBitmap(cropSameBitmap.first)
                        vb.scankContrastImg2.setImageBitmap(cropSameBitmap.second)
                    }
                    //detect
                    val result = ScanKOpenCV.similarity(cropSameBitmap.first, cropSameBitmap.second) * 100
                    runOnUiThread {
                        vb.scankContrastRes.text = result.toString()
                    }
                } finally {
                    _reentrantLock.unlock()
                }

                image.close()
            }
        }
    }
}