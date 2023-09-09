package com.mozhimen.componentktest.cameraxk

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.camera.view.PreviewView
import com.mozhimen.basick.elemk.android.graphics.cons.CImageFormat
import com.mozhimen.basick.elemk.android.graphics.cons.CPixelFormat
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.lintk.optin.OptInFieldCall_Close
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CUseFeature
import com.mozhimen.basick.utilk.android.graphics.applyAnyBitmapRotate
import com.mozhimen.basick.utilk.kotlin.intByte2strByte
import com.mozhimen.uicorek.adaptk.systembar.annors.AAdaptKSystemBarProperty
import com.mozhimen.uicorek.adaptk.systembar.cons.CProperty
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFacing
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFormat
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXCaptureListener
import com.mozhimen.componentk.camerak.camerax.commons.ICameraXKFrameListener
import com.mozhimen.componentk.camerak.camerax.helpers.jpegImageProxy2JpegBitmap
import com.mozhimen.componentk.camerak.camerax.helpers.rgba8888ImageProxy2Rgba8888Bitmap
import com.mozhimen.componentk.camerak.camerax.helpers.yuv420888ImageProxy2JpegBitmap
import com.mozhimen.componentk.camerak.camerax.mos.MCameraKXConfig
import com.mozhimen.componentktest.databinding.ActivityCameraxkBinding
import com.mozhimen.uicorek.adaptk.systembar.initAdaptKSystemBar

@AManifestKRequire(CPermission.CAMERA, CUseFeature.CAMERA, CUseFeature.CAMERA_AUTOFOCUS)
@APermissionCheck(CPermission.CAMERA)
@AAdaptKSystemBarProperty(CProperty.IMMERSED_HARD_STICKY)
class CameraXKActivity : BaseActivityVB<ActivityCameraxkBinding>() {

    override fun initFlag() {
        initAdaptKSystemBar()
    }

    private var _isInitData = false
    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.requestPermissions(this, onSuccess = {
            _isInitData = true
            super.initData(savedInstanceState)
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        initCamera()
    }

    override fun onResume() {
        super.onResume()
        if (!_isInitData) initData(null)
    }

    private val _format = ACameraKXFormat.YUV_420_888

    private fun initCamera() {
//        vb.cameraxkPreviewLayout.previewView?.scaleType = PreviewView.ScaleType.FILL_CENTER
        vb.cameraxkPreviewLayout.apply {
            initCameraKX(this@CameraXKActivity, MCameraKXConfig(_format, ACameraKXFacing.FRONT))
            setCameraXFrameListener(_cameraKXFrameListener)
            setCameraXCaptureListener(_cameraKXCaptureListener)
        }
        vb.cameraxkBtn.setOnClickListener {
            vb.cameraxkPreviewLayout.startCapture()
        }
    }

    private var _outputBitmap: Bitmap? = null

    @OptIn(OptInFieldCall_Close::class)
    private val _cameraKXFrameListener: ICameraXKFrameListener by lazy {
        object : ICameraXKFrameListener {
            @SuppressLint("UnsafeOptInUsageError")
            override fun invoke(imageProxy: ImageProxy) {
                when (_format) {
                    ACameraKXFormat.RGBA_8888 -> _outputBitmap = imageProxy.rgba8888ImageProxy2Rgba8888Bitmap()
                    ACameraKXFormat.YUV_420_888 -> _outputBitmap = imageProxy.yuv420888ImageProxy2JpegBitmap()
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

    private val _cameraKXCaptureListener = object : ICameraKXCaptureListener {
        override fun onCaptureSuccess(bitmap: Bitmap, imageRotation: Int) {
            runOnUiThread {
                vb.cameraxkImg.setImageBitmap(bitmap)
            }
        }
    }
}