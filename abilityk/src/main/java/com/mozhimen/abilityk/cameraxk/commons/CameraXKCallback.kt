package com.mozhimen.abilityk.cameraxk.commons

import android.graphics.Bitmap

/**
 * @ClassName CameraXKListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 5:44
 * @Version 1.0
 */
open class CameraXKCallback : ICameraXKListener {
    override fun onCameraStartFail(e: String) {}

    override fun onCameraFlashOn() {}

    override fun onCameraFlashAuto() {}

    override fun onCameraFlashOff() {}

    override fun onCameraHDRCheck(available: Boolean) {}

    override fun onCameraHDROpen() {}
}