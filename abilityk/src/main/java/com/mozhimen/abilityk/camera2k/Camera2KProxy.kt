package com.mozhimen.abilityk.camera2k

import android.annotation.SuppressLint
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
import android.util.Log
import android.util.Size
import android.view.OrientationEventListener
import android.view.Surface
import android.view.SurfaceHolder
import android.widget.Toast
import com.mozhimen.basick.utilk.UtilKGlobal
import java.lang.IllegalArgumentException
import java.util.*
import java.util.concurrent.locks.ReentrantLock

/**
 * @ClassName Camera2KProxy
 * @Description TODO
 * @Author Kolin Zhao / Mozhimen
 * @Date 2022/6/15 17:56
 * @Version 1.0
 */
class Camera2KProxy {

    private val TAG = "Camera2KProxy>>>>>"

    private val _context = UtilKGlobal.instance.getApp()!!
    private var _cameraDevice: CameraDevice? = null // 相机对象
    private var _cameraManager: CameraManager? = null// 相机管理者
    private var _cameraCharacteristics: CameraCharacteristics? = null// 相机属性
    private var _cameraCaptureSession: CameraCaptureSession? = null

    private var _previewSize: Size? = null// 预览大小
    private var _previewSurface: Surface? = null
    private var _previewSurfaceTexture: SurfaceTexture? = null
    private var _previewRequestBuilder: CaptureRequest.Builder? = null// 相机预览请求的构造器

    private var _cameraId = CameraCharacteristics.LENS_FACING_FRONT // 要打开的摄像头ID


    private var _previewRequest: CaptureRequest? = null
    private var _backgroundHandler: Handler? = null
    private var _backgroundThread: HandlerThread? = null
    private var _imageReader: ImageReader? = null
    private var _orientationEventListener: OrientationEventListener? = null
    private var _displayRotate = 0
    private var _deviceOrientation = 0 // 设备方向，由相机传感器获取
    private var _zoom = 1 // 缩放
    var _controller: TextureController? = null

    //打开摄像头的回调
    private val _cameraStateCallback: CameraDevice.StateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            _cameraDevice = camera
            initPreviewRequest()
        }

        override fun onDisconnected(camera: CameraDevice) {
            releaseCamera()
        }

        override fun onError(camera: CameraDevice, error: Int) {
            releaseCamera()
        }
    }

    fun Camera2KProxy(controller: TextureController) {
        this._controller = mController
        _cameraManager = _activity!!.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        _orientationEventListener = object : OrientationEventListener(_activity) {
            override fun onOrientationChanged(orientation: Int) {
                _deviceOrientation = orientation
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun openCamera(width: Int, height: Int) {
        //Log.v(TAG, "openCamera");
        startBackgroundThread() // 对应 releaseCamera() 方法中的 stopBackgroundThread()
        try {
            _orientationEventListener!!.enable()
            _cameraCharacteristics = _cameraManager!!.getCameraCharacteristics(Integer.toString(_cameraId))
            val map = _cameraCharacteristics!!.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
            // 拍照大小，选择能支持的一个最大的图片大小
            val largest = Collections.max(Arrays.asList(*map!!.getOutputSizes(ImageFormat.YUV_420_888)), CompareSizesByArea())
            // Log.d(TAG, "picture size: " + largest.getWidth() + "*" + largest.getHeight());

            // 预览大小，根据上面选择的拍照图片的长宽比，选择一个和控件长宽差不多的大小
            _previewSize = chooseOptimalSize(map.getOutputSizes(SurfaceTexture::class.java), width, height, largest)
            //Log.d(TAG, "preview size: " + mPreviewSize.getWidth() + "*" + mPreviewSize.getHeight());
            _controller.setDataSize(_previewSize!!.height, _previewSize!!.width)
            // 打开摄像头
            _cameraManager!!.openCamera(Integer.toString(_cameraId), _cameraStateCallback, _backgroundHandler)
        } catch (e: CameraAccessException) {
            Log.e("CameraV2", e.message!!)
            Log.e("CameraV2", "摄像头打开失败！")
            _activity!!.runOnUiThread { Toast.makeText(_activity, "请检查摄像头", Toast.LENGTH_SHORT).show() }
            //mActivity.finish();
        } catch (e: IllegalArgumentException) {
            Log.e("CameraV2", e.message!!)
            Log.e("CameraV2", "摄像头打开失败！")
            _activity!!.runOnUiThread { Toast.makeText(_activity, "请检查摄像头", Toast.LENGTH_SHORT).show() }
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
        _orientationEventListener!!.disable()
        stopBackgroundThread() // 对应 openCamera() 方法中的 startBackgroundThread()
    }

    var onImageAvailableListener: ImageReader.OnImageAvailableListener? = null

    fun setImageAvailableListener(onImageAvailableListener: ImageReader.OnImageAvailableListener?) {
        this.onImageAvailableListener = onImageAvailableListener
    }

    fun setPreviewSurface(holder: SurfaceHolder) {
        _previewSurface = holder.surface
    }

    fun setPreviewSurface(surfaceTexture: SurfaceTexture?) {
        _previewSurfaceTexture = surfaceTexture
    }

    private fun initPreviewRequest() {
        try {
            _previewRequestBuilder = _cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            _previewSurface = Surface(_controller.getTexture())
            _controller.getTexture().setDefaultBufferSize(
                _previewSize!!.width, _previewSize!!.height
            )
            _previewRequestBuilder!!.addTarget(_previewSurface!!) // 设置预览输出的 Surface
            _imageReader = ImageReader.newInstance(_previewSize!!.width, _previewSize!!.height, ImageFormat.YUV_420_888, 2)
            _imageReader!!.setOnImageAvailableListener(OnImageAvailableListenerImpl(), _backgroundHandler)
            _previewRequestBuilder!!.addTarget(_imageReader!!.surface) // 设置预览输出的 Surface
            _cameraDevice!!.createCaptureSession(
                Arrays.asList(_previewSurface, _imageReader!!.surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        try {
                            session.setRepeatingRequest(_previewRequestBuilder!!.build(), object : CameraCaptureSession.CaptureCallback() {
                                override fun onCaptureProgressed(
                                    session: CameraCaptureSession,
                                    request: CaptureRequest,
                                    partialResult: CaptureResult
                                ) {
                                    super.onCaptureProgressed(session, request, partialResult)
                                }

                                override fun onCaptureCompleted(session: CameraCaptureSession, request: CaptureRequest, result: TotalCaptureResult) {
                                    super.onCaptureCompleted(session, request, result)
                                    _controller.requestRender()
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

    fun startPreview() {
        //Log.v(TAG, "startPreview");
        if (_cameraCaptureSession == null || _previewRequestBuilder == null) {
            //Log.w(TAG, "startPreview: mCaptureSession or mPreviewRequestBuilder is null");
            return
        }
        try {
            // 开始预览，即一直发送预览的请求
            _cameraCaptureSession!!.setRepeatingRequest(_previewRequest!!, null, _backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    fun stopPreview() {
        // Log.v(TAG, "stopPreview");
        if (_cameraCaptureSession == null || _previewRequestBuilder == null) {
            //Log.w(TAG, "stopPreview: mCaptureSession or mPreviewRequestBuilder is null");
            return
        }
        try {
            _cameraCaptureSession!!.stopRepeating()
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }


    private fun getJpegOrientation(deviceOrientation: Int): Int {
        var deviceOrientation = deviceOrientation
        if (deviceOrientation == OrientationEventListener.ORIENTATION_UNKNOWN) return 0
        val sensorOrientation = _cameraCharacteristics!!.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
        // Round device orientation to a multiple of 90
        deviceOrientation = (deviceOrientation + 45) / 90 * 90
        // Reverse device orientation for front-facing cameras
        val facingFront =
            _cameraCharacteristics!!.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT
        if (facingFront) deviceOrientation = -deviceOrientation
        // Calculate desired JPEG orientation relative to camera orientation to make
        // the image upright relative to the device orientation
        //Log.d(TAG, "jpegOrientation: " + jpegOrientation);
        return (sensorOrientation + deviceOrientation + 360) % 360
    }

    fun isFrontCamera(): Boolean {
        return _cameraId == CameraCharacteristics.LENS_FACING_BACK
    }

    fun getPreviewSize(): Size? {
        return _previewSize
    }

    fun switchCamera(width: Int, height: Int) {
        _cameraId = _cameraId xor 1
        //Log.d(TAG, "switchCamera: mCameraId: " + mCameraId);
        releaseCamera()
        openCamera(width, height)
    }

    private fun chooseOptimalSize(sizes: Array<Size>, viewWidth: Int, viewHeight: Int, pictureSize: Size): Size? {
        val totalRotation = getRotation()
        val swapRotation = totalRotation == 90 || totalRotation == 270
        val width = if (swapRotation) viewHeight else viewWidth
        val height = if (swapRotation) viewWidth else viewHeight
        return getSuitableSize(sizes, width, height, pictureSize)
    }

    private fun getRotation(): Int {
        var displayRotation = _activity!!.windowManager.defaultDisplay.rotation
        when (displayRotation) {
            Surface.ROTATION_0 -> displayRotation = 90
            Surface.ROTATION_90 -> displayRotation = 0
            Surface.ROTATION_180 -> displayRotation = 270
            Surface.ROTATION_270 -> displayRotation = 180
        }
        val sensorOrientation = _cameraCharacteristics!!.get(CameraCharacteristics.SENSOR_ORIENTATION)!!
        _displayRotate = (displayRotation + sensorOrientation + 270) % 360
        return _displayRotate
    }

    private fun getSuitableSize(sizes: Array<Size>, width: Int, height: Int, pictureSize: Size): Size? {
        var minDelta = Int.MAX_VALUE // 最小的差值，初始值应该设置大点保证之后的计算中会被重置
        var index = 0 // 最小的差值对应的索引坐标
        val aspectRatio = pictureSize.height * 1.0f / pictureSize.width
        //Log.d(TAG, "getSuitableSize. aspectRatio: " + aspectRatio);
        for (i in sizes.indices) {
            val size = sizes[i]
            // 先判断比例是否相等
            if (size.width * aspectRatio == size.height.toFloat()) {
                val delta = Math.abs(width - size.width)
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

    fun handleZoom(isZoomIn: Boolean) {
        if (_cameraDevice == null || _cameraCharacteristics == null || _previewRequestBuilder == null) {
            return
        }
        val maxZoom = (_cameraCharacteristics!!.get(CameraCharacteristics.SCALER_AVAILABLE_MAX_DIGITAL_ZOOM)!!.toInt()
                * 10)
        // Log.d(TAG, "handleZoom: maxZoom: " + maxZoom);
        val rect = _cameraCharacteristics!!.get(CameraCharacteristics.SENSOR_INFO_ACTIVE_ARRAY_SIZE)
        if (isZoomIn && _zoom < maxZoom) {
            _zoom++
        } else if (_zoom > 1) {
            _zoom--
        }
        // Log.d(TAG, "handleZoom: mZoom: " + mZoom);
        val minW = rect!!.width() / maxZoom
        val minH = rect.height() / maxZoom
        val difW = rect.width() - minW
        val difH = rect.height() - minH
        var cropW = difW * _zoom / 100
        var cropH = difH * _zoom / 100
        cropW -= cropW and 3
        cropH -= cropH and 3
        // Log.d(TAG, "handleZoom: cropW: " + cropW + ", cropH: " + cropH);
        val zoomRect = Rect(cropW, cropH, rect.width() - cropW, rect.height() - cropH)
        _previewRequestBuilder!!.set(CaptureRequest.SCALER_CROP_REGION, zoomRect)
        _previewRequest = _previewRequestBuilder!!.build()
        startPreview() // 需要重新 start preview 才能生效
    }

    fun focusOnPoint(x: Double, y: Double, width: Int, height: Int) {
        var x = x
        var y = y
        if (_cameraDevice == null || _previewRequestBuilder == null) {
            return
        }
        // 1. 先取相对于view上面的坐标
        var previewWidth = _previewSize!!.width
        var previewHeight = _previewSize!!.height
        if (_displayRotate == 90 || _displayRotate == 270) {
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
        x = x / imgScale + horizontalOffset
        y = y / imgScale + verticalOffset
        if (90 == _displayRotate) {
            tmp = x
            x = y
            y = _previewSize!!.height - tmp
        } else if (270 == _displayRotate) {
            tmp = x
            x = _previewSize!!.width - y
            y = tmp
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
        x = x * imgScale + horizontalOffset + cropRegion.left
        y = y * imgScale + verticalOffset + cropRegion.top
        val tapAreaRatio = 0.1
        val rect = Rect()
        rect.left = clamp((x - tapAreaRatio / 2 * cropRegion.width()).toInt(), 0, cropRegion.width())
        rect.right = clamp((x + tapAreaRatio / 2 * cropRegion.width()).toInt(), 0, cropRegion.width())
        rect.top = clamp((y - tapAreaRatio / 2 * cropRegion.height()).toInt(), 0, cropRegion.height())
        rect.bottom = clamp((y + tapAreaRatio / 2 * cropRegion.height()).toInt(), 0, cropRegion.height())
        // 6. 设置 AF、AE 的测光区域，即上述得到的 rect
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_REGIONS, arrayOf(MeteringRectangle(rect, 1000)))
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AE_REGIONS, arrayOf(MeteringRectangle(rect, 1000)))
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_AUTO)
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_START)
        _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, CameraMetadata.CONTROL_AE_PRECAPTURE_TRIGGER_START)
        try {
            // 7. 发送上述设置的对焦请求，并监听回调
            _cameraCaptureSession!!.capture(_previewRequestBuilder!!.build(), mAfCaptureCallback, _backgroundHandler)
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private val mAfCaptureCallback: CameraCaptureSession.CaptureCallback = object : CameraCaptureSession.CaptureCallback() {
        private fun process(result: CaptureResult) {
            val state = result.get(CaptureResult.CONTROL_AF_STATE) ?: return
            // Log.d(TAG, "process: CONTROL_AF_STATE: " + state);
            if (state == CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED || state == CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED) {
                // Log.d(TAG, "process: start normal preview");
                _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL)
                _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                _previewRequestBuilder!!.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.FLASH_MODE_OFF)
                startPreview()
            }
        }

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
    }


    private fun startBackgroundThread() {
        if (_backgroundThread == null || _backgroundHandler == null) {
            // Log.v(TAG, "startBackgroundThread");
            _backgroundThread = HandlerThread("CameraBackground")
            // mBackgroundThread.setPriority(Thread.MAX_PRIORITY);
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

    private fun clamp(x: Int, min: Int, max: Int): Int {
        if (x > max) {
            return max
        }
        return if (x < min) {
            min
        } else x
    }

    /**
     * Compares two `Size`s based on their areas.
     */
    internal class CompareSizesByArea : Comparator<Size?> {
        override fun compare(lhs: Size, rhs: Size): Int {
            // We cast here to ensure the multiplications won't overflow
            return java.lang.Long.signum(
                lhs.width.toLong() * lhs.height -
                        rhs.width.toLong() * rhs.height
            )
        }
    }


    private var isTakePhoto = false

    /**
     * 进行拍照
     */
    fun takePhoto() {
        isTakePhoto = true
    }


    private class OnImageAvailableListenerImpl : ImageReader.OnImageAvailableListener {
        private val lock = ReentrantLock()
        override fun onImageAvailable(reader: ImageReader) {
            val image = reader.acquireNextImage()
            if (image.format == ImageFormat.YUV_420_888) {
                lock.lock()
                //                Log.e("ysj,,,", "ysj");
                SDKDetectUtils.getSingleton(mActivity).sdkDetect(image)
                lock.unlock()
            }
            image.close()
        }
    }