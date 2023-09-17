package com.mozhimen.componentk.camerak.camerax

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import androidx.camera.core.FocusMeteringAction
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.slider.Slider
import com.mozhimen.basick.elemk.android.hardware.commons.IDisplayListener
import com.mozhimen.basick.elemk.android.view.bases.BaseGestureDetector
import com.mozhimen.basick.manifestk.annors.AManifestKRequire
import com.mozhimen.basick.manifestk.cons.CPermission
import com.mozhimen.basick.manifestk.cons.CUseFeature
import com.mozhimen.basick.utilk.android.hardware.UtilKDisplayManager
import com.mozhimen.componentk.R
import com.mozhimen.componentk.camerak.camerax.annors.ACameraKXFacing
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKX
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXCaptureListener
import com.mozhimen.componentk.camerak.camerax.commons.ICameraKXListener
import com.mozhimen.componentk.camerak.camerax.commons.ICameraXKFrameListener
import com.mozhimen.componentk.camerak.camerax.cons.ECameraKXTimer
import com.mozhimen.componentk.camerak.camerax.helpers.CameraKXDelegate
import com.mozhimen.componentk.camerak.camerax.helpers.CameraKXUtil
import com.mozhimen.componentk.camerak.camerax.mos.MCameraKXConfig
import com.mozhimen.uicorek.layoutk.bases.BaseLayoutKFrame

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

    private val _cameraXKDelegate: CameraKXDelegate by lazy { CameraKXDelegate(this) }
    private var _focusMeteringAction: FocusMeteringAction? = null
    //////////////////////////////////////////////////////////////////////////////////////////////

    private var _previewView: PreviewView? = null
    private var _slider: Slider? = null
    private var _sliderContainer: FrameLayout? = null

    val previewView get() = _previewView
    val slider get() = _slider
    val sliderContainer get() = _sliderContainer

    //////////////////////////////////////////////////////////////////////////////////////////////

    private var _displayId = -1
    private val _displayManager by lazy { UtilKDisplayManager.get(context) }//显示管理器获取显示更改回调的实例 An instance for display manager to get display change callbacks
    private val _displayManagerListener = object : IDisplayListener {
        override fun onDisplayChanged(displayId: Int) {
            if (displayId == _displayId)
                _cameraXKDelegate.rotation = this@CameraKXLayout.display.rotation
        }
    }
    private val _onAttachStateChangeListener = object : OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
            _displayManager.registerDisplayListener(_displayManagerListener, null)

        }

        override fun onViewDetachedFromWindow(v: View) {
            _displayManager.unregisterDisplayListener(_displayManagerListener)
            _previewView?.removeOnAttachStateChangeListener(this)
        }
    }

    private val _onGlobalLayoutListener = object : OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            Log.d(TAG, "_onLayoutChangeListener: width ${_previewView!!.width} height ${_previewView!!.height}")
            _cameraXKDelegate.aspectRatio = CameraKXUtil.getFitAspectRatio(_previewView!!.width, _previewView!!.height)//输出图像和预览图像的比率 The ratio for the output image and preview
            _cameraXKDelegate.rotation = _previewView!!.display.rotation.also { Log.d(TAG, "onViewAttachedToWindow: rotation $rotation") }
            _displayId = _previewView!!.display.displayId
            restartCameraKX()
            _previewView!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    }

    private val _zoomGestureDetector = object : BaseGestureDetector(context) {
        //        override fun onZoomUp() {
//            if (_cameraXKDelegate.zoomRatio < _cameraXKDelegate.maxZoomRatio) {
//                _cameraXKDelegate.cameraControl?.setZoomRatio((_cameraXKDelegate.zoomRatio + 0.1).toFloat())
//            }
//        }
//
//        override fun onZoomDown() {
//            if (_cameraXKDelegate.zoomRatio > _cameraXKDelegate.minZoomRatio) {
//                _cameraXKDelegate.cameraControl?.setZoomRatio((_cameraXKDelegate.zoomRatio - 0.1).toFloat())
//            }
//        }
        override fun onSingleClick(x: Float, y: Float) {
            try {
                _focusMeteringAction = FocusMeteringAction.Builder(_previewView!!.meteringPointFactory.createPoint(x, y)).build()
                _cameraXKDelegate.cameraControl?.startFocusAndMetering(_focusMeteringAction!!)
            } catch (_: Exception) {
            }
        }

        override fun onScale(scaleFactor: Float) {
            val zoomRatio = _cameraXKDelegate.zoomRatio
            if (zoomRatio in _cameraXKDelegate.minZoomRatio.._cameraXKDelegate.maxZoomRatio)
                _cameraXKDelegate.cameraControl?.setZoomRatio(zoomRatio * scaleFactor)
        }

        override fun onDoubleClick(x: Float, y: Float) {
            if (_cameraXKDelegate.zoomRatio > _cameraXKDelegate.minZoomRatio)
                _cameraXKDelegate.cameraControl?.setLinearZoom(0f)
            else
                _cameraXKDelegate.cameraControl?.setLinearZoom(0.5f)
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    init {
        initView()
    }

    //region # open fun
    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.cameraxk_preview_layout, this)
        _previewView =
            view.findViewById<PreviewView>(R.id.cameraxk_preview).apply {
                addOnAttachStateChangeListener(_onAttachStateChangeListener)
                viewTreeObserver.addOnGlobalLayoutListener(_onGlobalLayoutListener)
                setOnTouchListener { v, event ->
                    _zoomGestureDetector.onTouch(v, event)// 自定义预览界面touch类
                }
            }
        _sliderContainer =
            view.findViewById<FrameLayout>(R.id.cameraxk_container)
        _slider =
            view.findViewById<Slider>(R.id.cameraxk_slider)
    }

//    private fun initPreview() {
//        if (_previewView != null) {
//            _cameraXKDelegate.aspectRatio = CameraKXUtil.getFitAspectRatio(_previewView!!.width, _previewView!!.height)//输出图像和预览图像的比率 The ratio for the output image and preview
//            _cameraXKDelegate.rotation = _previewView!!.display.rotation//rotation
//            _preview = Preview.Builder()
//                .setTargetAspectRatio(_cameraXKDelegate.aspectRatio) // set the camera aspect ratio
//                .setTargetRotation(_cameraXKDelegate.rotation) // set the camera rotation
//                .build()//摄像头预览的配置 The Configuration of camera preview
//        }
//    }

    override fun initCameraKX(owner: LifecycleOwner, config: MCameraKXConfig) {
        _cameraXKDelegate.initCameraKX(owner, config)
    }

    override fun initCameraKX(owner: LifecycleOwner) {
        _cameraXKDelegate.initCameraKX(owner)
    }

    override fun restartCameraKX() {
        _cameraXKDelegate.restartCameraKX()
    }

    override fun startCapture() {
        _cameraXKDelegate.startCapture()
    }

    override fun isCameraKXStart(): Boolean {
        return _cameraXKDelegate.isCameraKXStart()
    }

    override fun stopCameraKX() {
        _cameraXKDelegate.stopCameraKX()
    }

    override fun setCameraXListener(listener: ICameraKXListener) {
        _cameraXKDelegate.setCameraXListener(listener)
    }

    override fun setCameraXCaptureListener(listener: ICameraKXCaptureListener) {
        _cameraXKDelegate.setCameraXCaptureListener(listener)
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

    override fun changeRotation(rotation: Int) {
        _cameraXKDelegate.changeRotation(rotation)
    }

    override fun changeFacing(@ACameraKXFacing facing: Int) {
        _cameraXKDelegate.changeFacing(facing)
    }
    //endregion

    override fun onDetachedFromWindow() {
        stopCameraKX()
        super.onDetachedFromWindow()
    }
}

