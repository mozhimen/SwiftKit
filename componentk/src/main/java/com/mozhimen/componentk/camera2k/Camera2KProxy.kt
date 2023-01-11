package com.mozhimen.componentk.camera2k

import android.app.Activity
import android.content.Context
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.hardware.camera2.params.MeteringRectangle
import android.media.ImageReader
import android.os.Handler
import android.os.HandlerThread
import android.util.Size
import android.view.OrientationEventListener
import android.view.Surface
import android.view.SurfaceHolder
import androidx.lifecycle.LifecycleOwner
import com.mozhimen.basick.permissionk.cons.CPermission
import com.mozhimen.basick.permissionk.PermissionK
import com.mozhimen.basick.permissionk.annors.APermissionRequire
import com.mozhimen.basick.permissionk.cons.CUseFeature
import com.mozhimen.basick.utilk.exts.showToastOnMain
import com.mozhimen.basick.utilk.UtilKDisplay
import com.mozhimen.componentk.camera2k.helpers.GLSurfaceRenderer
import com.mozhimen.underlayk.logk.LogK
import java.util.*
import kotlin.math.abs

/**
 * @ClassName Camera2KProxy
 * @Description Need Camera Permission
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/15 17:56
 * @Version 1.0
 */
@APermissionRequire(CPermission.CAMERA,CUseFeature.CAMERA, CUseFeature.CAMERA_AUTOFOCUS)
class Camera2KProxy(activity: Activity, renderer: GLSurfaceRenderer) {

    private val TAG = "Camera2KProxy>>>>>"

    private var _activity: Activity = activity
    private var _renderer: GLSurfaceRenderer = renderer

    private var _cameraManager: CameraManager = _activity.getSystemService(Context.CAMERA_SERVICE) as CameraManager// 相机管理者
    private var _cameraDevice: CameraDevice? = null // 相机对象
    private var _cameraCharacteristics: CameraCharacteristics? = null// 相机属性
    private var _cameraCaptureSession: CameraCaptureSession? = null
    private var _cameraCaptureRequest: CaptureRequest? = null

    private var _previewSize: Size? = null// 预览大小
    private var _previewSurface: Surface? = null
    private var _previewSurfaceTexture: SurfaceTexture? = null
    private var _previewRequestBuilder: CaptureRequest.Builder? = null// 相机预览请求的构造器

    private var _backgroundHandler: Handler? = null
    private var _backgroundThread: HandlerThread? = null

    private var _imageReader: ImageReader? = null
    private var _imageAvailableListener: ImageReader.OnImageAvailableListener? = null

    private val _orientationEventListener: OrientationEventListener = object : OrientationEventListener(_activity) {
        override fun onOrientationChanged(orientation: Int) {
            _cameraOrientation = orientation
        }
    }

    private var _cameraRotate = 0
    private var _cameraOrientation = 0 // 设备方向，由相机传感器获取
    private var _cameraZoom = 1 // 缩放
    private var _cameraCapture = false
    private var _cameraId: Int = CameraCharacteristics.LENS_FACING_FRONT // 要打开的摄像头ID

    //打开摄像头的回调
    private val _cameraStateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            initPreview(camera.also { _cameraDevice = camera })
        }

        override fun onDisconnected(camera: CameraDevice) {
            releaseCamera()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            releaseCamera()
        }
    }

    private val _captureCallback: CameraCaptureSession.CaptureCallback = object : CameraCaptureSession.CaptureCallback() {

        override fun onCaptureProgressed(
            session: CameraCaptureSession,
            request: CaptureRequest,
            partialResult: CaptureResult
        ) {
            process(partialResult)
        }

        override fun onCaptureCompleted(
            session: CameraCaptureSession,
            request: CaptureRequest,
            result: TotalCaptureResult
        ) {
            process(result)
        }

        private fun process(result: CaptureResult) {
            val state = result.get(CaptureResult.CONTROL_AF_STATE) ?: return
            if (state == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED || state == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED) {
                _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL)
                _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.FLASH_MODE_OFF)
                startPreview()
            }
        }
    }

    /*private class OnImageAvailableListenerImpl : ImageReader.OnImageAvailableListener {
        private val lock = ReentrantLock()
        override fun onImageAvailable(reader: ImageReader) {
            val image = reader.acquireNextImage()
            if (image.format == ImageFormat.YUV_420_888) {
                lock.lock()
                lock.unlock()
            }
            image.close()
        }
    }*/

    fun setImageAvailableListener(listener: ImageReader.OnImageAvailableListener) {
        this._imageAvailableListener = listener
    }

    @Throws(Exception::class)
    fun openCamera(previewWidth: Int, previewHeight: Int) {
        require(PermissionK.checkPermission(CPermission.CAMERA)) { "$TAG need camera permission" }
        startBackgroundThread() // 对应 releaseCamera() 方法中的 stopBackgroundThread()
        try {
            _orientationEventListener.enable()
            _cameraCharacteristics = _cameraManager.getCameraCharacteristics(_cameraId.toString())
            val map = _cameraCharacteristics!!.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            val largest = Collections.max(listOf(*map!!.getOutputSizes(ImageFormat.YUV_420_888)), CompareSizesByArea())// 拍照大小，选择能支持的一个最大的图片大小
            _previewSize =
                choosePreviewSize(
                    map.getOutputSizes(SurfaceTexture::class.java),
                    previewWidth,
                    previewHeight,
                    largest
                )// 预览大小，根据上面选择的拍照图片的长宽比，选择一个和控件长宽差不多的大小
            _renderer.setPreviewSize(_previewSize!!.height, _previewSize!!.width)
            _cameraManager.openCamera(_cameraId.toString(), _cameraStateCallback, _backgroundHandler)// 打开摄像头
        } catch (e: CameraAccessException) {
            LogK.et(TAG, "openCamera: fail CameraAccessException ${e.message}")
            e.printStackTrace()
            "请检查摄像头".showToastOnMain()
        } catch (e: IllegalArgumentException) {
            LogK.et(TAG, "openCamera: fail IllegalArgumentException ${e.message}")
            e.printStackTrace()
            "请检查摄像头".showToastOnMain()
        }
    }

    fun startPreview() {
        if (_cameraCaptureSession == null || _previewRequestBuilder == null) return
        try {
            // 开始预览，即一直发送预览的请求
            _cameraCaptureSession!!.setRepeatingRequest(_cameraCaptureRequest!!, null, _backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun releaseCamera() {
        if (_cameraCaptureSession != null) {
            _cameraCaptureSession!!.close()
            _cameraCaptureSession = null
        }
        if (_cameraDevice != null) {
            _cameraDevice!!.close()
            _cameraDevice = null
        }
        if (_imageReader != null) {
            _imageReader!!.close()
            _imageReader = null
        }
        _orientationEventListener.disable()
        stopBackgroundThread() // 对应 openCamera() 方法中的 startBackgroundThread()
    }

    /**
     * 进行拍照
     */
    fun capture() {
        _cameraCapture = true
    }

    fun setPreviewSurfaceHolder(holder: SurfaceHolder) {
        _previewSurface = holder.surface
    }

    fun setPreviewSurfaceTexture(surfaceTexture: SurfaceTexture) {
        _previewSurfaceTexture = surfaceTexture
    }

    fun stopPreview() {
        if (_cameraCaptureSession == null || _previewRequestBuilder == null) return
        try {
            _cameraCaptureSession!!.stopRepeating()
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun isFrontCamera(): Boolean {
        return _cameraId == CameraCharacteristics.LENS_FACING_FRONT
    }

    fun getPreviewSize(): Size? {
        return _previewSize
    }

    fun switchCamera(owner: LifecycleOwner, previewWidth: Int, previewHeight: Int) {
        _cameraId = _cameraId xor 1
        releaseCamera()
        openCamera(previewWidth, previewHeight)
    }

    fun handleZoom(isZoomIn: Boolean) {
        if (_cameraDevice == null || _cameraCharacteristics == null || _previewRequestBuilder == null) return
        val maxZoom = (_cameraCharacteristics!!.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)!!.toInt() * 10)
        val rect = _cameraCharacteristics!!.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE)
        if (isZoomIn && _cameraZoom < maxZoom) {
            _cameraZoom++
        } else if (_cameraZoom > 1) {
            _cameraZoom--
        }
        val minW = rect!!.width() / maxZoom
        val minH = rect.height() / maxZoom
        val difW = rect.width() - minW
        val difH = rect.height() - minH
        var cropW = difW * _cameraZoom / 100
        var cropH = difH * _cameraZoom / 100
        cropW -= cropW and 3
        cropH -= cropH and 3
        val zoomRect = Rect(cropW, cropH, rect.width() - cropW, rect.height() - cropH)
        _previewRequestBuilder!!.set(CaptureRequest.SCALER_CROP_REGION, zoomRect)
        _cameraCaptureRequest = _previewRequestBuilder!!.build()
        startPreview() // 需要重新 start preview 才能生效
    }

    fun focusOnPoint(x: Double, y: Double, width: Int, height: Int) {
        if (_cameraDevice == null || _previewRequestBuilder == null) return
        var tempX = x
        var tempY = y
        // 1. 先取相对于view上面的坐标
        var previewWidth = _previewSize!!.width
        var previewHeight = _previewSize!!.height
        if (_cameraRotate == 90 || _cameraRotate == 270) {
            previewWidth = _previewSize!!.height
            previewHeight = _previewSize!!.width
        }
        // 2. 计算摄像头取出的图像相对于view放大了多少，以及有多少偏移
        val tmp: Double
        var imgScale: Double
        var verticalOffset = 0.0
        var horizontalOffset = 0.0
        if (previewHeight * width > previewWidth * height) {
            imgScale = width * 1.0 / previewWidth
            verticalOffset = (previewHeight - height / imgScale) / 2
        } else {
            imgScale = height * 1.0 / previewHeight
            horizontalOffset = (previewWidth - width / imgScale) / 2
        }
        // 3. 将点击的坐标转换为图像上的坐标
        tempX = tempX / imgScale + horizontalOffset
        tempY = tempY / imgScale + verticalOffset
        if (90 == _cameraRotate) {
            tmp = tempX
            tempX = tempY
            tempY = _previewSize!!.height - tmp
        } else if (270 == _cameraRotate) {
            tmp = tempX
            tempX = _previewSize!!.width - tempY
            tempY = tmp
        }
        // 4. 计算取到的图像相对于裁剪区域的缩放系数，以及位移
        var cropRegion = _previewRequestBuilder!!.get(CaptureRequest.SCALER_CROP_REGION)
        if (cropRegion == null) {
            //  Log.w(TAG, "can't get crop region");
            cropRegion = _cameraCharacteristics!!.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE)
        }
        val cropWidth = cropRegion!!.width()
        val cropHeight = cropRegion.height()
        if (_previewSize!!.height * cropWidth > _previewSize!!.width * cropHeight) {
            imgScale = cropHeight * 1.0 / _previewSize!!.height
            verticalOffset = 0.0
            horizontalOffset = (cropWidth - imgScale * _previewSize!!.width) / 2
        } else {
            imgScale = cropWidth * 1.0 / _previewSize!!.width
            horizontalOffset = 0.0
            verticalOffset = (cropHeight - imgScale * _previewSize!!.height) / 2
        }
        // 5. 将点击区域相对于图像的坐标，转化为相对于成像区域的坐标
        tempX = tempX * imgScale + horizontalOffset + cropRegion.left
        tempY = tempY * imgScale + verticalOffset + cropRegion.top
        val tapAreaRatio = 0.1
        val rect = Rect()
        rect.left = clamp((tempX - tapAreaRatio / 2 * cropRegion.width()).toInt(), 0, cropRegion.width())
        rect.right = clamp((tempX + tapAreaRatio / 2 * cropRegion.width()).toInt(), 0, cropRegion.width())
        rect.top = clamp((tempY - tapAreaRatio / 2 * cropRegion.height()).toInt(), 0, cropRegion.height())
        rect.bottom = clamp((tempY + tapAreaRatio / 2 * cropRegion.height()).toInt(), 0, cropRegion.height())
        // 6. 设置 AF、AE 的测光区域，即上述得到的 rect
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_REGIONS, arrayOf(MeteringRectangle(rect, 1000)))
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AE_REGIONS, arrayOf(MeteringRectangle(rect, 1000)))
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_AUTO)
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START)
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_START)
        try {
            // 7. 发送上述设置的对焦请求，并监听回调
            _cameraCaptureSession!!.capture(_previewRequestBuilder!!.build(), _captureCallback, _backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun getJpegOrientation(deviceOrientation: Int): Int {
        var tempDeviceOrientation = deviceOrientation
        if (tempDeviceOrientation == OrientationEventListener.ORIENTATION_UNKNOWN) return 0
        val sensorOrientation = _cameraCharacteristics!!.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
        // Round device orientation to a multiple of 90
        tempDeviceOrientation = (tempDeviceOrientation + 45) / 90 * 90
        // Reverse device orientation for front-facing cameras
        val facingFront =
            _cameraCharacteristics!!.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT
        if (facingFront) tempDeviceOrientation = -tempDeviceOrientation
        // Calculate desired JPEG orientation relative to camera orientation to make
        // the image upright relative to the device orientation
        return (sensorOrientation + tempDeviceOrientation + 360) % 360
    }

    private fun initPreview(cameraDevice: CameraDevice) {
        try {
            _previewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            _previewSurface = Surface(_renderer.getTexture())
            _renderer.getTexture()?.setDefaultBufferSize(_previewSize!!.width, _previewSize!!.height)
            _previewRequestBuilder!!.addTarget(_previewSurface!!) // 设置预览输出的 Surface
            _imageReader = ImageReader.newInstance(_previewSize!!.width, _previewSize!!.height, ImageFormat.YUV_420_888, 2)
            _imageReader!!.setOnImageAvailableListener(_imageAvailableListener, _backgroundHandler)
            _previewRequestBuilder!!.addTarget(_imageReader!!.surface) // 设置预览输出的 Surface
            cameraDevice.createCaptureSession(
                listOf(_previewSurface, _imageReader!!.surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        try {
                            session.setRepeatingRequest(_previewRequestBuilder!!.build(), object : CameraCaptureSession.CaptureCallback() {
                                override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                                    super.onCaptureCompleted(session, request, result)
                                    _renderer.onRequestRender()
                                }
                            }, _backgroundHandler)
                        } catch (e: CameraAccessException) {
                            e.printStackTrace()
                        }
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        //  Log.e(TAG, "ConfigureFailed. session: mCaptureSession");
                    }
                }, _backgroundHandler
            ) // handle 传入 null 表示使用当前线程的 Looper
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private fun choosePreviewSize(sizes: Array<Size>, viewWidth: Int, viewHeight: Int, pictureSize: Size): Size {
        val totalRotation = getRotation(_activity)
        val swapRotation = totalRotation == 90 || totalRotation == 270
        val width = if (swapRotation) viewHeight else viewWidth
        return getSuitableSize(sizes, width, pictureSize)
    }

    private fun getRotation(activity: Activity): Int {
        var displayRotation = UtilKDisplay.getRotation(activity)
        when (displayRotation) {
            Surface.ROTATION_0 -> displayRotation = 90
            Surface.ROTATION_90 -> displayRotation = 0
            Surface.ROTATION_180 -> displayRotation = 270
            Surface.ROTATION_270 -> displayRotation = 180
        }
        val sensorOrientation = _cameraCharacteristics!!.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
        _cameraRotate = (displayRotation + sensorOrientation + 270) % 360
        return _cameraRotate
    }

    private fun getSuitableSize(sizes: Array<Size>, width: Int, pictureSize: Size): Size {
        var minDelta = Int.MAX_VALUE // 最小的差值，初始值应该设置大点保证之后的计算中会被重置
        var index = 0 // 最小的差值对应的索引坐标
        val aspectRatio = pictureSize.height * 1.0f / pictureSize.width
        for (i in sizes.indices) {
            val size = sizes[i]
            // 先判断比例是否相等
            if (size.width * aspectRatio == size.height.toFloat()) {
                val delta = abs(width - size.width)
                if (delta == 0) {
                    return size
                }
                if (minDelta > delta) {
                    minDelta = delta
                    index = i
                }
            }
        }
        return sizes[index]
    }

    private fun startBackgroundThread() {
        if (_backgroundThread == null || _backgroundHandler == null) {
            _backgroundThread = HandlerThread("Camera2KBackgroundThread")
            _backgroundThread!!.start()
            _backgroundHandler = Handler(_backgroundThread!!.looper)
        }
    }

    private fun stopBackgroundThread() {
        _backgroundThread?.quitSafely()
        try {
            _backgroundThread?.join()
            _backgroundThread = null
            _backgroundHandler = null
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun clamp(x: Int, min: Int, max: Int): Int = if (x > max) max else if (x < min) min else x

    /**
     * 根据他们的面积比较两个大小
     * Compares two `Size`s based on their areas.
     */
    internal class CompareSizesByArea : Comparator<Size> {
        override fun compare(lhs: Size, rhs: Size): Int {
            //我们在这里进行强制转换以确保乘法不会过度 We cast here to ensure the multiplications won't overflow
            return java.lang.Long.signum(
                lhs.width.toLong() * lhs.height -
                        rhs.width.toLong() * rhs.height
            )
        }
    }
}