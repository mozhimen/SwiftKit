package com.mozhimen.abilityktest.scank

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import android.util.Log
import androidx.camera.core.ImageProxy
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.helpers.ImageProxyUtil
import com.mozhimen.abilityk.scank.ScanKHSV
import com.mozhimen.abilityktest.databinding.ActivityScankHsvBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.utilk.android.graphics.UtilKBitmapDeal
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CUseFeature
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.graphics.cropBitmap
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.mos.MCameraXKConfig
import java.util.concurrent.locks.ReentrantLock

@AManifestKRequire(CPermission.CAMERA, CUseFeature.CAMERA, CUseFeature.CAMERA_AUTOFOCUS)
@APermissionCheck(CPermission.CAMERA)
class ScanKHSVActivity : BaseActivityVB<ActivityScankHsvBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        ManifestKPermission.requestPermissions(this) {
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
        vb.scankHsvPreview.initCamera(this, MCameraXKConfig(facing = ACameraXKFacing.BACK))
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
                        ImageProxyUtil.yuv420888ImageProxy2JpegBitmap(image)!!
                    } else {
                        ImageProxyUtil.jpegImageProxy2JpegBitmap(image)
                    }
                    val rotateBitmap = UtilKBitmapDeal.rotateBitmap(bitmap, 90)
                    val ratio: Double =
                        vb.scankHsvQrscan.getRectSize().toDouble() / UtilKScreen.getRealWidth().toDouble()
                    val cropBitmap = rotateBitmap.cropBitmap(
                        (ratio * rotateBitmap.width).toInt(),
                        (ratio * rotateBitmap.width).toInt(),
                        ((1 - ratio) * rotateBitmap.width / 2).toInt(),
                        ((rotateBitmap.height - ratio * rotateBitmap.width) / 2).toInt()
                    )
                    val scaleBitmap = UtilKBitmapDeal.scaleBitmapRatio(cropBitmap, cropBitmap.width / 5f, cropBitmap.height / 5f)//降低分辨率提高运算速度
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