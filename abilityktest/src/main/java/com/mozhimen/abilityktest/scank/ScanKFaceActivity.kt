package com.mozhimen.abilityktest.scank

import android.Manifest
import android.graphics.Bitmap
import android.media.FaceDetector
import android.os.Bundle
import android.util.Log
import androidx.camera.core.ImageAnalysis
import com.mozhimen.abilityktest.databinding.ActivityScankFaceBinding
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapFormat
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFormat
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.componentk.cameraxk.mos.CameraXKConfig


/**
 * @ClassName ScanKFaceActivity
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2023/1/3 14:46
 * @Version 1.0
 */
@APermissionK(permissions = [Manifest.permission.CAMERA])
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
        vb.scankFaceCamera.initCamera(this, CameraXKConfig(facing = ACameraXKFacing.BACK, format = ACameraXKFormat.RGBA_8888))
        vb.scankFaceCamera.setImageAnalyzer(_frameAnalyzer)
        vb.scankFaceCamera.startCamera()
    }

    private var _rgb565Bitmap: Bitmap? = null
    private var _faceDetector: FaceDetector? = null
    private var _faces: Array<FaceDetector.Face> = arrayOf()
    private var _currentTime = System.currentTimeMillis()
    private val _frameAnalyzer: ImageAnalysis.Analyzer by lazy {
        ImageAnalysis.Analyzer { image ->
            if (System.currentTimeMillis() - _currentTime > 2000L){
                _currentTime = System.currentTimeMillis()
                _rgb565Bitmap = UtilKBitmapFormat.rgba8888Bitmap2Rgb565Bitmap(ImageConverter.rgba8888Image2Rgba8888Bitmap(image))
                _faceDetector = FaceDetector(_rgb565Bitmap!!.width, _rgb565Bitmap!!.height, 1)
                val faceCount = _faceDetector!!.findFaces(_rgb565Bitmap!!, _faces)
                Log.d(TAG, "faceCount: $faceCount")
            }
            image.close()
        }
    }
}