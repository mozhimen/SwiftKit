package com.mozhimen.componentk.camerak.camerax.annors

import android.annotation.SuppressLint
import androidx.annotation.IntDef
import androidx.camera.core.ImageCapture

/**
 * @ClassName ACameraKXCaptureMode
 * @Description TODO
 * @Author Mozhimen / Kolin Zhao
 * @Date 2023/9/13 1:51
 * @Version 1.0
 */
@SuppressLint("UnsafeOptInUsageError")
@IntDef(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY, ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY, ImageCapture.CAPTURE_MODE_ZERO_SHUTTER_LAG)
annotation class ACameraKXCaptureMode {
    companion object {
        const val MAXIMIZE_QUALITY = ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
        const val MINIMIZE_LATENCY = ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
        const val ZERO_SHUTTER_LAG = ImageCapture.CAPTURE_MODE_ZERO_SHUTTER_LAG
    }
}
