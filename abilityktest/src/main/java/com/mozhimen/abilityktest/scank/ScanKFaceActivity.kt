package com.mozhimen.abilityktest.scank

import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.RectF
import android.media.FaceDetector
import android.os.Bundle
import android.util.Log
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityktest.databinding.ActivityScankFaceBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CUseFeature
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.graphics.rotate
import com.mozhimen.basick.utilk.android.graphics.asRgb565Bitmap
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFacing
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFormat
import com.mozhimen.componentk.camerak.camerax.commons.ICameraXKFrameListener
import com.mozhimen.componentk.camerak.camerax.helpers.ImageProxyUtil
import com.mozhimen.componentk.camerak.camerax.mos.MCameraKXConfig
import com.mozhimen.uicorek.viewk.scan.ViewKScanOverlay

/**
 * @ClassName ScanKFaceActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/3 14:46
 * @Version 1.0
 */
@AManifestKRequire(CPermission.CAMERA, CUseFeature.CAMERA, CUseFeature.CAMERA_AUTOFOCUS)
@APermissionCheck(CPermission.CAMERA)
class ScanKFaceActivity : BaseActivityVB<ActivityScankFaceBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.requestPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKLaunchActivity.startSettingAppDetails(this)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.scankFaceCamerax.apply {
            initCameraX(this@ScanKFaceActivity, MCameraKXConfig(facing = ACameraKXFacing.FRONT, format = ACameraKXFormat.RGBA_8888))
            setCameraXFrameListener(_frameAnalyzer)
            startCameraX()
        }
    }

    private var _rgb565Bitmap: Bitmap? = null
    private var _faceDetector: FaceDetector? = null
    private var _faces: Array<FaceDetector.Face?> = Array(1) { null }
    private var _facePointF = PointF()
    private var _currentTime = System.currentTimeMillis()
    private val _frameAnalyzer: ICameraXKFrameListener by lazy {
        object : ICameraXKFrameListener {
            override fun invoke(imageProxy: ImageProxy) {
                if (System.currentTimeMillis() - _currentTime > 2000L) {
                    _rgb565Bitmap = ImageProxyUtil.rgba8888ImageProxy2Rgba8888Bitmap(imageProxy).asRgb565Bitmap().rotate(-90, flipX = true)
                    if (_faceDetector == null)
                        _faceDetector = FaceDetector(_rgb565Bitmap!!.width, _rgb565Bitmap!!.height, 1)
                    val faceCount = _faceDetector!!.findFaces(_rgb565Bitmap!!, _faces)
                    Log.v(TAG, "faceCount: $faceCount")

                    runOnUiThread {
                        vb.scankFaceImg.setImageBitmap(_rgb565Bitmap)
                        if (_faces.getOrNull(0) != null && faceCount != 0) {
                            _faces[0]!!.getMidPoint(_facePointF)
                            val eyeDistance = _faces[0]!!.eyesDistance()
                            vb.scankFaceOverlay.setObjectRect(
                                _rgb565Bitmap!!.width, _rgb565Bitmap!!.height, listOf(
                                    ViewKScanOverlay.Detection(
                                        RectF(
                                            _facePointF.x - eyeDistance,
                                            _facePointF.y - eyeDistance,
                                            _facePointF.x + eyeDistance,
                                            _facePointF.y + eyeDistance,
                                        ), null
                                    )
                                )
                            )
                        } else {
                            vb.scankFaceOverlay.clearObjectRect()
                        }
                    }
                    _currentTime = System.currentTimeMillis()
                }
                imageProxy.close()
            }
        }
    }
}