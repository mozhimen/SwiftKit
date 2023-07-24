package com.mozhimen.componentk.cameraxk.temps

import androidx.camera.core.ImageProxy
import com.mozhimen.basick.utilk.android.util.UtilKLog
import com.mozhimen.basick.utilk.bases.IUtilK
import com.mozhimen.basick.utilk.java.nio.toByteArray
import com.mozhimen.componentk.cameraxk.commons.ICameraXKAnalyzer

class LuminosityAnalyzer : IUtilK, ICameraXKAnalyzer {

    override fun analyze(imageProxy: ImageProxy) {
        // Since format in ImageAnalysis is YUV, image.planes[0]
        // contains the Y (luminance) plane
        val buffer = imageProxy.planes[0].buffer
        // Extract image data from callback object
        val data = buffer.toByteArray()
        // Convert the data into an array of pixel values
        val pixels = data.map { it.toInt() and 0xFF }
        // Compute average luminance for the image
        val luma = pixels.average()
        // Log the new luma value
        UtilKLog.vt(TAG, "Average luminosity luma $luma")
        // Update timestamp of last analyzed frame
    }
}
