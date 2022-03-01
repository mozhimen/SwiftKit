package com.mozhimen.abilitymk.cameraxmk.commons

import android.graphics.Bitmap

/**
 * @ClassName CameraXMKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 5:44
 * @Version 1.0
 */
open class CameraXMKListener : ICameraXMKListener {
    override fun onCameraStartFail(e: String) {}

    override fun onCameraFlashOn() {}

    override fun onCameraFlashAuto() {}

    override fun onCameraFlashOff() {}

    override fun onCheckCameraHDR(available: Boolean) {}

    override fun onCameraHDROpen() {}

    override fun onCaptureSuccess(bitmap: Bitmap) {}

    override fun onCaptureFail() {}
}