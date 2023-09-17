package com.mozhimen.componentk.camerak.camerax.helpers

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.camera.camera2.internal.Camera2CameraInfoImpl
import androidx.camera.core.*
import androidx.camera.extensions.ExtensionMode
import androidx.camera.extensions.ExtensionsManager
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.slider.Slider
import com.mozhimen.basick.elemk.java.util.bases.BaseHandlerExecutor
import com.mozhimen.basick.lintk.optin.OptInFieldCall_Close
import com.mozhimen.basick.utilk.android.util.dt
import com.mozhimen.basick.utilk.bases.BaseUtilK
import com.mozhimen.basick.utilk.android.util.et
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainScope
import com.mozhimen.componentk.camerak.camerax.CameraKXLayout
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXCaptureMode
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFacing
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFormat
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXRotation
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKX
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXCaptureListener
import com.mozhimen.componentk.camerak.camerax.commons.ICameraXKFrameListener
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXListener
import com.mozhimen.componentk.camerak.camerax.cons.CAspectRatio
import com.mozhimen.componentk.camerak.camerax.cons.ECameraKXTimer
import com.mozhimen.componentk.camerak.camerax.mos.MCameraKXConfig
import com.mozhimen.componentk.camerak.camerax.temps.OtherCameraFilter
import kotlinx.coroutines.delay
import java.util.concurrent.ExecutionException
import kotlin.properties.Delegates

/**
 * @ClassName CameraXKDelegate
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/1/3 1:17
 * @Version 1.0
 */
class CameraKXDelegate(private val _cameraKXLayout: CameraKXLayout) : ICameraKX, BaseUtilK() {

    private var _cameraXKListener: ICameraKXListener? = null
    private var _cameraXKCaptureListener: ICameraKXCaptureListener? = null
    private var _cameraXKFrameListener: ICameraXKFrameListener? = null
    private var _cameraXKTimer = ECameraKXTimer.OFF
    private var _imageFormatFrame: Int = ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888
    private var _imageCaptureMode = ACameraKXCaptureMode.MAXIMIZE_QUALITY
    private var _isCameraSingle: Boolean = false
    private var _isCameraOpen: Boolean = false

    //////////////////////////////////////////////////////////////////////////////////////////////

    private var _preview: Preview? = null
    private var _cameraSelectorFacing: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA//显示相机选择的选择器(正面或背面) Selector showing which camera is selected (front or back)
    private var _cameraSelectorHdr: CameraSelector? = null
    private var _zoomState: LiveData<ZoomState>? = null

    private var _imageCapture: ImageCapture? = null
    private var _imageCaptureBitmap: Bitmap? = null
    private var _imageAnalysis: ImageAnalysis? = null
    private var _handlerThreadAnalyzer: HandlerThread? = null
    private lateinit var _lifecycleOwner: LifecycleOwner

    //////////////////////////////////////////////////////////////////////////////////////////////

    @OptIn(OptInFieldCall_Close::class)
    private val _imageCaptureCallback = object : ImageCapture.OnImageCapturedCallback() {
        @SuppressLint("UnsafeOptInUsageError")
        override fun onCaptureSuccess(image: ImageProxy) {
            "onCaptureSuccess: ${image.format} ${image.width}x${image.height}".dt(TAG)
            when (image.format) {
                ImageFormat.YUV_420_888 -> _imageCaptureBitmap = image.yuv420888ImageProxy2JpegBitmap().also { "onCaptureSuccess: YUV_420_888".dt(TAG) }
                ImageFormat.JPEG -> _imageCaptureBitmap = image.jpegImageProxy2JpegBitmap().also { "onCaptureSuccess: JPEG".dt(TAG) }
                ImageFormat.FLEX_RGBA_8888 -> _imageCaptureBitmap = image.rgba8888ImageProxy2Rgba8888Bitmap().also { "onCaptureSuccess: FLEX_RGBA_8888".dt(TAG) }
            }
            _imageCaptureBitmap?.let { _cameraXKCaptureListener?.onCaptureSuccess(it, image.imageInfo.rotationDegrees) }
            image.close()
        }

        override fun onError(e: ImageCaptureException) {
            "OnImageCapturedCallback onError ImageCaptureException ${e.message}".et(TAG)
            _cameraXKCaptureListener?.onCaptureFail()
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    private val _imageAnalysisAnalyzer: ImageAnalysis.Analyzer = ImageAnalysis.Analyzer { imageProxy ->
        _cameraXKFrameListener?.invoke(imageProxy)
    }

    private val _zoomRatioObserver: Observer<ZoomState> = object : Observer<ZoomState> {
        override fun onChanged(t: ZoomState?) {
            t?.let { zoomRatio = it.zoomRatio }
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    internal var camera: Camera? by Delegates.observable(null) { _, old, new ->
        if (old == new || new == null) return@observable
        _zoomState = camera!!.cameraInfo.zoomState.apply {
            maxZoomRatio = value!!.maxZoomRatio
            minZoomRatio = value!!.minZoomRatio
            zoomRatio = value!!.zoomRatio
            removeObserver(_zoomRatioObserver)
            observe(_lifecycleOwner, _zoomRatioObserver)
        }
    }

    internal val cameraControl: CameraControl?
        get() = camera?.cameraControl

    internal var maxZoomRatio = 0f
    internal var minZoomRatio = 0f
    internal var zoomRatio = 0f

    internal var isCameraOpening: Boolean by Delegates.observable(false) { _, old, new ->//选择器显示是否启用hdr(只有当设备的摄像头在硬件层面支持hdr时才会工作) Selector showing is hdr enabled or not (will work, only if device's camera supports hdr on hardware level)
        if (old == new) return@observable
        _isCameraOpen = !new
    }

    internal var isOpenHdr: Boolean by Delegates.observable(false) { _, old, new ->//选择器显示是否启用hdr(只有当设备的摄像头在硬件层面支持hdr时才会工作) Selector showing is hdr enabled or not (will work, only if device's camera supports hdr on hardware level)
        if (old == new) return@observable
        restartCameraKX()
    }

    internal var lensFacing: Int by Delegates.observable(CameraSelector.LENS_FACING_BACK) { _, old, new ->
        if (old == new) return@observable
        _cameraSelectorFacing = when (new) {
            ACameraKXFacing.FRONT -> CameraSelector.DEFAULT_FRONT_CAMERA
            else -> CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    internal var flashMode: Int by Delegates.observable(ImageCapture.FLASH_MODE_OFF) { _, old, new ->//选择器显示所选择的闪光模式(开、关或自动) Selector showing which flash mode is selected (on, off or auto)
        if (old == new) return@observable
        _imageCapture?.flashMode = new
        when (new) {
            ImageCapture.FLASH_MODE_ON -> _cameraXKListener?.onCameraFlashOn()
            ImageCapture.FLASH_MODE_AUTO -> _cameraXKListener?.onCameraFlashAuto()
            else -> _cameraXKListener?.onCameraFlashOff()
        }
    }

    internal var rotation: Int by Delegates.observable(ACameraKXRotation.ROTATION_90) { _, old, new ->
        if (old == new) return@observable
        Log.d(TAG, "rotation: $new")
        _preview?.targetRotation = new
    }

    internal var aspectRatio: Int = CAspectRatio.RATIO_16_9

    //////////////////////////////////////////////////////////////////////////////////////////////

    //region open fun
    override fun initCameraKX(owner: LifecycleOwner, config: MCameraKXConfig) {
        _lifecycleOwner = owner
        _imageFormatFrame = when (config.format) {
            ACameraKXFormat.RGBA_8888 -> ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
            else -> ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888
        }
        _imageCaptureMode = config.captureMode
        lensFacing = config.facing
    }

    override fun initCameraKX(owner: LifecycleOwner) {
        initCameraKX(owner, MCameraKXConfig())
    }

    @SuppressLint("NewApi")
    override fun restartCameraKX() {
        isCameraOpening = true
        try {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(_context)
            cameraProviderFuture.addListener(
                {
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //获取相机信息
                    val cameraProvider: ProcessCameraProvider? = cameraProviderFuture.get()

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //预览
                    _preview = Preview.Builder()
                        .setTargetAspectRatio(this.aspectRatio) // set the camera aspect ratio
                        .setTargetRotation(this.rotation) // set the camera rotation
                        .build().apply {
                            setSurfaceProvider(_cameraKXLayout.previewView!!.surfaceProvider)// Attach the viewfinder's surface provider to preview use case
                        }//摄像头预览的配置 The Configuration of camera preview

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //拍照
                    _imageCapture = ImageCapture.Builder()//图像捕获的配置 The Configuration of image capture
                        .setTargetAspectRatio(this.aspectRatio) // set the capture aspect ratio
                        .setTargetRotation(this.rotation) // set the capture rotation
                        .setCaptureMode(_imageCaptureMode) // setting to have pictures with highest quality possible (may be slow)
                        .setFlashMode(this.flashMode) // set capture flash
                        .build()

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //Hdr
                    //checkForHdrExtensionAvailability(cameraProvider)

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //回调帧 //图像分析的配置 The Configuration of image analyzing
                    _imageAnalysis = ImageAnalysis.Builder()
                        .setTargetAspectRatio(this.aspectRatio) // set the analyzer aspect ratio
                        .setTargetRotation(this.rotation) // set the analyzer rotation
                        .setOutputImageRotationEnabled(true)
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) // in our analysis, we care about the latest image
                        .setOutputImageFormat(_imageFormatFrame.also { Log.d(TAG, "restartCameraKX: _imageFormatFrame $_imageFormatFrame") })
                        .build()
                        .also {
                            setCameraXKAnalyzer(it)
                        }

                    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //绑定生命周期
                    val processCameraProvider: ProcessCameraProvider = cameraProvider ?: throw IllegalStateException("Camera initialization failed.")
                    processCameraProvider.unbindAll()// Unbind the use-cases before rebinding them
                    bindToLifecycle(processCameraProvider, _preview!!, _cameraKXLayout.previewView!!, _cameraKXLayout.slider!!)// Bind all use cases to the camera with lifecycle
                },
                ContextCompat.getMainExecutor(_context)
            )
            isCameraOpening = false
        } catch (e: InterruptedException) {
            _cameraXKListener?.onCameraStartFail(e.message ?: "").also { "startCamera InterruptedException ${e.message ?: ""}".et(TAG) }
        } catch (e: ExecutionException) {
            _cameraXKListener?.onCameraStartFail(e.message ?: "").also { "startCamera ExecutionException ${e.message ?: ""}".et(TAG) }
        } catch (e: Exception) {
            e.printStackTrace()
            e.message?.et(TAG)
        }
    }

    override fun setCameraXListener(listener: ICameraKXListener) {
        _cameraXKListener = listener
    }

    override fun setCameraXCaptureListener(listener: ICameraKXCaptureListener) {
        _cameraXKCaptureListener = listener
    }

    override fun setCameraXFrameListener(listener: ICameraXKFrameListener) {
        _cameraXKFrameListener = listener
    }

    override fun changeHdr(isOpen: Boolean) {
        if (isOpenHdr == isOpen) return
        this.isOpenHdr = isOpen
    }

    override fun changeFlash(@ImageCapture.FlashMode flashMode: Int) {
        if (this.flashMode == flashMode) return
        this.flashMode = flashMode
    }

    override fun changeCountDownTimer(timer: ECameraKXTimer) {
        if (_cameraXKTimer == timer) return
        _cameraXKTimer = timer
    }

    override fun changeRotation(rotation: Int) {
        if (this.rotation == rotation) return
        this.rotation = rotation
    }

    override fun changeFacing(@ACameraKXFacing facing: Int) {
        if (facing == lensFacing || _isCameraSingle) return
        this.lensFacing = facing
        restartCameraKX()
    }

    override fun startCapture() {
        _lifecycleOwner.runOnMainScope {
            when (_cameraXKTimer) {// Show a timer based on user selection
                ECameraKXTimer.S3 -> for (i in 3 downTo 1) delay(1000)
                ECameraKXTimer.S10 -> for (i in 10 downTo 1) delay(1000)
                else -> {}
            }
            _imageCapture?.takePicture(ContextCompat.getMainExecutor(_context), _imageCaptureCallback)// the executor, on which the task will run)
        }
    }

    override fun isCameraKXStart(): Boolean {
        return _isCameraOpen
    }

    override fun stopCameraKX() {
        if (_handlerThreadAnalyzer != null && !_handlerThreadAnalyzer!!.isInterrupted) {
            _handlerThreadAnalyzer?.interrupt()
            _handlerThreadAnalyzer = null
        }
        _zoomState?.removeObserver(_zoomRatioObserver)
        _zoomState = null
        _isCameraOpen = false
    }
    //endregion

    @SuppressLint("RestrictedApi")
    @Throws(Exception::class)
    private fun bindToLifecycle(localCameraProvider: ProcessCameraProvider, preview: Preview, previewView: PreviewView, slider: Slider) {
        if (localCameraProvider.availableCameraInfos.size == 1) {
            Log.d(TAG, "bindToLifecycle: availCamera size = localCameraProvider.availableCameraInfos.size")
            _isCameraSingle = true
            val cameraInfo: Camera2CameraInfoImpl = (localCameraProvider.availableCameraInfos[0] as Camera2CameraInfoImpl).also {
                Log.d(
                    TAG,
                    "bindToLifecycle: cameraInfo $it _lensFacing ${it.cameraSelector.lensFacing} id ${it.cameraId}"
                )
            }
            _cameraSelectorFacing.cameraFilterSet.clear()
            _cameraSelectorFacing.cameraFilterSet.add(OtherCameraFilter(cameraInfo.cameraId))
        }
        camera = localCameraProvider.bindToLifecycle(
            _lifecycleOwner, // current lifecycle owner
            /*_hdrCameraSelector ?: */
            _cameraSelectorFacing, // either front or back facing
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
    }

    /**
     * 为HDR创建供应商扩展
     * Create a Vendor Extension for HDR
     */
    private fun checkForHdrExtensionAvailability(cameraProvider: CameraProvider) {
        val extensionsManagerFuture = ExtensionsManager.getInstanceAsync(_context, cameraProvider)
        extensionsManagerFuture.addListener(
            {
                val extensionsManager = extensionsManagerFuture.get() ?: return@addListener
                val isAvailable = extensionsManager.isExtensionAvailable(_cameraSelectorFacing, ExtensionMode.HDR)

                //检查是否有扩展可用 check for any extension availability
                Log.d(TAG, "checkForHdrExtensionAvailability: AUTO " + extensionsManager.isExtensionAvailable(_cameraSelectorFacing, ExtensionMode.AUTO))
                Log.d(TAG, "checkForHdrExtensionAvailability: HDR " + extensionsManager.isExtensionAvailable(_cameraSelectorFacing, ExtensionMode.HDR))
                Log.d(TAG, "checkForHdrExtensionAvailability: FACE RETOUCH " + extensionsManager.isExtensionAvailable(_cameraSelectorFacing, ExtensionMode.FACE_RETOUCH))
                Log.d(TAG, "checkForHdrExtensionAvailability: BOKEH " + extensionsManager.isExtensionAvailable(_cameraSelectorFacing, ExtensionMode.BOKEH))
                Log.d(TAG, "checkForHdrExtensionAvailability: NIGHT " + extensionsManager.isExtensionAvailable(_cameraSelectorFacing, ExtensionMode.NIGHT))
                Log.d(TAG, "checkForHdrExtensionAvailability: NONE " + extensionsManager.isExtensionAvailable(_cameraSelectorFacing, ExtensionMode.NONE))

                //检查分机是否在设备上可用 Check if the extension is available on the device
                if (!isAvailable) {
                    _cameraXKListener?.onCameraHDRCheck(false)
                } else if (isOpenHdr) {
                    //如果是，如果HDR是由用户打开的，则打开 If yes, turn on if the HDR is turned on by the user
                    _cameraXKListener?.onCameraHDROpen()
                    _cameraSelectorHdr = extensionsManager.getExtensionEnabledCameraSelector(_cameraSelectorFacing, ExtensionMode.HDR)
                }
            }, ContextCompat.getMainExecutor(_context)
        )
    }

    private fun setCameraXKAnalyzer(imageAnalysis: ImageAnalysis) {
        //使用工作线程进行图像分析，以防止故障 Use a worker thread for image analysis to prevent glitches
        _cameraXKFrameListener?.let {
            stopCameraKX()
            _handlerThreadAnalyzer = HandlerThread("CameraXKLuminosityAnalysis").apply { start() }
            imageAnalysis.setAnalyzer(BaseHandlerExecutor(Handler(_handlerThreadAnalyzer!!.looper)), _imageAnalysisAnalyzer)
        }
    }
}