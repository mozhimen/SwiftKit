package com.mozhimen.componentk.cameraxk.commons

import android.graphics.Bitmap

/**
 * @ClassName ICameraXKCaptureListener
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/5/9 19:33
 * @Version 1.0
 */
interface ICameraXKCaptureListener {
    fun onCaptureSuccess(bitmap: Bitmap, imageRotation: Int)
    fun onCaptureFail()
}