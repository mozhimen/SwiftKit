package com.mozhimen.abilitymk.cameraxmk.commons

import android.graphics.Bitmap

/**
 * @ClassName CameraXMKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 1:22
 * @Version 1.0
 */
interface ICameraXMKListener {
    fun onCameraStartFail(e: String)
    fun onCameraFlashOn()
    fun onCameraFlashAuto()
    fun onCameraFlashOff()
    fun onCheckCameraHDR(available: Boolean)
    fun onCameraHDROpen()
    fun onCaptureSuccess(bitmap: Bitmap)
    fun onCaptureFail()
}