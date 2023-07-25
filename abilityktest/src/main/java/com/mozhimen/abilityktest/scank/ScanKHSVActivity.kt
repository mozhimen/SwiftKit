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
import com.mozhimen.basick.utilk.android.graphics.crop
import com.mozhimen.basick.utilk.android.graphics.rotate
import com.mozhimen.basick.utilk.android.graphics.scaleRatio
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.mos.MCameraXKConfig

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
            initCamera(this@ScanKHSVActivity, MCameraXKConfig(facing = ACameraXKFacing.BACK))
            setCameraXKFrameListener(_frameAnalyzer)
            startCamera()
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
                    }.rotate(90).apply {
                        crop(
                            (_ratio * this.width).toInt(),
                            (_ratio * this.width).toInt(),
                            ((1 - _ratio) * this.width / 2).toInt(),
                            ((this.height - _ratio * this.width) / 2).toInt()
                        ).apply {
                            scaleRatio( this.width / 5f, this.height / 5f)//降低分辨率提高运算速度
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