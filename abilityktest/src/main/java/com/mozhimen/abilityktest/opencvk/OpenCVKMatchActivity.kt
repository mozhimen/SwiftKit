package com.mozhimen.abilityktest.opencvk

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Bundle
import androidx.camera.core.ImageProxy
import com.mozhimen.abilityk.opencvk.OpenCVKMatch
import com.mozhimen.abilityk.opencvk.OpenCVKTrans
import com.mozhimen.abilityk.opencvk.setMat
import com.mozhimen.abilityktest.R
import com.mozhimen.abilityktest.databinding.ActivityOpencvkMatchBinding
import com.mozhimen.basick.elemk.activity.bases.BaseActivityVB
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionK
import com.mozhimen.basick.utilk.UtilKPermission
import com.mozhimen.basick.utilk.UtilKRes
import com.mozhimen.basick.utilk.UtilKScreen
import com.mozhimen.basick.utilk.bitmap.UtilKBitmapDeal
import com.mozhimen.basick.utilk.exts.cropBitmap
import com.mozhimen.basick.utilk.exts.drawable2Bitmap
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.helpers.ImageConverter
import com.mozhimen.componentk.cameraxk.mos.CameraXKConfig
import com.mozhimen.opencvk.OpenCVK
import java.util.concurrent.locks.ReentrantLock

@APermissionK(Manifest.permission.CAMERA)
class OpenCVKMatchActivity : BaseActivityVB<ActivityOpencvkMatchBinding>() {
    private val _templateMat by lazy { OpenCVKTrans.bitmap2Mat(UtilKRes.getDrawable(R.mipmap.opencvk_contrast_test)!!.drawable2Bitmap()) }

    override fun initData(savedInstanceState: Bundle?) {
        PermissionK.initPermissions(this) {
            if (it) {
                super.initData(savedInstanceState)
            } else {
                UtilKPermission.openSettingSelf()
            }
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        require(OpenCVK.initSDK()) { "opencv init fail" }
        initCamera()
    }

    private fun initCamera() {
        vb.opencvkMatchPreview.initCamera(this, CameraXKConfig(facing = ACameraXKFacing.BACK))
        vb.opencvkMatchPreview.setCameraXKFrameListener(_frameAnalyzer)
        vb.opencvkMatchPreview.startCamera()
    }

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
                        vb.opencvkMatchQrscan.getRectSize().toDouble() / UtilKScreen.getRealScreenWidth().toDouble()

                    val cropBitmap = rotateBitmap.cropBitmap(
                        (ratio * rotateBitmap.width).toInt(),
                        (ratio * rotateBitmap.width).toInt(),
                        ((1 - ratio) * rotateBitmap.width / 2).toInt(),
                        ((rotateBitmap.height - ratio * rotateBitmap.width) / 2).toInt()
                    )

                    val srcMat = OpenCVKTrans.bitmap2Mat(cropBitmap)

                    val resultMat = OpenCVKMatch.templateMatch(srcMat, _templateMat)
                    Thread.sleep(100)
                    try {
                        vb.opencvkMatchImg.setMat(resultMat)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        srcMat.release()
                        resultMat.release()
                    }
                } finally {
                    _reentrantLock.unlock()
                }

                image.close()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _templateMat.release()
    }
}