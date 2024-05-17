package com.mozhimen.basick.utilk.android.opengl

import android.graphics.Bitmap
import android.opengl.GLSurfaceView
import android.os.Build
import android.view.PixelCopy
import androidx.annotation.UiThread
import com.mozhimen.basick.utilk.kotlin.UtilKResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * @ClassName UtilKGLSurfaceView
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/16
 * @Version 1.0
 */
suspend fun GLSurfaceView.takeScreenshotOnMain(maxResolution: Int, retries: Int = 1): Bitmap? =
    UtilKGLSurfaceView.takeScreenshotOnMain(this, maxResolution, retries)

///////////////////////////////////////////////////////////////////////////////////////

object UtilKGLSurfaceView {
    @JvmStatic
    @UiThread
    suspend fun takeScreenshotOnMain(glSurfaceView: GLSurfaceView, maxResolution: Int, retries: Int = 1): Bitmap? =
        withContext(Dispatchers.Main) {
            UtilKResult.runCatching_ofRetry(retries) { takeScreenshot(glSurfaceView, maxResolution) }.getOrNull()
        }

    @JvmStatic
    suspend fun takeScreenshot(glSurfaceView: GLSurfaceView, maxResolution: Int): Bitmap? =
        suspendCoroutine { cont ->
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                cont.resume(null)
                return@suspendCoroutine
            }

            glSurfaceView.queueEvent {
                try {
                    val outputScaling = maxResolution / maxOf(glSurfaceView.width, glSurfaceView.height).toFloat()
                    val inputScaling = outputScaling * 2

                    val inputBitmap = Bitmap.createBitmap(
                        (glSurfaceView.width * inputScaling).roundToInt(),
                        (glSurfaceView.height * inputScaling).roundToInt(),
                        Bitmap.Config.ARGB_8888
                    )

                    val onCompleted = { result: Int ->
                        if (result == PixelCopy.SUCCESS) {

                            // This rescaling limits the artifacts introduced by shaders.
                            val outputBitmap = Bitmap.createScaledBitmap(
                                inputBitmap,
                                (glSurfaceView.width * outputScaling).roundToInt(),
                                (glSurfaceView.height * outputScaling).roundToInt(),
                                true
                            )

                            cont.resume(outputBitmap)
                        } else {
                            cont.resumeWithException(RuntimeException("Cannot take screenshot. Error code: $result"))
                        }
                    }
                    PixelCopy.request(glSurfaceView, inputBitmap, onCompleted, glSurfaceView.handler)
                } catch (e: Exception) {
                    cont.resumeWithException(e)
                }
            }
        }
}