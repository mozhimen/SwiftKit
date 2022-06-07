package com.mozhimen.abilityk.cameraxk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.hardware.display.DisplayManager
import android.os.Handler
import android.os.HandlerThread
import android.util.AttributeSet
import android.util.Log
import com.mozhimen.abilityk.R
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
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
import com.mozhimen.basick.logk.LogK
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
class CameraXKPreviewView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val TAG = "CameraXKPreviewView>>>>>"

    private var _displayId = -1
    private lateinit var _analyzerThread: HandlerThread
    private lateinit var _previewView: PreviewView
    private lateinit var _sliderContainer: FrameLayout
    private lateinit var _slider: Slider
    private lateinit var _preview: Preview



    // An instance for display manager to get display change callbacks
    private val _displayManager by lazy { context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager }

    private var _hdrCameraSelector: CameraSelector? = null

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

        override fun onError(e: ImageCaptureException) {
            LogK.et(TAG, "OnImageCapturedCallback onError ImageCaptureException ${e.message}")
            _cameraXKCaptureListener?.onCaptureFail()
            e.printStackTrace()
        }
    }

    private var _cameraXKAnalyzer: ImageAnalysis.Analyzer? = null



    private var _isOpenHdr =
        false// Selector showing is hdr enabled or not (will work, only if device's camera supports hdr on hardware level)
    private var _selectedTimer = CameraXKTimer.OFF

    init {
        initView()
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
            LogK.et(TAG, "bindToLifecycle Exception ${e.message}")
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
        LayoutInflater.from(context).inflate(R.layout.cameraxk_preview_layout, this)
        _previewView = findViewById(R.id.cameraxk_preview)
        _sliderContainer = findViewById(R.id.cameraxk_container)
        _slider = findViewById(R.id.cameraxk_slider)
        _previewView.addOnAttachStateChangeListener(_onAttachStateChangeListener)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        _displayId = _previewView.display.displayId
    }



    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _analyzerThread.interrupt()
        _displayManager.unregisterDisplayListener(_displayListener)
    }
}