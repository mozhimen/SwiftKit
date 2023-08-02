package com.mozhimen.componentk.camerak.camerax.temps

import androidx.camera.core.ImageProxy
import com.mozhimen.basick.utilk.android.util.UtilKLog
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.nio.byteBuffer2bytes
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXAnalyzer

class LuminosityAnalyzer : IUtilK, ICameraKXAnalyzer {

    override fun analyze(imageProxy: ImageProxy) {
        // Since format in ImageAnalysis is YUV, image.planes[0]
        // contains the Y (luminance) plane
        val buffer = imageProxy.planes[0].buffer
        // Extract image data from callback object
        val data = buffer.byteBuffer2bytes()
        // Convert the data into an array of pixel values
        val pixels = data.map { it.toInt() and 0xFF }
        // Compute average luminance for the image
        val luma = pixels.average()
        // Log the new luma value
        UtilKLog.vt(TAG, "Average luminosity luma $luma")
        // Update timestamp of last analyzed frame
    }
}
