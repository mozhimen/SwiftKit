package com.mozhimen.app.abilityk.cameraxk

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityk.cameraxk.annors.CameraXKFacing
import com.mozhimen.abilityk.cameraxk.annors.CameraXKFormat
import com.mozhimen.abilityk.cameraxk.commons.ICameraXKCaptureListener
import com.mozhimen.abilityk.cameraxk.helpers.ImageConverter
import com.mozhimen.app.R
import com.mozhimen.app.databinding.ActivityCameraxkBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.utilk.UtilKBitmap
import com.mozhimen.componentk.statusbark.annors.StatusBarKAnnor
import com.mozhimen.componentk.statusbark.annors.StatusBarKType
import java.util.concurrent.locks.ReentrantLock

@StatusBarKAnnor(statusBarType = StatusBarKType.FULL_SCREEN)
class CameraXKActivity : BaseKActivity<ActivityCameraxkBinding, BaseKViewModel>(R.layout.activity_cameraxk) {

    /*private val outputDirectory: String by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            "${Environment.DIRECTORY_DCIM}/CameraXKActivity/"
        } else {
            "${this.getExternalFilesDir(Environment.DIRECTORY_DCIM)}/CameraXKActivity/"
        }
    }*/

    override fun initData(savedInstanceState: Bundle?) {
        initCamera()
    }

    private val _format = CameraXKFormat.RGBA_8888

    private fun initCamera() {
        vb.camerakPreviewView.initCamera(this, CameraXKFacing.FRONT, _format)
        vb.camerakPreviewView.setImageAnalyzer(_frameAnalyzer)
        vb.camerakPreviewView.setCameraXKCaptureListener(_cameraXKCaptureListener)
        vb.camerakPreviewView.startCamera()
        vb.camerakBtn.setOnClickListener {
            vb.camerakPreviewView.takePicture()
        }
    }

    private val _frameAnalyzer: ImageAnalysis.Analyzer by lazy {
        object : ImageAnalysis.Analyzer {
            private val _reentrantLock = ReentrantLock()

            @SuppressLint("UnsafeOptInUsageError")
            override fun analyze(image: ImageProxy) {
                try {
                    _reentrantLock.lock()

                    when (_format) {
                        CameraXKFormat.RGBA_8888 -> {
                            val bitmap: Bitmap = ImageConverter.rgb2Bitmap(image)
                            val rotateBitmap = UtilKBitmap.rotateBitmap(bitmap, 90)
                            runOnUiThread {
                                vb.camerakImg1.setImageBitmap(rotateBitmap)
                            }
                        }
                        CameraXKFormat.YUV_420_888 -> {
                            val bitmap: Bitmap = ImageConverter.yuv2Bitmap(image)!!
                            val rotateBitmap = UtilKBitmap.rotateBitmap(bitmap, 90)
                            runOnUiThread {
                                vb.camerakImg1.setImageBitmap(rotateBitmap)
                            }
                        }
                    }
                } finally {
                    _reentrantLock.unlock()
                }

                image.close()
            }
        }
    }

    private val _cameraXKCaptureListener = object : ICameraXKCaptureListener {
        override fun onCaptureSuccess(bitmap: Bitmap, imageRotation: Int) {
            //UtilKImage.saveBitmap(outputDirectory, bitmap)
            runOnUiThread {
                vb.camerakImg.setImageBitmap(UtilKBitmap.rotateBitmap(bitmap, 90, flipY = false))
            }
        }

        override fun onCaptureFail() {}
    }
}