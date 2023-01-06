package com.mozhimen.abilityktest.scank

import android.Manifest
import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.RectF
import android.media.FaceDetector
import android.os.Bundle
import android.util.Log
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityktest.databinding.ActivityScankFaceBinding
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapDeal
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapFormat
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFormat
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.componentk.cameraxk.mos.CameraXKConfig
import com.mozhimen.uicorek.viewk.ViewKOverlay


/**
 * @ClassName ScanKFaceActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/3 14:46
 * @Version 1.0
 */
@APermissionK(Manifest.permission.CAMERA)
class ScanKFaceActivity : BaseActivityVB<ActivityScankFaceBinding>() {
    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKPermission.openSettingSelf(this)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        vb.scankFaceCamera.initCamera(this, CameraXKConfig(facing = ACameraXKFacing.FRONT, format = ACameraXKFormat.RGBA_8888))
        vb.scankFaceCamera.setCameraXKFrameListener(_frameAnalyzer)
        vb.scankFaceCamera.startCamera()
    }

    private var _rgb565Bitmap: Bitmap? = null
    private var _faceDetector: FaceDetector? = null
    private var _faces: Array<FaceDetector.Face?> = Array(1) { null }
    private var _facePointF = PointF()
    private var _currentTime = System.currentTimeMillis()
    private val _frameAnalyzer: ICameraXKFrameListener by lazy {
        object : ICameraXKFrameListener {
            override fun onFrame(image: ImageProxy) {
                if (System.currentTimeMillis() - _currentTime > 2000L) {
                    _currentTime = System.currentTimeMillis()
                    _rgb565Bitmap =
                        UtilKBitmapDeal.bitmap2Rgb565Bitmap(
                            UtilKBitmapDeal.rotateBitmap(
                                ImageConverter.rgba8888Image2Rgba8888Bitmap(image), -90, flipX = true
                            )
                        )
                    if (_faceDetector == null) {
                        _faceDetector = FaceDetector(_rgb565Bitmap!!.width, _rgb565Bitmap!!.height, 1)
                    }
                    val faceCount = _faceDetector!!.findFaces(_rgb565Bitmap!!, _faces)
                    Log.v(TAG, "faceCount: $faceCount")

                    runOnUiThread {
                        vb.scankFaceImg.setImageBitmap(_rgb565Bitmap)
                        if (_faces.getOrNull(0) != null && faceCount != 0) {
                            _faces[0]!!.getMidPoint(_facePointF)
                            val eyeDistance = _faces[0]!!.eyesDistance()
                            vb.scankFaceOverlay.setObjectRect(
                                _rgb565Bitmap!!.width, _rgb565Bitmap!!.height, listOf(
                                    ViewKOverlay.Detection(
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
                }
                image.close()
            }
        }
    }
}