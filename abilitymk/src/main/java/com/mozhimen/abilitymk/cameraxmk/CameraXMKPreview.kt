package com.mozhimen.abilitymk.cameraxmk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.util.Log
import com.mozhimen.abilitymk.R
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
import com.mozhimen.abilitymk.cameraxmk.commons.ICameraXMKListener
import com.mozhimen.abilitymk.cameraxmk.helpers.ImageConverter
import com.mozhimen.abilitymk.cameraxmk.helpers.LuminosityAnalyzer
import com.mozhimen.abilitymk.cameraxmk.mos.CameraXMKTimer
import com.mozhimen.abilitymk.cameraxmk.helpers.ThreadExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.properties.Delegates

/**
 * @ClassName CameraXMKPreview
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 0:22
 * @Version 1.0
 */
@SuppressLint("LongLogTag")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CameraXMKPreviewView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val TAG = "CameraXMKPreviewView>>>>>"

        private const val RATIO_4_3_VALUE = 4.0 / 3.0 // aspect ratio 4x3
        private const val RATIO_16_9_VALUE = 16.0 / 9.0 // aspect ratio 16x9
    }

    private var displayId = -1
    private lateinit var analyzerThread: HandlerThread
    private lateinit var previewView: PreviewView
    private lateinit var sliderContainer: FrameLayout
    private lateinit var slider: Slider
    private lateinit var preview: Preview
    private lateinit var imageCapture: ImageCapture
    private lateinit var imageAnalyzer: ImageAnalysis

    // An instance for display manager to get display change callbacks
    private val displayManager by lazy { context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }
    private var cameraProvider: ProcessCameraProvider? = null
    private var hdrCameraSelector: CameraSelector? = null
    private var flashMode by Delegates.observable(ImageCapture.FLASH_MODE_OFF) { _, _, new ->
        when (new) {
            ImageCapture.FLASH_MODE_ON -> cameraXMKListener?.onCameraFlashOn()
            ImageCapture.FLASH_MODE_AUTO -> cameraXMKListener?.onCameraFlashAuto()
            else -> cameraXMKListener?.onCameraFlashOff()
        }
    }// Selector showing which flash mode is selected (on, off or auto)
    private val onAttachStateChangeListener = object : OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View?) {
            displayManager.registerDisplayListener(displayListener, null)
        }

        override fun onViewDetachedFromWindow(v: View?) {
            displayManager.unregisterDisplayListener(displayListener)
        }
    }
    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit

        @SuppressLint("UnsafeOptInUsageError")
        override fun onDisplayChanged(displayId: Int) {
            if (displayId == this@CameraXMKPreviewView.displayId) {
                preview.targetRotation = this@CameraXMKPreviewView.display.rotation
                imageCapture.targetRotation = this@CameraXMKPreviewView.display.rotation
                imageAnalyzer.targetRotation = this@CameraXMKPreviewView.display.rotation
            }
        }
    }
    private val onImageCaptureCallback = object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            Log.d(TAG, "onCaptureSuccess: ${image.format} ${image.width}x${image.height}")
            if (image.format == ImageFormat.YUV_420_888) {
                val bitmap = ImageConverter.yuv2Bitmap(image)
                cameraXMKListener?.onCaptureSuccess(bitmap)
            } else if (image.format == ImageFormat.JPEG) {
                val bitmap = ImageConverter.jpeg2Bitmap(image)
                Log.d(TAG, "onCaptureSuccess: ${bitmap.width}x${bitmap.height}")
                cameraXMKListener?.onCaptureSuccess(bitmap)
            }
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e(TAG, "onError: ${exception.message}")
            cameraXMKListener?.onCaptureFail()
            exception.printStackTrace()
        }
    }

    private var cameraXMKListener: ICameraXMKListener? = null
    private var lifecycleOwner: LifecycleOwner? = null
    private var imageLoader: ImageAnalysis.Analyzer? = null
    private var lensFacing = CameraSelector.DEFAULT_BACK_CAMERA// Selector showing which camera is selected (front or back)
    private var isOpenHdr = false// Selector showing is hdr enabled or not (will work, only if device's camera supports hdr on hardware level)
    private var selectedTimer = CameraXMKTimer.OFF

    init {
        initView()
    }

    fun initCamera(
        owner: LifecycleOwner,
        listener: ICameraXMKListener,
        facing: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    ) {
        lifecycleOwner = owner
        cameraXMKListener = listener
        lensFacing = facing
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
            } catch (e: InterruptedException) {
                cameraXMKListener?.onCameraStartFail(e.message.toString())
                Log.e(TAG, "startCamera: Error starting camera")
                return@addListener
            } catch (e: ExecutionException) {
                cameraXMKListener?.onCameraStartFail(e.message.toString())
                Log.e(TAG, "startCamera: Error starting camera")
                return@addListener
            }

            // The ratio for the output image and preview
            val aspectRatio = aspectRatio(previewView.width, previewView.height)
            // The display rotation
            val rotation = previewView.display.rotation

            val localCameraProvider = cameraProvider
                ?: throw IllegalStateException("Camera initialization failed.")

            // The Configuration of camera preview
            preview = Preview.Builder()
                .setTargetAspectRatio(aspectRatio) // set the camera aspect ratio
                .setTargetRotation(rotation) // set the camera rotation
                .build()

            // The Configuration of image capture
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY) // setting to have pictures with highest quality possible (may be slow)
                .setFlashMode(flashMode) // set capture flash
                .setTargetAspectRatio(aspectRatio) // set the capture aspect ratio
                .setTargetRotation(rotation) // set the capture rotation
                .build()

            checkForHdrExtensionAvailability()

            // The Configuration of image analyzing
            imageAnalyzer = ImageAnalysis.Builder()
                .setTargetAspectRatio(aspectRatio) // set the analyzer aspect ratio
                .setTargetRotation(rotation) // set the analyzer rotation
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) // in our analysis, we care about the latest image
                .build()
                .also { setLuminosityAnalyzer(it) }

            // Unbind the use-cases before rebinding them
            localCameraProvider.unbindAll()
            // Bind all use cases to the camera with lifecycle
            bindToLifecycle(localCameraProvider, previewView)
        }, ContextCompat.getMainExecutor(context))
    }

    fun changeFlash(@ImageCapture.FlashMode flash: Int) {
        flashMode = flash
        imageCapture.flashMode = flashMode
    }

    fun changeCountDownTimer(timer: CameraXMKTimer) {
        selectedTimer = timer
    }

    fun changeCameraFacing(cameraSelector: CameraSelector) {
        if (lensFacing != cameraSelector) {
            lensFacing = if (lensFacing == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
            startCamera()
        }
    }

    fun changeHDRStatus(isOpen: Boolean) {
        if (isOpenHdr != isOpen) {
            isOpenHdr = !isOpenHdr
            startCamera()
        }
    }

    fun takePicture() = GlobalScope.launch(Dispatchers.Main) {
        // Show a timer based on user selection
        when (selectedTimer) {
            CameraXMKTimer.S3 -> for (i in 3 downTo 1) {
                delay(1000)
            }
            CameraXMKTimer.S10 -> for (i in 10 downTo 1) {
                delay(1000)
            }
            else -> {
            }
        }
        captureImage()
    }

    private fun captureImage() {
        val localImageCapture = imageCapture
        localImageCapture.takePicture(
            ContextCompat.getMainExecutor(context), // the executor, on which the task will run
            onImageCaptureCallback
        )
    }

    private fun checkForHdrExtensionAvailability() {
        // Create a Vendor Extension for HDR
        val extensionsManagerFuture = ExtensionsManager.getInstanceAsync(context, cameraProvider ?: return)
        extensionsManagerFuture.addListener(
            {
                val extensionsManager = extensionsManagerFuture.get() ?: return@addListener

                val isAvailable = extensionsManager.isExtensionAvailable(lensFacing, ExtensionMode.HDR)

                // check for any extension availability
                println("AUTO " + extensionsManager.isExtensionAvailable(lensFacing, ExtensionMode.AUTO))
                println("HDR " + extensionsManager.isExtensionAvailable(lensFacing, ExtensionMode.HDR))
                println("FACE RETOUCH " + extensionsManager.isExtensionAvailable(lensFacing, ExtensionMode.FACE_RETOUCH))
                println("BOKEH " + extensionsManager.isExtensionAvailable(lensFacing, ExtensionMode.BOKEH))
                println("NIGHT " + extensionsManager.isExtensionAvailable(lensFacing, ExtensionMode.NIGHT))
                println("NONE " + extensionsManager.isExtensionAvailable(lensFacing, ExtensionMode.NONE))

                // Check if the extension is available on the device
                if (!isAvailable) {
                    cameraXMKListener?.onCheckCameraHDR(false)
                } else if (isOpenHdr) {
                    // If yes, turn on if the HDR is turned on by the user
                    cameraXMKListener?.onCameraHDROpen()
                    hdrCameraSelector =
                        extensionsManager.getExtensionEnabledCameraSelector(lensFacing, ExtensionMode.HDR)
                }
            }, ContextCompat.getMainExecutor(context)
        )
    }

    private fun bindToLifecycle(localCameraProvider: ProcessCameraProvider, previewView: PreviewView) {
        try {
            localCameraProvider.bindToLifecycle(
                lifecycleOwner!!, // current lifecycle owner
                hdrCameraSelector ?: lensFacing, // either front or back facing
                preview, // camera preview use case
                imageCapture, // image capture use case
                imageAnalyzer, // image analyzer use case
            ).apply {
                // Init camera exposure control
                cameraInfo.exposureState.run {
                    val lower = exposureCompensationRange.lower
                    val upper = exposureCompensationRange.upper

                    slider.run {
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
            preview.setSurfaceProvider(previewView.surfaceProvider)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to bind use cases", e)
        }
    }

    private fun setLuminosityAnalyzer(imageAnalysis: ImageAnalysis) {
        // Use a worker thread for image analysis to prevent glitches
        analyzerThread = HandlerThread("LuminosityAnalysis").apply { start() }
        imageLoader?.let {
            imageAnalysis.setAnalyzer(ThreadExecutor(Handler(analyzerThread.looper)), it)
        }
        imageAnalysis.setAnalyzer(
            ThreadExecutor(Handler(analyzerThread.looper)),
            LuminosityAnalyzer()
        )
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_cameraxmk_preview, this)
        previewView = findViewById(R.id.cameraxmk_preview)
        sliderContainer = findViewById(R.id.cameraxmk_container)
        slider = findViewById(R.id.cameraxmk_slider)
        previewView.addOnAttachStateChangeListener(onAttachStateChangeListener)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        displayId = previewView.display.displayId
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
        analyzerThread.interrupt()
        displayManager.unregisterDisplayListener(displayListener)
    }
}