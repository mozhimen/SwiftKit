package com.mozhimen.componentktest.cameraxk

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.camera.core.ImageProxy
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CUseFeature
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFormat
import com.mozhimen.componentk.cameraxk.commons.ICameraXKCaptureListener
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.componentk.cameraxk.mos.CameraXKConfig
import com.mozhimen.componentktest.databinding.ActivityCameraxkBinding

@AManifestKRequire(CPermission.CAMERA, CUseFeature.CAMERA, CUseFeature.CAMERA_AUTOFOCUS)
@APermissionCheck(CPermission.CAMERA)
@com.mozhimen.basick.statusbark.annors.AStatusBarK(statusBarType = com.mozhimen.basick.statusbark.annors.AStatusBarKType.FULL_SCREEN)
class CameraXKActivity : BaseActivityVB<ActivityCameraxkBinding>() {

    /*private val outputDirectory: String by lazy {
        if (Build.VERSION.SDK_INT >= VersionCode.Q) {
            "${Environment.DIRECTORY_DCIM}/CameraXKActivity/"
        } else {
            "${this.getExternalFilesDir(Environment.DIRECTORY_DCIM)}/CameraXKActivity/"
        }
    }*/

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.initPermissions(this, onSuccess = { super.initData(savedInstanceState) })
    }

    override fun initView(savedInstanceState: Bundle?) {
        initCamera()
    }

    private val _format = ACameraXKFormat.RGBA_8888

    private fun initCamera() {
        VB.cameraxkPreviewLayout.initCamera(this, CameraXKConfig(_format, ACameraXKFacing.FRONT))
        VB.cameraxkPreviewLayout.setCameraXKFrameListener(_frameAnalyzer)
        VB.cameraxkPreviewLayout.setCameraXKCaptureListener(_cameraXKCaptureListener)
        VB.cameraxkPreviewLayout.startCamera()
        VB.cameraxkBtn.setOnClickListener {
            VB.cameraxkPreviewLayout.takePicture()
        }
    }

    private var _outputBitmap: Bitmap? = null
    private val _frameAnalyzer: ICameraXKFrameListener by lazy {
        object : ICameraXKFrameListener {
            @SuppressLint("UnsafeOptInUsageError")
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
                        VB.cameraxkImg1.setImageBitmap(_outputBitmap)
                    }
                }
                image.close()
            }
        }
    }

    private val _cameraXKCaptureListener = object : ICameraXKCaptureListener {
        override fun onCaptureSuccess(bitmap: Bitmap, imageRotation: Int) {
            runOnUiThread {
                VB.cameraxkImg.setImageBitmap(bitmap)
            }
        }

        override fun onCaptureFail() {}
    }
}