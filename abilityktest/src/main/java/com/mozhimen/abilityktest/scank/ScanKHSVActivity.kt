package com.mozhimen.abilityktest.scank

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import android.util.Log
import androidx.camera.core.ImageProxy
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.abilityk.scank.ScanKHSV
import com.mozhimen.abilityktest.databinding.ActivityScankHsvBinding
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.view.display.UtilKScreen
import com.mozhimen.basick.utilk.graphics.bitmap.UtilKBitmapDeal
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CUseFeature
import com.mozhimen.basick.utilk.content.activity.UtilKLaunchActivity
import com.mozhimen.basick.utilk.graphics.bitmap.cropBitmap
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.mos.CameraXKConfig
import java.util.concurrent.locks.ReentrantLock

@AManifestKRequire(CPermission.CAMERA, CUseFeature.CAMERA, CUseFeature.CAMERA_AUTOFOCUS)
@APermissionCheck(CPermission.CAMERA)
class ScanKHSVActivity : BaseActivityVB<ActivityScankHsvBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKLaunchActivity.startSettingAppDetails(this)
            }
        }
    }

    @Throws(Exception::class)
    override fun initView(savedInstanceState: Bundle?) {
        initCamera()
    }

    private fun initCamera() {
        vb.scankHsvPreview.initCamera(this, CameraXKConfig(facing = ACameraXKFacing.BACK))
        vb.scankHsvPreview.setCameraXKFrameListener(_frameAnalyzer)
        vb.scankHsvPreview.startCamera()
    }

    private lateinit var _orgBitmap: Bitmap

    private val _frameAnalyzer: ICameraXKFrameListener by lazy {
        object : ICameraXKFrameListener {
            private val _reentrantLock = ReentrantLock()

            @SuppressLint("UnsafeOptInUsageError")
            override fun onFrame(image: ImageProxy) {
                try {
                    _reentrantLock.lock()
                    val bitmap: Bitmap = if (image.format == ImageFormat.YUV_420_888) {
                        ImageConverter.yuv420888Image2JpegBitmap(image)!!
                    } else {
                        ImageConverter.jpegImage2JpegBitmap(image)
                    }
                    val rotateBitmap = UtilKBitmapDeal.rotateBitmap(bitmap, 90)
                    val ratio: Double =
                        vb.scankHsvQrscan.getRectSize().toDouble() / UtilKScreen.getRealScreenWidth().toDouble()
                    val cropBitmap = rotateBitmap.cropBitmap(
                        (ratio * rotateBitmap.width).toInt(),
                        (ratio * rotateBitmap.width).toInt(),
                        ((1 - ratio) * rotateBitmap.width / 2).toInt(),
                        ((rotateBitmap.height - ratio * rotateBitmap.width) / 2).toInt()
                    )
                    val scaleBitmap = UtilKBitmapDeal.scaleBitmap(cropBitmap, cropBitmap.width / 5, cropBitmap.height / 5)//降低分辨率提高运算速度
                    val results = ScanKHSV.colorAnalyze(scaleBitmap)
                    Log.i(TAG, "analyze: $results")
                } finally {
                    _reentrantLock.unlock()
                }

                image.close()
            }
        }
    }
}