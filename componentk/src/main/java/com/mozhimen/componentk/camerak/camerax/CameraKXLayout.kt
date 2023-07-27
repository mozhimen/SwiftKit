package com.mozhimen.componentk.camerak.camerax

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
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFacing
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXRotation
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKX
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXCaptureListener
import com.mozhimen.componentk.camerak.camerax.commons.ICameraXKFrameListener
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXListener
import com.mozhimen.componentk.camerak.camerax.cons.CCameraKXAspectRatio
import com.mozhimen.componentk.camerak.camerax.cons.ECameraKXTimer
import com.mozhimen.componentk.camerak.camerax.helpers.CameraKXDelegate
import com.mozhimen.componentk.camerak.camerax.mos.MCameraKXConfig
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame
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
class CameraKXLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseLayoutKFrame(context, attrs, defStyleAttr), ICameraKX {

    private lateinit var _cameraXKDelegate: CameraKXDelegate
    private lateinit var _preview: Preview
    private lateinit var _previewView: PreviewView
    private lateinit var _slider: Slider
    private lateinit var _sliderContainer: FrameLayout

    //////////////////////////////////////////////////////////////////////////////////////////////

    private var _aspectRatio: Int by Delegates.observable(AspectRatio.RATIO_16_9) { _, _, new ->
        _cameraXKDelegate.aspectRatio = new
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private var _rotation: Int by Delegates.observable(ACameraKXRotation.ROTATION_90) { _, _, new ->
        _cameraXKDelegate.rotation = new
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

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
            if (displayId == this@CameraKXLayout._displayId) {
                _preview.targetRotation = _rotation
                _rotation = this@CameraKXLayout.display.rotation
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

    //////////////////////////////////////////////////////////////////////////////////////////////

    init {
        if (!isInEditMode) {
            initView()
            _cameraXKDelegate = CameraKXDelegate()
            _cameraXKDelegate.apply {
                slider = _slider
                previewView = _previewView
            }
            this.post {
                initPreview()
                _cameraXKDelegate.preview = _preview
            }
        }
    }

    //region # open fun
    override fun setCameraXListener(listener: ICameraKXListener) {
        _cameraXKDelegate.setCameraXListener(listener)
    }

    override fun setCameraXCaptureListener(listener: ICameraKXCaptureListener) {
        _cameraXKDelegate.setCameraXCaptureListener(listener)
    }

    override fun initCameraX(owner: LifecycleOwner, config: MCameraKXConfig) {
        _cameraXKDelegate.initCameraX(owner, config)
    }

    override fun initCameraX(owner: LifecycleOwner) {
        _cameraXKDelegate.initCameraX(owner)
    }

    override fun startCameraX() {
        _cameraXKDelegate.startCameraX()
    }

    override fun setCameraXFrameListener(listener: ICameraXKFrameListener) {
        _cameraXKDelegate.setCameraXFrameListener(listener)
    }

    override fun changeHdr(isOpen: Boolean) {
        _cameraXKDelegate.changeHdr(isOpen)
    }

    override fun changeFlash(flashMode: Int) {
        _cameraXKDelegate.changeFlash(flashMode)
    }

    override fun changeCountDownTimer(timer: ECameraKXTimer) {
        _cameraXKDelegate.changeCountDownTimer(timer)
    }

    override fun changeCameraXFacing(@ACameraKXFacing facing: Int) {
        _cameraXKDelegate.changeCameraXFacing(facing)
    }

    override fun startCapture() {
        _cameraXKDelegate.startCapture()
    }
    //endregion

    override fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.cameraxk_preview_layout, this)
        _previewView = view.findViewById(R.id.cameraxk_preview)
        _previewView.addOnAttachStateChangeListener(_onAttachStateChangeListener)
        _sliderContainer = view.findViewById(R.id.cameraxk_container)
        _slider = view.findViewById(R.id.cameraxk_slider)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _cameraXKDelegate.onFrameFinished()
        _displayManager.unregisterDisplayListener(_displayListener)
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    private fun initPreview() {
        _displayId = _previewView.display.displayId

        //输出图像和预览图像的比率 The ratio for the output image and preview
        _aspectRatio = getFitAspectRatio(_previewView.width, _previewView.height)

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
    private fun getFitAspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - CCameraKXAspectRatio.RATIO_4_3) <= abs(previewRatio - CCameraKXAspectRatio.RATIO_16_9)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }
}