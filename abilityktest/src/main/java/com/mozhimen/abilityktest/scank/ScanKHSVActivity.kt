package com.mozhimen.abilityktest.scank

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.mozhimen.componentk.cameraxk.annors.CameraXKFacing
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.abilityk.scank.ScanKHSV
import com.mozhimen.abilityktest.R
import com.mozhimen.abilityktest.databinding.ActivityScankHsvBinding
import com.mozhimen.basick.basek.BaseKActivity
import com.mozhimen.basick.basek.BaseKViewModel
import com.mozhimen.basick.extsk.cropBitmap
import com.mozhimen.basick.utilk.UtilKBitmap
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.componentk.permissionk.PermissionK
import com.mozhimen.componentk.permissionk.annors.PermissionKAnnor
import com.mozhimen.opencvk.OpenCVK
import java.util.concurrent.locks.ReentrantLock

@PermissionKAnnor(permissions = [Manifest.permission.CAMERA])
class ScanKHSVActivity : BaseKActivity<ActivityScankHsvBinding, BaseKViewModel>(R.layout.activity_scank_hsv) {

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                initView(savedInstanceState)
            } else {
                PermissionK.applySetting(this)
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        require(OpenCVK.initSDK()) { "opencv init fail" }
        initCamera()
    }

    private fun initCamera() {
        vb.scankHsvPreview.initCamera(this, CameraXKFacing.BACK)
        vb.scankHsvPreview.setImageAnalyzer(_frameAnalyzer)
        vb.scankHsvPreview.startCamera()
    }

    private lateinit var _orgBitmap: Bitmap

    private val _options: HmsScanAnalyzerOptions = HmsScanAnalyzerOptions.Creator()
        .setHmsScanTypes(HmsScan.QRCODE_SCAN_TYPE)
        .setPhotoMode(true)
        .create()

    private val _frameAnalyzer: ImageAnalysis.Analyzer by lazy {
        object : ImageAnalysis.Analyzer {
            private val _reentrantLock = ReentrantLock()

            @SuppressLint("UnsafeOptInUsageError")
            override fun analyze(image: ImageProxy) {
                try {
                    _reentrantLock.lock()
                    val bitmap: Bitmap = if (image.format == ImageFormat.YUV_420_888) {
                        ImageConverter.yuv2Bitmap(image)!!
                    } else {
                        ImageConverter.jpeg2Bitmap(image)
                    }
                    val rotateBitmap = UtilKBitmap.rotateBitmap(bitmap, 90)
                    val ratio: Double =
                        vb.scankHsvQrscan.getRectSize().toDouble() / UtilKScreen.getScreenWidth().toDouble()
                    val cropBitmap = rotateBitmap.cropBitmap(
                        (ratio * rotateBitmap.width).toInt(),
                        (ratio * rotateBitmap.width).toInt(),
                        ((1 - ratio) * rotateBitmap.width / 2).toInt(),
                        ((rotateBitmap.height - ratio * rotateBitmap.width) / 2).toInt()
                    )
                    val scaleBitmap = UtilKBitmap.scaleBitmap(cropBitmap, cropBitmap.width / 5, cropBitmap.height / 5)//降低分辨率提高运算速度
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