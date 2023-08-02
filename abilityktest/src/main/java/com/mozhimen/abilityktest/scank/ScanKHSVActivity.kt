package com.mozhimen.abilityktest.scank

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import android.util.Log
import androidx.camera.core.ImageProxy
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFacing
import com.mozhimen.componentk.camerak.camerax.helpers.ImageProxyUtil
import com.mozhimen.abilityk.scank.ScanKHSV
import com.mozhimen.abilityktest.databinding.ActivityScankHsvBinding
import com.mozhimen.basick.elemk.androidx.appcompat.bases.BaseActivityVB
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.utilk.android.view.UtilKScreen
import com.mozhimen.basick.manifestk.permission.ManifestKPermission
import com.mozhimen.basick.manifestk.permission.annors.APermissionCheck
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CUseFeature
import com.mozhimen.basick.utilk.android.app.UtilKLaunchActivity
import com.mozhimen.basick.utilk.android.graphics.applyAnyBitmapCrop
import com.mozhimen.basick.utilk.android.graphics.applyAnyBitmapRotate
import com.mozhimen.basick.utilk.android.graphics.applyAnyBitmapScaleRatio
import com.mozhimen.componentk.camerak.camerax.commons.ICameraXKFrameListener
import com.mozhimen.componentk.camerak.camerax.mos.MCameraKXConfig

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
        vb.scankHsvPreview.apply {
            initCameraX(this@ScanKHSVActivity, MCameraKXConfig(facing = ACameraKXFacing.BACK))
            setCameraXFrameListener(_frameAnalyzer)
            startCameraX()
        }
    }

    private var _orgBitmap: Bitmap? = null
    private var _lastTime: Long = System.currentTimeMillis()
    private val _ratio: Double by lazy { vb.scankHsvQrscan.getRectSize().toDouble() / UtilKScreen.getRealWidth().toDouble() }
    private val _frameAnalyzer: ICameraXKFrameListener by lazy {
        object : ICameraXKFrameListener {

            @SuppressLint("UnsafeOptInUsageError")
            override fun invoke(imageProxy: ImageProxy) {
                if (System.currentTimeMillis() - _lastTime >= 1000) {
                    _orgBitmap = if (imageProxy.format == ImageFormat.YUV_420_888) {
                        ImageProxyUtil.yuv420888ImageProxy2JpegBitmap(imageProxy)!!
                    } else {
                        ImageProxyUtil.jpegImageProxy2JpegBitmap(imageProxy)
                    }.applyAnyBitmapRotate(90).apply {
                        applyAnyBitmapCrop(
                            (_ratio * this.width).toInt(),
                            (_ratio * this.width).toInt(),
                            ((1 - _ratio) * this.width / 2).toInt(),
                            ((this.height - _ratio * this.width) / 2).toInt()
                        ).apply {
                            applyAnyBitmapScaleRatio( this.width / 5f, this.height / 5f)//降低分辨率提高运算速度
                        }
                    }
                    val results = ScanKHSV.colorAnalyze(_orgBitmap!!)
                    Log.i(TAG, "analyze: $results")
                    _lastTime = System.currentTimeMillis()
                }

                imageProxy.close()
            }
        }
    }
}