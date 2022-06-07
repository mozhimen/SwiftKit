package com.mozhimen.abilityk.cameraxk.helpers

import android.util.Log
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

object LuminosityAnalyzer {

    private val TAG="LuminosityAnalyzer>>>>>"

    @JvmStatic
    fun analyze(image: ImageProxy) {
        // Since format in ImageAnalysis is YUV, image.planes[0]
        // contains the Y (luminance) plane
        val buffer = image.planes[0].buffer
        // Extract image data from callback object
        val data = buffer.toByteArray()
        // Convert the data into an array of pixel values
        val pixels = data.map { it.toInt() and 0xFF }
        // Compute average luminance for the image
        val luma = pixels.average()
        // Log the new luma value
        Log.v(TAG, "Average luminosity luma $luma")
        // Update timestamp of last analyzed frame
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }
}
