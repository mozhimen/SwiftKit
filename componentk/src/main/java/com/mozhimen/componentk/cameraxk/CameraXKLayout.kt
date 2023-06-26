package com.mozhimen.componentk.cameraxk

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.display.DisplayManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.camera.core.AspectRatio
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.slider.Slider
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.cons.CUseFeature
import com.mozhimen.basick.utilk.android.hardware.UtilKDisplayManager
import com.mozhimen.componentk.R
import com.mozhimen.componentk.cameraxk.annors.ACameraXKFacing
import com.mozhimen.componentk.cameraxk.annors.ACameraXKRotation
import com.mozhimen.componentk.cameraxk.commons.ICameraXKAction
import com.mozhimen.componentk.cameraxk.commons.ICameraXKCaptureListener
import com.mozhimen.componentk.cameraxk.commons.ICameraXKFrameListener
import com.mozhimen.componentk.cameraxk.commons.ICameraXKListener
import com.mozhimen.componentk.cameraxk.cons.CAspectRatio
import com.mozhimen.componentk.cameraxk.cons.ECameraXKTimer
import com.mozhimen.componentk.cameraxk.helpers.CameraXKProxy
import com.mozhimen.componentk.cameraxk.mos.CameraXKConfig
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
@AManifestKRequire(CPermission.CAMERA, CUseFeature.CAMERA, CUseFeature.CAMERA_AUTOFOCUS)
class CameraXKLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr), ICameraXKAction {

    private lateinit var _cameraXKProxy: CameraXKProxy
    private lateinit var _preview: Preview
    private lateinit var _previewView: PreviewView
    private lateinit var _slider: Slider
    private lateinit var _sliderContainer: FrameLayout
    private var _aspectRatio: Int by Delegates.observable(AspectRatio.RATIO_16_9) { _, _, new ->
        _cameraXKProxy.aspectRatio = new
    }
    private var _rotation: Int by Delegates.observable(ACameraXKRotation.ROTATION_90) { _, _, new ->
        _cameraXKProxy.rotation = new
    }
    private var _displayId = -1

    /**
     * 显示管理器获取显示更改回调的实例
     * An instance for display manager to get display change callbacks
     */
    private val _displayManager by lazy { UtilKDisplayManager.get(context) }
    private val _displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit

        @SuppressLint("UnsafeOptInUsageError")
        override fun onDisplayChanged(displayId: Int) {
            if (displayId == this@CameraXKLayout._displayId) {
                _preview.targetRotation = _rotation
                _rotation = this@CameraXKLayout.display.rotation
            }
        }
    }

    private val _onAttachStateChangeListener = object : OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
            _displayManager.registerDisplayListener(_displayListener, null)
        }

        override fun onViewDetachedFromWindow(v: View) {
            _displayManager.unregisterDisplayListener(_displayListener)
        }
    }

    init {
        if (!isInEditMode) {
            initView()
            _cameraXKProxy = CameraXKProxy()
            _cameraXKProxy.apply {
                slider = _slider
                previewView = _previewView
            }
            this.post {
                initPreview()
                _cameraXKProxy.preview = _preview
            }
        }
    }

    //region # open fun
    override fun setCameraXKListener(listener: ICameraXKListener) {
        _cameraXKProxy.setCameraXKListener(listener)
    }

    override fun setCameraXKCaptureListener(listener: ICameraXKCaptureListener) {
        _cameraXKProxy.setCameraXKCaptureListener(listener)
    }

    fun initCamera(
        owner: LifecycleOwner,
        cameraXKConfig: CameraXKConfig
    ) {
        _cameraXKProxy.initCamera(owner, cameraXKConfig)
    }

    fun startCamera() {
        _cameraXKProxy.startCamera()
    }

    override fun setCameraXKFrameListener(listener: ICameraXKFrameListener) {
        _cameraXKProxy.setCameraXKFrameListener(listener)
    }

    override fun changeHdr(isOpen: Boolean) {
        _cameraXKProxy.changeHdr(isOpen)
    }

    override fun changeFlash(flashMode: Int) {
        _cameraXKProxy.changeFlash(flashMode)
    }

    override fun changeCountDownTimer(timer: ECameraXKTimer) {
        _cameraXKProxy.changeCountDownTimer(timer)
    }

    override fun changeCameraFacing(@ACameraXKFacing facing: Int) {
        _cameraXKProxy.changeCameraFacing(facing)
    }

    override fun takePicture() {
        _cameraXKProxy.takePicture()
    }
    //endregion

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.cameraxk_preview_layout, this)
        _previewView = view.findViewById(R.id.cameraxk_preview)
        _previewView.addOnAttachStateChangeListener(_onAttachStateChangeListener)
        _sliderContainer = view.findViewById(R.id.cameraxk_container)
        _slider = view.findViewById(R.id.cameraxk_slider)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _cameraXKProxy.onFrameFinished()
        _displayManager.unregisterDisplayListener(_displayListener)
    }

    private fun initPreview() {
        _displayId = _previewView.display.displayId

        //输出图像和预览图像的比率 The ratio for the output image and preview
        _aspectRatio = aspectRatio(_previewView.width, _previewView.height)

        //rotation
        _rotation = _previewView.display.rotation

        //摄像头预览的配置 The Configuration of camera preview
        _preview = Preview.Builder()
            .setTargetAspectRatio(_aspectRatio) // set the camera aspect ratio
            .setTargetRotation(_rotation) // set the camera rotation
            .build()
    }

    /**
     *  检测当前尺寸的最合适的长宽比
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - CAspectRatio.RATIO_4_3) <= abs(previewRatio - CAspectRatio.RATIO_16_9)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }
}