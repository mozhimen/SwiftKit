package com.mozhimen.componentktest.cameraxk

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapDeal
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFormat
import com.mozhimen.componentk.cameraxk.commons.ICameraXKCaptureListener
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.componentk.statusbark.annors.AStatusBarK
import com.mozhimen.componentk.statusbark.annors.AStatusBarKType
import com.mozhimen.componentktest.databinding.ActivityCameraxkBinding
import java.util.concurrent.locks.ReentrantLock

@AStatusBarK(statusBarType = AStatusBarKType.FULL_SCREEN)
class CameraXKActivity : BaseActivityVB<ActivityCameraxkBinding>() {

    /*private val outputDirectory: String by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            "${Environment.DIRECTORY_DCIM}/CameraXKActivity/"
        } else {
            "${this.getExternalFilesDir(Environment.DIRECTORY_DCIM)}/CameraXKActivity/"
        }
    }*/

    override fun initView(savedInstanceState: Bundle?) {
        initCamera()
    }

    private val _format = ACameraXKFormat.RGBA_8888

    private fun initCamera() {
        vb.camerakPreviewView.initCamera(this, ACameraXKFacing.FRONT, _format)
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
                        ACameraXKFormat.RGBA_8888 -> {
                            val bitmap: Bitmap = ImageConverter.rgb2Bitmap(image)
                            val rotateBitmap = UtilKBitmapDeal.rotateBitmap(bitmap, 90)
                            runOnUiThread {
                                vb.camerakImg1.setImageBitmap(rotateBitmap)
                            }
                        }
                        ACameraXKFormat.YUV_420_888 -> {
                            val bitmap: Bitmap = ImageConverter.yuv2Bitmap(image)!!
                            val rotateBitmap = UtilKBitmapDeal.rotateBitmap(bitmap, 90)
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
                vb.camerakImg.setImageBitmap(UtilKBitmapDeal.rotateBitmap(bitmap, 90, flipY = false))
            }
        }

        override fun onCaptureFail() {}
    }
}