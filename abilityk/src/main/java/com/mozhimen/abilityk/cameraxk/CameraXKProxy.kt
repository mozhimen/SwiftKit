package com.mozhimen.abilityk.cameraxk

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ImageFormat
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.camera.core.*
import androidx.camera.extensions.ExtensionMode
import androidx.camera.extensions.ExtensionsManager
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.slider.Slider
import com.mozhimen.abilityk.cameraxk.commons.ICameraXKAction
import com.mozhimen.abilityk.cameraxk.commons.ICameraXKCaptureListener
import com.mozhimen.abilityk.cameraxk.commons.ICameraXKListener
import com.mozhimen.abilityk.cameraxk.helpers.ImageConverter
import com.mozhimen.abilityk.cameraxk.helpers.ThreadExecutor
import com.mozhimen.abilityk.cameraxk.mos.CameraXKRotation
import com.mozhimen.abilityk.cameraxk.mos.CameraXKTimer
import com.mozhimen.basick.logk.LogK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException
import kotlin.properties.Delegates

/**
 * @ClassName CameraXKProxy
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 1:17
 * @Version 1.0
 */
class CameraXKProxy(private val _context: Context) : ICameraXKAction {
    companion object {
        private const val TAG = "CameraXKProxy>>>>>"
    }

    private var _cameraXKListener: ICameraXKListener? = null
    private var _cameraXKCaptureListener: ICameraXKCaptureListener? = null
    private var _cameraXKAnalyzer: ImageAnalysis.Analyzer? = null

    private var _hdrCameraSelector: CameraSelector? = null
    private var _imageCapture: ImageCapture? = null
    private var _imageAnalysis: ImageAnalysis? = null

    private var _selectedTimer = CameraXKTimer.OFF
    internal var aspectRatio: Int = AspectRatio.RATIO_16_9
    internal var rotation = CameraXKRotation.ROTATION_90

    private lateinit var _lifecycleOwner: LifecycleOwner
    private lateinit var _analyzerThread: HandlerThread
    internal lateinit var slider: Slider
    internal lateinit var previewView: PreviewView
    internal lateinit var preview: Preview

    /**
     * 选择器显示所选择的闪光模式(开、关或自动)
     * Selector showing which flash mode is selected (on, off or auto)
     */
    private var _flashMode by Delegates.observable(ImageCapture.FLASH_MODE_OFF) { _, _, new ->
        when (new) {
            ImageCapture.FLASH_MODE_ON -> _cameraXKListener?.onCameraFlashOn()
            ImageCapture.FLASH_MODE_AUTO -> _cameraXKListener?.onCameraFlashAuto()
            else -> _cameraXKListener?.onCameraFlashOff()
        }
    }

    /**
     * 显示相机选择的选择器(正面或背面)
     * Selector showing which camera is selected (front or back)
     */
    private var _lensFacing = CameraSelector.DEFAULT_BACK_CAMERA

    /**
     * 选择器显示是否启用hdr(只有当设备的摄像头在硬件层面支持hdr时才会工作)
     * Selector showing is hdr enabled or not (will work, only if device's camera supports hdr on hardware level)
     */
    private var _isOpenHdr = false

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

    //region open fun
    override fun setCameraXKListener(listener: ICameraXKListener) {
        this._cameraXKListener = listener
    }

    override fun setCameraXKCaptureListener(listener: ICameraXKCaptureListener) {
        this._cameraXKCaptureListener = listener
    }

    fun initCamera(
        owner: LifecycleOwner,
        facing: CameraSelector
    ) {
        this._lifecycleOwner = owner
        this._lensFacing = facing
    }

    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(_context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider?
            try {
                cameraProvider = cameraProviderFuture.get()
            } catch (e: InterruptedException) {
                _cameraXKListener?.onCameraStartFail(e.message ?: "")
                LogK.et(TAG, "startCamera InterruptedException ${e.message ?: ""}")
                return@addListener
            } catch (e: ExecutionException) {
                _cameraXKListener?.onCameraStartFail(e.message ?: "")
                LogK.et(TAG, "startCamera ExecutionException ${e.message ?: ""}")
                return@addListener
            }

            val localCameraProvider: ProcessCameraProvider = cameraProvider
                ?: throw IllegalStateException("Camera initialization failed.")

            //图像捕获的配置 The Configuration of image capture
            _imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY) // setting to have pictures with highest quality possible (may be slow)
                .setFlashMode(_flashMode) // set capture flash
                .setTargetAspectRatio(aspectRatio) // set the capture aspect ratio
                .setTargetRotation(rotation) // set the capture rotation
                .build()

            //Hdr
            checkForHdrExtensionAvailability(cameraProvider)

            //图像分析的配置 The Configuration of image analyzing
            _imageAnalysis = ImageAnalysis.Builder()
                .setTargetAspectRatio(aspectRatio) // set the analyzer aspect ratio
                .setTargetRotation(rotation) // set the analyzer rotation
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) // in our analysis, we care about the latest image
                .build()
                .also { setCameraXKAnalyzer(it) }

            // Unbind the use-cases before rebinding them
            localCameraProvider.unbindAll()
            // Bind all use cases to the camera with lifecycle
            bindToLifecycle(localCameraProvider, preview, previewView, slider)
        }, ContextCompat.getMainExecutor(_context))
    }

    override fun setImageAnalyzer(analyzer: ImageAnalysis.Analyzer) {
        this._cameraXKAnalyzer = analyzer
    }

    override fun changeHdr(isOpen: Boolean) {
        if (_isOpenHdr != isOpen) {
            _isOpenHdr = !_isOpenHdr
            startCamera()
        }
    }

    override fun changeFlash(@ImageCapture.FlashMode flashMode: Int) {
        _flashMode = flashMode
        _imageCapture?.flashMode = _flashMode
    }

    override fun changeCountDownTimer(timer: CameraXKTimer) {
        _selectedTimer = timer
    }

    override fun changeCameraFacing(cameraSelector: CameraSelector) {
        if (_lensFacing != cameraSelector) {
            _lensFacing = if (_lensFacing == CameraSelector.DEFAULT_BACK_CAMERA) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else {
                CameraSelector.DEFAULT_BACK_CAMERA
            }
            startCamera()
        }
    }

    override fun takePicture() {
        _lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
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
    }

    fun onFrameFinished() {
        if (!_analyzerThread.isInterrupted) _analyzerThread.interrupt()
    }
    //endregion

    private fun bindToLifecycle(localCameraProvider: ProcessCameraProvider, preview: Preview, previewView: PreviewView, slider: Slider) {
        try {
            localCameraProvider.bindToLifecycle(
                _lifecycleOwner, // current lifecycle owner
                _hdrCameraSelector ?: _lensFacing, // either front or back facing
                preview, // camera preview use case
                _imageCapture!!, // image capture use case
                _imageAnalysis!!, // image analyzer use case
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
            LogK.et(TAG, "bindToLifecycle Exception ${e.message ?: ""}")
        }
    }

    private fun captureImage() {
        val localImageCapture = _imageCapture ?: return
        localImageCapture.takePicture(
            ContextCompat.getMainExecutor(_context), // the executor, on which the task will run
            _onImageCaptureCallback
        )
    }

    /**
     * 为HDR创建供应商扩展
     * Create a Vendor Extension for HDR
     * @param cameraProvider CameraProvider
     */
    private fun checkForHdrExtensionAvailability(cameraProvider: CameraProvider) {
        //为HDR创建供应商扩展 Create a Vendor Extension for HDR
        val extensionsManagerFuture = ExtensionsManager.getInstanceAsync(_context, cameraProvider)
        extensionsManagerFuture.addListener(
            {
                val extensionsManager = extensionsManagerFuture.get() ?: return@addListener
                val isAvailable = extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.HDR)

                //检查是否有扩展可用 check for any extension availability
                Log.d(TAG, "checkForHdrExtensionAvailability: AUTO " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.AUTO))
                Log.d(TAG, "checkForHdrExtensionAvailability: HDR " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.HDR))
                Log.d(
                    TAG,
                    "checkForHdrExtensionAvailability: FACE RETOUCH " + extensionsManager.isExtensionAvailable(
                        _lensFacing,
                        ExtensionMode.FACE_RETOUCH
                    )
                )
                Log.d(TAG, "checkForHdrExtensionAvailability: BOKEH " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.BOKEH))
                Log.d(TAG, "checkForHdrExtensionAvailability: NIGHT " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.NIGHT))
                Log.d(TAG, "checkForHdrExtensionAvailability: NONE " + extensionsManager.isExtensionAvailable(_lensFacing, ExtensionMode.NONE))

                //检查分机是否在设备上可用 Check if the extension is available on the device
                if (!isAvailable) {
                    _cameraXKListener?.onCameraHDRCheck(false)
                } else if (_isOpenHdr) {
                    //如果是，如果HDR是由用户打开的，则打开 If yes, turn on if the HDR is turned on by the user
                    _cameraXKListener?.onCameraHDROpen()
                    _hdrCameraSelector = extensionsManager.getExtensionEnabledCameraSelector(_lensFacing, ExtensionMode.HDR)
                }
            }, ContextCompat.getMainExecutor(_context)
        )
    }

    private fun setCameraXKAnalyzer(imageAnalysis: ImageAnalysis) {
        //使用工作线程进行图像分析，以防止故障 Use a worker thread for image analysis to prevent glitches
        _cameraXKAnalyzer?.let {
            _analyzerThread = HandlerThread("CameraXKLuminosityAnalysis").apply { start() }
            imageAnalysis.setAnalyzer(ThreadExecutor(Handler(_analyzerThread.looper)), it)
        }
    }
}