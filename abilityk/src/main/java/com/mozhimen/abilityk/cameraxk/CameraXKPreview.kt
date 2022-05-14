package com.mozhimen.abilityk.cameraxk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.util.Log
import com.mozhimen.abilityk.R
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.extensions.ExtensionMode
import androidx.camera.extensions.ExtensionsManager
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.slider.Slider
import com.mozhimen.abilityk.cameraxk.commons.ICameraXKCaptureListener
import com.mozhimen.abilityk.cameraxk.commons.ICameraXKListener
import com.mozhimen.abilityk.cameraxk.helpers.ImageConverter
import com.mozhimen.abilityk.cameraxk.mos.CameraXKTimer
import com.mozhimen.abilityk.cameraxk.helpers.ThreadExecutor
import kotlinx.coroutines.*
import java.util.concurrent.ExecutionException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

/**
 * @ClassName CameraXKPreview
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 0:22
 * @Version 1.0
 */
@SuppressLint("LongLogTag")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CameraXKPreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val TAG = "CameraXKPreviewView>>>>>"

    companion object {
        private const val RATIO_4_3_VALUE = 4.0 / 3.0 // aspect ratio 4x3
        private const val RATIO_16_9_VALUE = 16.0 / 9.0 // aspect ratio 16x9
    }

    private var _displayId = -1
    private lateinit var _analyzerThread: HandlerThread
    private lateinit var _previewView: PreviewView
    private lateinit var _sliderContainer: FrameLayout
    private lateinit var _slider: Slider
    private lateinit var _preview: Preview
    private lateinit var _imageCapture: ImageCapture
    private lateinit var _imageAnalyzer: ImageAnalysis

    // An instance for display manager to get display change callbacks
    private val _displayManager by lazy { context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
    private var _cameraProvider: ProcessCameraProvider? = null
    private var _hdrCameraSelector: CameraSelector? = null
    private var _flashMode by Delegates.observable(ImageCapture.FLASH_MODE_OFF) { _, _, new ->
        when (new) {
            ImageCapture.FLASH_MODE_ON -> _cameraXKListener?.onCameraFlashOn()
            ImageCapture.FLASH_MODE_AUTO -> _cameraXKListener?.onCameraFlashAuto()
            else -> _cameraXKListener?.onCameraFlashOff()
        }
    }// Selector showing which flash mode is selected (on, off or auto)
    private val _onAttachStateChangeListener = object : OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View?) {
            _displayManager.registerDisplayListener(_displayListener, null)
        }

        override fun onViewDetachedFromWindow(v: View?) {
            _displayManager.unregisterDisplayListener(_displayListener)
        }
    }
    private val _displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit

        @SuppressLint("UnsafeOptInUsageError")
        override fun onDisplayChanged(displayId: Int) {
            if (displayId == this@CameraXKPreviewView._displayId) {
                _preview.targetRotation = this@CameraXKPreviewView.display.rotation
                _imageCapture.targetRotation = this@CameraXKPreviewView.display.rotation
                _imageAnalyzer.targetRotation = this@CameraXKPreviewView.display.rotation
            }
        }
    }
    private val _onImageCaptureCallback = object : ImageCapture.OnImageCapturedCallback() {
        @SuppressLint("UnsafeOptInUsageError")
        override fun onCaptureSuccess(image: ImageProxy) {
            Log.d(TAG, "onCaptureSuccess: ${image.format} ${image.width}x${image.height}")
            if (image.format == ImageFormat.YUV_420_888) {
                val bitmap = ImageConverter.yuv2Bitmap(image)
                if (bitmap != null) {
                    _cameraXKCaptureListener?.onCaptureSuccess(bitmap)
                }
            } else if (image.format == ImageFormat.JPEG) {
                val bitmap = ImageConverter.jpeg2Bitmap(image)
                Log.d(TAG, "onCaptureSuccess: ${bitmap.width}x${bitmap.height}")
                _cameraXKCaptureListener?.onCaptureSuccess(bitmap)
            }
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e(TAG, "onError: ${exception.message}")
            _cameraXKCaptureListener?.onCaptureFail()
            exception.printStackTrace()
        }
    }

    private var _cameraXKListener: ICameraXKListener? = null
    private var _cameraXKAnalyzer: ImageAnalysis.Analyzer? = null
    private var _cameraXKCaptureListener: ICameraXKCaptureListener? = null
    private var _lifecycleOwner: LifecycleOwner? = null
    private var _lensFacing =
        CameraSelector.DEFAULT_BACK_CAMERA// Selector showing which camera is selected (front or back)
    private var _isOpenHdr =
        false// Selector showing is hdr enabled or not (will work, only if device's camera supports hdr on hardware level)
    private var _selectedTimer = CameraXKTimer.OFF

    init {
        initView()
    }

    fun initCamera(
        owner: LifecycleOwner,
        facing: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    ) {
        this._lifecycleOwner = owner
        this._lensFacing = facing
    }

    fun setCameraXKListener(listener: ICameraXKListener) {
        this._cameraXKListener = listener
    }

    fun setCameraXKCaptureListener(listener: ICameraXKCaptureListener) {
        this._cameraXKCaptureListener = listener
    }

    fun setImageAnalyzer(analyzer: ImageAnalysis.Analyzer) {
        this._cameraXKAnalyzer = analyzer
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            try {
                _cameraProvider = cameraProviderFuture.get()
            } catch (e: InterruptedException) {
                _cameraXKListener?.onCameraStartFail(e.message.toString())
                Log.e(TAG, "startCamera: Error starting camera")
                return@addListener
            } catch (e: ExecutionException) {
                _cameraXKListener?.onCameraStartFail(e.message.toString())
                Log.e(TAG, "startCamera: Error starting camera")
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

    fun changeHDRStatus(isOpen: Boolean) {
        if (_isOpenHdr != isOpen) {
            _isOpenHdr = !_isOpenHdr
            startCamera()
        }
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

    private fun captureImage() {
        val localImageCapture = _imageCapture
        localImageCapture.takePicture(
            ContextCompat.getMainExecutor(context), // the executor, on which the task will run
            _onImageCaptureCallback
        )
    }

    private fun checkForHdrExtensionAvailability() {
        // Create a Vendor Extension for HDR
        val extensionsManagerFuture = ExtensionsManager.getInstanceAsync(context, _cameraProvider ?: return)
        extensionsManagerFuture.addListener(
            {
                val extensionsManager = extensionsManagerFuture.get() ?: return@addListener

                val isAvailable = extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.HDR)

                // check for any extension availability
                println("AUTO " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.AUTO))
                println("HDR " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.HDR))
                println(
                    "FACE RETOUCH " + extensionsManager.isExtensionAvailable(
                        _lensFacing,
                        ExtensionMode.FACE_RETOUCH
                    )
                )
                println("BOKEH " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.BOKEH))
                println("NIGHT " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.NIGHT))
                println("NONE " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.NONE))

                // Check if the extension is available on the device
                if (!isAvailable) {
                    _cameraXKListener?.onCameraHDRCheck(false)
                } else if (_isOpenHdr) {
                    // If yes, turn on if the HDR is turned on by the user
                    _cameraXKListener?.onCameraHDROpen()
                    _hdrCameraSelector =
                        extensionsManager.getExtensionEnabledCameraSelector(_lensFacing, ExtensionMode.HDR)
                }
            }, ContextCompat.getMainExecutor(context)
        )
    }

    private fun bindToLifecycle(localCameraProvider: ProcessCameraProvider, previewView: PreviewView) {
        try {
            localCameraProvider.bindToLifecycle(
                _lifecycleOwner!!, // current lifecycle owner
                _hdrCameraSelector ?: _lensFacing, // either front or back facing
                _preview, // camera preview use case
                _imageCapture, // image capture use case
                _imageAnalyzer, // image analyzer use case
            ).apply {
                // Init camera exposure control
                cameraInfo.exposureState.run {
                    val lower = exposureCompensationRange.lower
                    val upper = exposureCompensationRange.upper

                    _slider.run {
                        valueFrom = lower.toFloat()
                        valueTo = upper.toFloat()
                        stepSize = 1f
                        value = exposureCompensationIndex.toFloat()

                        addOnChangeListener { _, value, _ ->
                            cameraControl.setExposureCompensationIndex(value.toInt())
                        }
                    }
                }
            }

            // Attach the viewfinder's surface provider to preview use case
            _preview.setSurfaceProvider(previewView.surfaceProvider)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to bind use cases", e)
        }
    }

    private fun setLuminosityAnalyzer(imageAnalysis: ImageAnalysis) {
        // Use a worker thread for image analysis to prevent glitches
        _analyzerThread = HandlerThread("CameraXKLuminosityAnalysis").apply { start() }
        _cameraXKAnalyzer?.let {
            imageAnalysis.setAnalyzer(ThreadExecutor(Handler(_analyzerThread.looper)), it)
        }
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_cameraxk_preview, this)
        _previewView = findViewById(R.id.cameraxk_preview)
        _sliderContainer = findViewById(R.id.cameraxk_container)
        _slider = findViewById(R.id.cameraxk_slider)
        _previewView.addOnAttachStateChangeListener(_onAttachStateChangeListener)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        _displayId = _previewView.display.displayId
    }

    /**
     *  Detecting the most suitable aspect ratio for current dimensions
     *
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _analyzerThread.interrupt()
        _displayManager.unregisterDisplayListener(_displayListener)
    }
}