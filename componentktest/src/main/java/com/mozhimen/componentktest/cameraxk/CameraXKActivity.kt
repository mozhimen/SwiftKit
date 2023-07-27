package com.mozhimen.componentktest.cameraxk

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import androidx.camera.core.ImageProxy
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CUseFeature
import com.mozhimen.uicorek.adaptk.systembar.annors.AAdaptKSystemBarProperty
import com.mozhimen.uicorek.adaptk.systembar.cons.CProperty
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFacing
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFormat
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXCaptureListener
import com.mozhimen.componentk.camerak.camerax.commons.ICameraXKFrameListener
import com.mozhimen.componentk.camerak.camerax.helpers.ImageProxyUtil
import com.mozhimen.componentk.camerak.camerax.mos.MCameraKXConfig
import com.mozhimen.componentktest.databinding.ActivityCameraxkBinding

@AManifestKRequire(CPermission.CAMERA, CUseFeature.CAMERA, CUseFeature.CAMERA_AUTOFOCUS)
@APermissionCheck(CPermission.CAMERA)
@AAdaptKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
class CameraXKActivity : BaseActivityVB<ActivityCameraxkBinding>() {

    /*private val outputDirectory: String by lazy {
        if (Build.VERSION.SDK_INT >= VersionCode.Q) {
            "${Environment.DIRECTORY_DCIM}/CameraXKActivity/"
        } else {
            "${this.getExternalFilesDir(Environment.DIRECTORY_DCIM)}/CameraXKActivity/"
        }
    }*/

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.requestPermissions(this, onSuccess = { super.initData(savedInstanceState) })
    }

    override fun initView(savedInstanceState: Bundle?) {
        initCamera()
    }

    private val _format = ACameraKXFormat.RGBA_8888

    private fun initCamera() {
        vb.cameraxkPreviewLayout.apply {
            initCameraX(this@CameraXKActivity, MCameraKXConfig(_format, ACameraKXFacing.FRONT))
            setCameraXFrameListener(_frameAnalyzer)
            setCameraXCaptureListener(_cameraXKCaptureListener)
            startCameraX()
        }
        vb.cameraxkBtn.setOnClickListener {
            vb.cameraxkPreviewLayout.startCapture()
        }
    }

    private var _outputBitmap: Bitmap? = null
    private val _frameAnalyzer: ICameraXKFrameListener by lazy {
        object : ICameraXKFrameListener {
            @SuppressLint("UnsafeOptInUsageError")
            override fun invoke(imageProxy: ImageProxy) {
                when (_format) {
                    ACameraKXFormat.RGBA_8888 -> {
                        _outputBitmap = ImageProxyUtil.rgba8888ImageProxy2Rgba8888Bitmap(imageProxy)
                    }
                    ACameraKXFormat.YUV_420_888 -> {
                        _outputBitmap = ImageProxyUtil.yuv420888ImageProxy2JpegBitmap(imageProxy)
                    }
                }
                _outputBitmap?.let {
                    runOnUiThread {
                        vb.cameraxkImg1.setImageBitmap(_outputBitmap)
                    }
                }
                imageProxy.close()
            }
        }
    }

    private val _cameraXKCaptureListener = object : ICameraKXCaptureListener {
        override fun onCaptureSuccess(bitmap: Bitmap, imageRotation: Int) {
            runOnUiThread {
                vb.cameraxkImg.setImageBitmap(bitmap)
            }
        }

        override fun onCaptureFail() {}
    }
}