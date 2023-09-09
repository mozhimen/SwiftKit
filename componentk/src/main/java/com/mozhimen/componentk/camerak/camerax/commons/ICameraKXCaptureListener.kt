package com.mozhimen.componentk.camerak.camerax.commons

import android.graphics.Bitmap

/**
 * @ClassName ICameraXKCaptureListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/9 19:33
 * @Version 1.0
 */
interface ICameraKXCaptureListener {
    fun onCaptureSuccess(bitmap: Bitmap, imageRotation: Int)
    fun onCaptureFail() {}
}