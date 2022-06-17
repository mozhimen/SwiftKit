package com.mozhimen.app.abilityk.opencvk

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityk.cameraxk.helpers.ImageConverter
import com.mozhimen.abilityk.opencvk.OpenCVKMatch
import com.mozhimen.abilityk.opencvk.OpenCVKTrans
import com.mozhimen.abilityk.opencvk.setMat
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityOpencvkMatchBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.cropBitmap
import com.mozhimen.basick.extsk.drawable2Bitmap
import com.mozhimen.basick.utilk.UtilKBitmap
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor
import com.mozhimen.opencvk.OpenCVK
import java.util.concurrent.locks.ReentrantLock

@PermissionKAnnor(permissions = [Manifest.permission.CAMERA])
class OpenCVKMatchActivity : BaseKActivity<ActivityOpencvkMatchBinding, BaseKViewModel>(R.layout.activity_opencvk_match) {
    private val _templateMat by lazy { OpenCVKTrans.bitmap2Mat(UtilKRes.getDrawable(R.mipmap.opencvk_contrast_test)!!.drawable2Bitmap()) }

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
        vb.opencvkMatchPreview.initCamera(this, CameraSelector.DEFAULT_BACK_CAMERA)
        vb.opencvkMatchPreview.setImageAnalyzer(_frameAnalyzer)
        vb.opencvkMatchPreview.startCamera()
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
                        vb.opencvkMatchQrscan.getRectSize().toDouble() / UtilKScreen.getScreenWidth().toDouble()

                    val cropBitmap = rotateBitmap.cropBitmap(
                        (ratio * rotateBitmap.width).toInt(),
                        (ratio * rotateBitmap.width).toInt(),
                        ((1 - ratio) * rotateBitmap.width / 2).toInt(),
                        ((rotateBitmap.height - ratio * rotateBitmap.width) / 2).toInt()
                    )

                    val srcMat = OpenCVKTrans.bitmap2Mat(cropBitmap)

                    val resultMat = OpenCVKMatch.templateMatch(srcMat, _templateMat)
                    Thread.sleep(100)
                    try {
                        vb.opencvkMatchImg.setMat(resultMat)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        srcMat.release()
                        resultMat.release()
                    }
                } finally {
                    _reentrantLock.unlock()
                }

                image.close()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _templateMat.release()
    }
}