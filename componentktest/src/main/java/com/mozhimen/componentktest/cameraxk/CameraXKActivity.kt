package com.mozhimen.componentktest.cameraxk

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import androidx.camera.core.ImageProxy
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFormat
import com.mozhimen.componentk.cameraxk.commons.ICameraXKCaptureListener
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.componentk.cameraxk.mos.CameraXKConfig
import com.mozhimen.componentk.statusbark.annors.AStatusBarK
import com.mozhimen.componentk.statusbark.annors.AStatusBarKType
import com.mozhimen.componentktest.databinding.ActivityCameraxkBinding

@APermissionK(Manifest.permission.CAMERA)
@AStatusBarK(statusBarType = AStatusBarKType.FULL_SCREEN)
class CameraXKActivity : BaseActivityVB<ActivityCameraxkBinding>() {

    /*private val outputDirectory: String by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            "${Environment.DIRECTORY_DCIM}/CameraXKActivity/"
        } else {
            "${this.getExternalFilesDir(Environment.DIRECTORY_DCIM)}/CameraXKActivity/"
        }
    }*/

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this, onSuccess = { super.initData(savedInstanceState) })
    }

    override fun initView(savedInstanceState: Bundle?) {
        initCamera()
    }

    private val _format = ACameraXKFormat.RGBA_8888

    private fun initCamera() {
        vb.camerakPreviewLayout.initCamera(this, CameraXKConfig(ACameraXKFacing.FRONT, _format))
        vb.camerakPreviewLayout.setCameraXKFrameListener(_frameAnalyzer)
        vb.camerakPreviewLayout.setCameraXKCaptureListener(_cameraXKCaptureListener)
        vb.camerakPreviewLayout.startCamera()
        vb.camerakBtn.setOnClickListener {
            vb.camerakPreviewLayout.takePicture()
        }
    }

    private var _outputBitmap: Bitmap? = null
    private val _frameAnalyzer: ICameraXKFrameListener by lazy {
        object : ICameraXKFrameListener {
            override fun onFrame(image: ImageProxy) {
                when (_format) {
                    ACameraXKFormat.RGBA_8888 -> {
                        _outputBitmap = ImageConverter.rgba8888Image2Rgba8888Bitmap(image)
                    }
                    ACameraXKFormat.YUV_420_888 -> {
                        _outputBitmap = ImageConverter.yuv420888Image2JpegBitmap(image)
                    }
                }
                _outputBitmap?.let {
                    runOnUiThread {
                        vb.camerakImg1.setImageBitmap(_outputBitmap)
                    }
                }
                image.close()
            }
        }
    }

    private val _cameraXKCaptureListener = object : ICameraXKCaptureListener {
        override fun onCaptureSuccess(bitmap: Bitmap, imageRotation: Int) {
            runOnUiThread {
                vb.camerakImg.setImageBitmap(bitmap)
            }
        }

        override fun onCaptureFail() {}
    }
}