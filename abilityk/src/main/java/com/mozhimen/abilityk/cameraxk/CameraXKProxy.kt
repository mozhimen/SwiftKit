package com.mozhimen.abilityk.cameraxk

import android.content.Context
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.abilityk.cameraxk.commons.ICameraXKCaptureListener
import com.mozhimen.abilityk.cameraxk.commons.ICameraXKListener
import com.mozhimen.abilityk.cameraxk.mos.CameraXKTimer
import com.mozhimen.basick.logk.LogK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

/**
 * @ClassName CameraXKProxy
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 1:17
 * @Version 1.0
 */
class CameraXKProxy(private val _context: Context) {
    companion object {
        private const val TAG = "CameraXKProxy>>>>>"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0 // aspect ratio 4x3
        private const val RATIO_16_9_VALUE = 16.0 / 9.0 // aspect ratio 16x9
    }

    private var _cameraProvider: ProcessCameraProvider? = null
    private var _cameraXKListener: ICameraXKListener? = null
    private var _cameraXKCaptureListener: ICameraXKCaptureListener? = null
    private var _lifecycleOwner: LifecycleOwner? = null

    private lateinit var _imageCapture: ImageCapture
    private lateinit var _imageAnalyzer: ImageAnalysis

    private var _flashMode by Delegates.observable(ImageCapture.FLASH_MODE_OFF) { _, _, new ->
        when (new) {
            ImageCapture.FLASH_MODE_ON -> _cameraXKListener?.onCameraFlashOn()
            ImageCapture.FLASH_MODE_AUTO -> _cameraXKListener?.onCameraFlashAuto()
            else -> _cameraXKListener?.onCameraFlashOff()
        }
    }// Selector showing which flash mode is selected (on, off or auto)

    private var _lensFacing =
        CameraSelector.DEFAULT_BACK_CAMERA// Selector showing which camera is selected (front or back)


    fun initCamera(
        owner: LifecycleOwner,
        facing: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    ) {
        this._lifecycleOwner = owner
        this._lensFacing = facing
    }

    fun setImageAnalyzer(analyzer: ImageAnalysis.Analyzer) {
        this._cameraXKAnalyzer = analyzer
    }

    fun setCameraXKListener(listener: ICameraXKListener) {
        this._cameraXKListener = listener
    }

    fun setCameraXKCaptureListener(listener: ICameraXKCaptureListener) {
        this._cameraXKCaptureListener = listener
    }

    fun changeFlash(@ImageCapture.FlashMode flash: Int) {
        _flashMode = flash
        _imageCapture.flashMode = _flashMode
    }

    fun changeCountDownTimer(timer: CameraXKTimer) {
        _selectedTimer = timer
    }

    fun changeCameraFacing(cameraSelector: CameraSelector) {
        if (_lensFacing != cameraSelector) {
            _lensFacing = if (_lensFacing == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
            startCamera()
        }
    }

    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(_context)
        cameraProviderFuture.addListener({
            try {
                _cameraProvider = cameraProviderFuture.get()
            } catch (e: InterruptedException) {
                _cameraXKListener?.onCameraStartFail(e.message.toString())
                LogK.et(TAG, "startCamera InterruptedException ${e.message}")
                return@addListener
            } catch (e: ExecutionException) {
                _cameraXKListener?.onCameraStartFail(e.message.toString())
                LogK.et(TAG, "startCamera ExecutionException ${e.message}")
                return@addListener
            }

            // The ratio for the output image and preview
            val aspectRatio = aspectRatio(_previewView.width, _previewView.height)
            // The display rotation
            val rotation = _previewView.display.rotation

            val localCameraProvider = _cameraProvider
                ?: throw IllegalStateException("Camera initialization failed.")

            // The Configuration of camera preview
            _preview = Preview.Builder()
                .setTargetAspectRatio(aspectRatio) // set the camera aspect ratio
                .setTargetRotation(rotation) // set the camera rotation
                .build()

            // The Configuration of image capture
            _imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY) // setting to have pictures with highest quality possible (may be slow)
                .setFlashMode(_flashMode) // set capture flash
                .setTargetAspectRatio(aspectRatio) // set the capture aspect ratio
                .setTargetRotation(rotation) // set the capture rotation
                .build()

            checkForHdrExtensionAvailability()

            // The Configuration of image analyzing
            _imageAnalyzer = ImageAnalysis.Builder()
                .setTargetAspectRatio(aspectRatio) // set the analyzer aspect ratio
                .setTargetRotation(rotation) // set the analyzer rotation
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) // in our analysis, we care about the latest image
                .build()
                .also { setLuminosityAnalyzer(it) }

            // Unbind the use-cases before rebinding them
            localCameraProvider.unbindAll()
            // Bind all use cases to the camera with lifecycle
            bindToLifecycle(localCameraProvider, _previewView)
        }, ContextCompat.getMainExecutor(context))
    }

    fun takePicture() = CoroutineScope(Dispatchers.Main).launch {
        // Show a timer based on user selection
        when (_selectedTimer) {
            CameraXKTimer.S3 -> for (i in 3 downTo 1) {
                delay(1000)
            }
            CameraXKTimer.S10 -> for (i in 10 downTo 1) {
                delay(1000)
            }
            else -> {
            }
        }
        captureImage()
    }

    fun changeHDRStatus(isOpen: Boolean) {
        if (_isOpenHdr != isOpen) {
            _isOpenHdr = !_isOpenHdr
            startCamera()
        }
    }

    private fun captureImage() {
        val localImageCapture = _imageCapture
        localImageCapture.takePicture(
            ContextCompat.getMainExecutor(_context), // the executor, on which the task will run
            _onImageCaptureCallback
        )
    }

    /**
     *  检测当前尺寸的最合适的长宽比
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }
}