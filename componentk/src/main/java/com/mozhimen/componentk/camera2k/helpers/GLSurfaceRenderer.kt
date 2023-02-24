package com.mozhimen.componentk.camera2k.helpers

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.graphics.SurfaceTexture
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mozhimen.componentk.camera2k.commons.AFilter
import com.mozhimen.componentk.camera2k.commons.ICamera2KFrameListener
import com.mozhimen.componentk.camera2k.commons.ICamera2KRenderer
import com.mozhimen.basick.utilk.open.UtilKOpenGL
import com.mozhimen.basick.utilk.open.UtilKMatrix
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLDisplay
import javax.microedition.khronos.egl.EGLSurface
import javax.microedition.khronos.opengles.GL10


/**
 * @ClassName GLSurfaceRenderer
 * @Description TODO
 * @Author mozhimen / Kolin Zhao
 * @Date 2022/6/15 22:15
 * @Version 1.0
 */
class GLSurfaceRenderer(context: Context) : GLSurfaceView.Renderer {
    companion object{
        private const val TAG = "GLSurfaceRenderer>>>>>"

    }
    private var _surface: Any? = null
    private var _context: Context = context
    private var _camera2KRenderer: ICamera2KRenderer? = null//用户附加的Renderer或用来监听Renderer

    private lateinit var _camera2KGLSurfaceView: Camera2KGLSurfaceView
    private lateinit var _surfaceHolder: SurfaceHolder

    private var _inputPreviewSize: Point? = null //数据的大小
    private var _outputPreviewSize: Point? = null //输出视图的大小

    private var _effectFilter: CameraFrameFilter? = null//特效处理的Filter
    private var _groupFilter: GroupFilter? = null//中间特效
    private var _showFilter: AFilter? = null //用来渲染输出的Filter

    private val _isParamSet = AtomicBoolean(false)
    private val _screenMatrix: FloatArray = UtilKMatrix.getOriginalMatrix() //用于绘制到屏幕上的变换矩阵
    private var _showType: Int = UtilKMatrix.MATRIX_CENTER_CROP //输出到屏幕上的方式
    private var _directionFlag = 1 //AiyaFilter方向flag
    private val _callbackOptionMatrix = FloatArray(16) //用于绘制回调缩放的矩阵

    private val _exportFrame = IntArray(1)//创建离屏buffer，用于最后导出数据
    private val _exportTexture = IntArray(1)
    private var _isRecord = false //录像flag
    private var _isShoot = false //一次拍摄flag
    private var _outPutBuffer: Array<ByteBuffer?>? = arrayOfNulls(3) //用于存储回调数据的buffer
    private var _camera2KFrameListener: ICamera2KFrameListener? = null//回调

    private var _frameWidth = 0
    private var _frameHeight: Int = 0 //回调数据的宽高
    private var _indexOutput = 0 //回调数据使用的buffer索引

    init {
        initView()
    }

    fun setCamera2KRenderer(renderer: ICamera2KRenderer) {
        _camera2KRenderer = renderer
    }

    /**
     * 在Surface创建前，应该被调用
     * @param width Int
     * @param height Int
     */
    fun setPreviewSize(width: Int, height: Int) {
        _inputPreviewSize!!.x = width
        _inputPreviewSize!!.y = height
    }

    fun setImageDirection(flag: Int) {
        _directionFlag = flag
    }

    /**
     * 设置输入图像与输出视图大小不同时，图像的展示方式
     * @param type 展示方式，可选项为：
     * [UtilKMatrix.MATRIX_CENTER_CROP].[UtilKMatrix.MATRIX_CENTER_IN_SIDE].
     * [UtilKMatrix.MATRIX_FIT_END].[UtilKMatrix.MATRIX_FIT_START].
     * [UtilKMatrix.MATRIX_FIT_XY].与[ImageView.ScaleType]对应
     */
    fun setShowType(type: Int) {
        _showType = type
        if (_outputPreviewSize!!.x > 0 && _outputPreviewSize!!.y > 0) {
            UtilKMatrix.getMatrix(
                _screenMatrix, _showType,
                _inputPreviewSize!!.x, _inputPreviewSize!!.y, _outputPreviewSize!!.x, _outputPreviewSize!!.y
            )
            _showFilter?.setMatrix(_screenMatrix)
            _showFilter?.changeSize(_outputPreviewSize!!.x, _outputPreviewSize!!.y)
        }
    }

    fun setFrameListener(width: Int, height: Int, listener: ICamera2KFrameListener) {
        _frameWidth = width
        _frameHeight = height
        if (_frameWidth > 0 && _frameHeight > 0) {
            if (_outPutBuffer != null) {
                _outPutBuffer = arrayOfNulls(3)
            }
            calculateCallbackOM()
            _camera2KFrameListener = listener
        } else {
            _camera2KFrameListener = null
        }
    }

    /**
     * 增加滤镜
     * @param filter AFilter
     */
    fun addFilter(filter: AFilter) {
        _groupFilter?.addFilter(filter)
    }

    /**
     * 移除滤镜
     * @param filter AFilter
     */
    fun removeFilter(filter: AFilter) {
        _groupFilter?.removeFilter(filter)
    }

    fun onCreate(width: Int, height: Int) {
        _camera2KGLSurfaceView.attachedToWindow()
        onSurfaceCreated(_surface)
        onSurfaceChanged(width, height)
    }

    fun onRequestRender() {
        _camera2KGLSurfaceView.requestRender()
    }

    fun onPause() {
        _camera2KGLSurfaceView.onPause()
    }

    fun onResume() {
        _camera2KGLSurfaceView.onResume()
    }

    fun onDestroy() {
        _camera2KRenderer?.onDestroy()
        _camera2KGLSurfaceView.surfaceDestroyed(_surfaceHolder)
        _camera2KGLSurfaceView.detachedFromWindow()
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        _effectFilter?.create()
        _groupFilter?.create()
        _showFilter?.create()
        if (!_isParamSet.get()) {
            _camera2KRenderer?.onSurfaceCreated(gl, config)
            sdkParamSet()
        }
        calculateCallbackOM()
        _effectFilter?.setFilterFlag(_directionFlag)
        deleteFrameBuffer()
        GLES20.glGenFramebuffers(1, _exportFrame, 0)
        UtilKOpenGL.genTexturesAndParameterf(
            1, _exportTexture, 0, GLES20.GL_RGBA, _inputPreviewSize!!.x,
            _inputPreviewSize!!.y
        )
    }


    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        UtilKMatrix.getMatrix(
            _screenMatrix, _showType,
            _inputPreviewSize!!.x, _inputPreviewSize!!.y, width, height
        )
        _showFilter?.changeSize(width, height)
        _showFilter?.setMatrix(_screenMatrix)
        _groupFilter?.changeSize(_inputPreviewSize!!.x, _inputPreviewSize!!.y)
        _effectFilter?.changeSize(_inputPreviewSize!!.x, _inputPreviewSize!!.y)
        _showFilter?.changeSize(_inputPreviewSize!!.x, _inputPreviewSize!!.y)
        _camera2KRenderer?.onSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        if (_isParamSet.get()) {
            _effectFilter?.draw()
            _groupFilter?.setTextureId(_effectFilter!!.getOutputTexture())
            _groupFilter?.draw()
            GLES20.glViewport(0, 0, _outputPreviewSize!!.x, _outputPreviewSize!!.y)//显示传入的texture上，一般是显示在屏幕上
            _showFilter?.setMatrix(_screenMatrix)
            _showFilter?.setTextureId(_groupFilter!!.getOutputTexture())
            _showFilter?.draw()
            _camera2KRenderer?.onDrawFrame(gl)
            callbackIfNeeded()
        }
    }

    private fun onSurfaceCreated(nativeWindow: Any?) {
        _surface = nativeWindow
        _camera2KGLSurfaceView.surfaceCreated(_surfaceHolder)
    }

    private fun onSurfaceChanged(width: Int, height: Int) {
        _outputPreviewSize!!.x = width
        _outputPreviewSize!!.y = height
        _camera2KGLSurfaceView.surfaceChanged(_surfaceHolder, PixelFormat.RGBA_8888, width, height)
    }

    fun onSurfaceDestroyed() {
        _camera2KGLSurfaceView.surfaceDestroyed(_surfaceHolder)
    }

    fun getOutput(): Any? = _surface

    fun getTexture(): SurfaceTexture? {
        return _effectFilter?.getTexture()
    }

    fun getWindowSize(): Point? {
        return _outputPreviewSize
    }


    /**
     * 获取glView
     * @return GLView
     */
    fun getGLView(): Camera2KGLSurfaceView = _camera2KGLSurfaceView


    fun startRecord() {
        _isRecord = true
    }

    fun stopRecord() {
        _isRecord = false
    }

    fun takePhoto() {
        _isShoot = true
    }

    /**
     * 自定义GLSurfaceView，暴露出onAttachedToWindow
     * 方法及onDetachedFromWindow方法，取消holder的默认监听
     * onAttachedToWindow及onDetachedFromWindow必须保证view存在Parent
     * @constructor
     */
    inner class Camera2KGLSurfaceView(context: Context) : GLSurfaceView(context) {
        init {
            holder.addCallback(null).also { _surfaceHolder = holder }
            setEGLWindowSurfaceFactory(object : EGLWindowSurfaceFactory {
                override fun createWindowSurface(egl: EGL10, display: EGLDisplay, config: EGLConfig, window: Any): EGLSurface? {
                    try {
                        return egl.eglCreateWindowSurface(display, config, _surface, null)
                    } catch (e: Exception) {
                        Log.e(TAG, "createWindowSurface: Exception ${e.message}")
                        e.printStackTrace()
                    }
                    return null
                }

                override fun destroySurface(egl: EGL10, display: EGLDisplay, surface: EGLSurface) {
                    egl.eglDestroySurface(display, surface)
                }
            })
            setEGLContextClientVersion(2)
            setZOrderOnTop(true)
            holder.setFormat(PixelFormat.TRANSLUCENT)
            setRenderer(this@GLSurfaceRenderer)
            renderMode = RENDERMODE_WHEN_DIRTY
            preserveEGLContextOnPause = true
        }

        fun attachedToWindow() {
            super.onAttachedToWindow()
        }

        fun detachedFromWindow() {
            super.onDetachedFromWindow()
        }
    }

    //region private fun
    private fun initView() {
        _camera2KGLSurfaceView = Camera2KGLSurfaceView(_context)

        //避免GLView的attachToWindow和detachFromWindow崩溃
        val contentView: ViewGroup = object : ViewGroup(_context) {
            override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}
        }
        contentView.addView(_camera2KGLSurfaceView)
        contentView.visibility = View.GONE
        _effectFilter = CameraFrameFilter()
        _showFilter = NoFilter()
        _groupFilter = GroupFilter()

        //设置默认的DateSize，DataSize由AiyaProvider根据数据源的图像宽高进行设置
        _inputPreviewSize = Point(720, 1280)
        _outputPreviewSize = Point(720, 1280)
    }

    private fun calculateCallbackOM() {
        if (_frameHeight > 0 && _frameWidth > 0 && _inputPreviewSize!!.x > 0 && _inputPreviewSize!!.y > 0) {
            //计算输出的变换矩阵
            UtilKMatrix.getMatrix(
                _callbackOptionMatrix,
                UtilKMatrix.MATRIX_CENTER_CROP, _inputPreviewSize!!.x, _inputPreviewSize!!.y,
                _frameWidth,
                _frameHeight
            )
            UtilKMatrix.flipMatrix(_callbackOptionMatrix, isFlipX = false, isFlipY = true)
        }
    }

    private fun sdkParamSet() {
        if (!_isParamSet.get() && _inputPreviewSize!!.x > 0 && _inputPreviewSize!!.y > 0) {
            _isParamSet.set(true)
        }
    }

    /**
     * 需要回调，则缩放图片到指定大小，读取数据并回调
     */
    private fun callbackIfNeeded() {
        if (_camera2KFrameListener != null && (_isRecord || _isShoot)) {
            _indexOutput = if (_indexOutput++ >= 2) 0 else _indexOutput
            if (_outPutBuffer!![_indexOutput] == null) {
                _outPutBuffer!![_indexOutput] = ByteBuffer.allocate(
                    _frameWidth *
                            _frameHeight * 4
                )
            }
            GLES20.glViewport(0, 0, _frameWidth, _frameHeight)
            UtilKOpenGL.bindFrameBuffer(_exportFrame[0], _exportTexture[0])
            _showFilter?.setMatrix(_callbackOptionMatrix)
            _showFilter?.draw()
            onFrameCallback()
            _isShoot = false
            UtilKOpenGL.unBindFrameBuffer()
            _showFilter?.setMatrix(_screenMatrix)
        }
    }

    /**
     * 读取数据并回调
     */
    private fun onFrameCallback() {
        GLES20.glReadPixels(0, 0, _frameWidth, _frameHeight, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, _outPutBuffer!![_indexOutput])
        _camera2KFrameListener?.onFrame(_outPutBuffer!![_indexOutput]!!.array(), 0)
    }

    private fun deleteFrameBuffer() {
        GLES20.glDeleteFramebuffers(1, _exportFrame, 0)
        GLES20.glDeleteTextures(1, _exportTexture, 0)
    }
    //endregion
}