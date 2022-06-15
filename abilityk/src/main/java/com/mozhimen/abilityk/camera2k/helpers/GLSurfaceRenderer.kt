package com.mozhimen.abilityk.camera2k.helpers

import android.content.Context
import android.graphics.PixelFormat
import android.graphics.Point
import android.graphics.SurfaceTexture
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraFilter
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
    private var _surface: Any? = null
    private var _context: Context? = null

    private lateinit var _glView: GLView

    private var _renderer: GLSurfaceView.Renderer? = null//用户附加的Renderer或用来监听Renderer

    private var _effectFilter: CameraFilter? = null//特效处理的Filter
    private var _groupFilter: GroupFilter? = null//中间特效
    private var _showFilter: AFilter? = null //用来渲染输出的Filter
    private var _dataSize: Point? = null //数据的大小
    private var mWindowSize: Point? = null //输出视图的大小
    private val isParamSet = AtomicBoolean(false)
    private val SM: FloatArray = Gl2Utils.getOriginalMatrix() //用于绘制到屏幕上的变换矩阵
    private var mShowType: Int = MatrixUtils.TYPE_CENTERCROP //输出到屏幕上的方式
    private var mDirectionFlag = 1 //AiyaFilter方向flag
    private val callbackOM = FloatArray(16) //用于绘制回调缩放的矩阵

    //创建离屏buffer，用于最后导出数据
    private val mExportFrame = IntArray(1)
    private val mExportTexture = IntArray(1)
    private var isRecord = false //录像flag
    private var isShoot = false //一次拍摄flag
    private var outPutBuffer: Array<ByteBuffer?>? = arrayOfNulls(3) //用于存储回调数据的buffer
    private var mFrameCallback: FrameCallback? = null//回调

    private var frameCallbackWidth = 0
    private var frameCallbackHeight: Int = 0 //回调数据的宽高
    private var indexOutput = 0 //回调数据使用的buffer索引

    init {
        initView()
    }

    fun getGLView(): GLView {
        return _glView
    }

    fun surfaceCreated(nativeWindow: Any?) {
        _surface = nativeWindow
        _glView.surfaceCreated(null)
    }

    fun surfaceChanged(width: Int, height: Int) {
        mWindowSize!!.x = width
        mWindowSize!!.y = height
        _glView.surfaceChanged(null, 0, width, height)
    }

    fun surfaceDestroyed() {
        _glView.surfaceDestroyed(null)
    }

    fun getOutput(): Any? {
        return _surface
    }

    private fun initView() {
        _glView = GLView(_context)

        //避免GLView的attachToWindow和detachFromWindow崩溃
        val contentView: ViewGroup = object : ViewGroup(_context) {
            override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {}
        }
        contentView.addView(_glView)
        contentView.visibility = View.GONE
        _effectFilter = CameraFilter(_context!!.resources)
        _showFilter = NoFilter(_context!!.resources)
        _groupFilter = GroupFilter(_context!!.resources)

        //设置默认的DateSize，DataSize由AiyaProvider根据数据源的图像宽高进行设置
        _dataSize = Point(720, 1280)
        mWindowSize = Point(720, 1280)
    }

    //在Surface创建前，应该被调用
    fun setDataSize(width: Int, height: Int) {
        _dataSize!!.x = width
        _dataSize!!.y = height
    }

    fun getTexture(): SurfaceTexture? {
        return _effectFilter.getTexture()
    }

    fun setImageDirection(flag: Int) {
        mDirectionFlag = flag
    }

    fun setRenderer(renderer: Renderer?) {
        _renderer = renderer
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        _effectFilter.create()
        _groupFilter.create()
        _showFilter.create()
        if (!isParamSet.get()) {
            if (_renderer != null) {
                _renderer.onSurfaceCreated(gl, config)
            }
            sdkParamSet()
        }
        calculateCallbackOM()
        _effectFilter.setFlag(mDirectionFlag)
        deleteFrameBuffer()
        GLES20.glGenFramebuffers(1, mExportFrame, 0)
        EasyGlUtils.genTexturesWithParameter(
            1, mExportTexture, 0, GLES20.GL_RGBA, _dataSize!!.x,
            _dataSize!!.y
        )
    }

    private fun deleteFrameBuffer() {
        GLES20.glDeleteFramebuffers(1, mExportFrame, 0)
        GLES20.glDeleteTextures(1, mExportTexture, 0)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        MatrixUtils.getMatrix(
            SM, mShowType,
            _dataSize!!.x, _dataSize!!.y, width, height
        )
        _showFilter.setSize(width, height)
        _showFilter.setMatrix(SM)
        _groupFilter.setSize(_dataSize!!.x, _dataSize!!.y)
        _effectFilter.setSize(_dataSize!!.x, _dataSize!!.y)
        _showFilter.setSize(_dataSize!!.x, _dataSize!!.y)
        if (_renderer != null) {
            _renderer.onSurfaceChanged(gl, width, height)
        }
    }

    override fun onDrawFrame(gl: GL10?) {
        if (isParamSet.get()) {
            _effectFilter.draw()
            _groupFilter.setTextureId(_effectFilter.getOutputTexture())
            _groupFilter.draw()
            //显示传入的texture上，一般是显示在屏幕上
            GLES20.glViewport(0, 0, mWindowSize!!.x, mWindowSize!!.y)
            _showFilter.setMatrix(SM)
            _showFilter.setTextureId(_groupFilter.getOutputTexture())
            _showFilter.draw()
            if (_renderer != null) {
                _renderer.onDrawFrame(gl)
            }
            callbackIfNeeded()
        }
    }

    /**
     * 增加滤镜
     * @param filter [ERROR : AFilter]
     */
    fun addFilter(filter: AFilter?) {
        _groupFilter.addFilter(filter)
    }

    /**
     * 移除滤镜
     * @param filter [ERROR : AFilter]
     */
    fun removeFilter(filter: AFilter?) {
        _groupFilter.removeFilter(filter)
    }

    /**
     * 设置输入图像与输出视图大小不同时，图像的展示方式
     *
     * @param type 展示方式，可选项为：
     * [MatrixUtils.TYPE_CENTERCROP]、[MatrixUtils.TYPE_CENTERINSIDE]、
     * [MatrixUtils.TYPE_FITEND]、[MatrixUtils.TYPE_FITSTART]、
     * [MatrixUtils.TYPE_FITXY]，与[ImageView.ScaleType]对应
     */
    fun setShowType(type: Int) {
        mShowType = type
        if (mWindowSize!!.x > 0 && mWindowSize!!.y > 0) {
            MatrixUtils.getMatrix(
                SM, mShowType,
                _dataSize!!.x, _dataSize!!.y, mWindowSize!!.x, mWindowSize!!.y
            )
            _showFilter.setMatrix(SM)
            _showFilter.setSize(mWindowSize!!.x, mWindowSize!!.y)
        }
    }

    fun startRecord() {
        isRecord = true
    }

    fun stopRecord() {
        isRecord = false
    }

    fun takePhoto() {
        isShoot = true
    }

    fun setFrameCallback(width: Int, height: Int, frameCallback: FrameCallback?) {
        frameCallbackWidth = width
        this.frameCallbackHeight = height
        if (frameCallbackWidth > 0 && frameCallbackHeight > 0) {
            if (outPutBuffer != null) {
                outPutBuffer = arrayOfNulls(3)
            }
            calculateCallbackOM()
            mFrameCallback = frameCallback
        } else {
            mFrameCallback = null
        }
    }

    private fun calculateCallbackOM() {
        if (frameCallbackHeight > 0 && frameCallbackWidth > 0 && _dataSize!!.x > 0 && _dataSize!!.y > 0) {
            //计算输出的变换矩阵
            MatrixUtils.getMatrix(
                callbackOM, MatrixUtils.TYPE_CENTERCROP, _dataSize!!.x, _dataSize!!.y,
                frameCallbackWidth,
                frameCallbackHeight
            )
            MatrixUtils.flip(callbackOM, false, true)
        }
    }

    fun getWindowSize(): Point? {
        return mWindowSize
    }

    private fun sdkParamSet() {
        if (!isParamSet.get() && _dataSize!!.x > 0 && _dataSize!!.y > 0) {
            isParamSet.set(true)
        }
    }

    //需要回调，则缩放图片到指定大小，读取数据并回调
    private fun callbackIfNeeded() {
        if (mFrameCallback != null && (isRecord || isShoot)) {
            indexOutput = if (indexOutput++ >= 2) 0 else indexOutput
            if (outPutBuffer!![indexOutput] == null) {
                outPutBuffer!![indexOutput] = ByteBuffer.allocate(
                    frameCallbackWidth *
                            frameCallbackHeight * 4
                )
            }
            GLES20.glViewport(0, 0, frameCallbackWidth, frameCallbackHeight)
            EasyGlUtils.bindFrameTexture(mExportFrame[0], mExportTexture[0])
            _showFilter.setMatrix(callbackOM)
            _showFilter.draw()
            frameCallback()
            isShoot = false
            EasyGlUtils.unBindFrameBuffer()
            _showFilter.setMatrix(SM)
        }
    }

    //读取数据并回调
    private fun frameCallback() {
        GLES20.glReadPixels(
            0, 0, frameCallbackWidth, frameCallbackHeight,
            GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, outPutBuffer!![indexOutput]
        )
        mFrameCallback.onFrame(outPutBuffer!![indexOutput]!!.array(), 0)
    }

    fun create(width: Int, height: Int) {
        _glView!!.attachedToWindow()
        surfaceCreated(_surface)
        surfaceChanged(width, height)
    }

    fun destroy() {
        if (_renderer != null) {
            _renderer.onDestroy()
        }
        _glView!!.surfaceDestroyed(null)
        _glView!!.detachedFromWindow()
        _glView!!.clear()
    }

    fun requestRender() {
        _glView!!.requestRender()
    }

    fun onPause() {
        _glView!!.onPause()
    }

    fun onResume() {
        _glView!!.onResume()
    }

    /**
     * 自定义GLSurfaceView，暴露出onAttachedToWindow
     * 方法及onDetachedFromWindow方法，取消holder的默认监听
     * onAttachedToWindow及onDetachedFromWindow必须保证view
     * 存在Parent
     */
    class GLView(context: Context?) : GLSurfaceView(context) {
        private fun init() {
            holder.addCallback(null)
            setEGLWindowSurfaceFactory(object : EGLWindowSurfaceFactory {
                override fun createWindowSurface(egl: EGL10, display: EGLDisplay, config: EGLConfig, window: Any): EGLSurface {
                    try {
                        return egl.eglCreateWindowSurface(display, config, surface, null)
                    } catch (e: Exception) {
                        Log.e("TextureController", e.message!!)
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
            //setEGLConfigChooser(8, 8, 8, 8, 0, 0);
            setRenderer(this@TextureController)
            renderMode = RENDERMODE_WHEN_DIRTY
            preserveEGLContextOnPause = true
        }

        fun attachedToWindow() {
            super.onAttachedToWindow()
        }

        fun detachedFromWindow() {
            super.onDetachedFromWindow()
        }

        fun clear() {
//            try {
//                finalize();
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
        }

        init {
            init()
        }
    }
}